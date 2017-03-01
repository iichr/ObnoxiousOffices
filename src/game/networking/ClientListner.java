package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import game.core.event.Event;
import game.core.event.Events;

public class ClientListner extends Thread {
	private Socket server;
	private ObjectInputStream is;
	private Queue<Object> inputQ;

	public ClientListner(Socket server) {
		this.server = server;

		inputQ = new LinkedList<Object>();
		manageEvents();
	}

	@Override
	public void run() {
		try {
			is = new ObjectInputStream(this.server.getInputStream());
			boolean running = true;
			while (running) {
				Object input = is.readObject();
				inputQ.offer(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void manageEvents() {
		Thread manageInputs = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Object input = inputQ.poll();
				if (input != null) {
					Event e = (Event) input;
					Events.trigger(e);
				}
			}
		});
		manageInputs.start();
	}
}
