package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.GameFullEvent;

public class ClientListner extends Thread {
	private Socket server;
	private ObjectInputStream is;
	private Queue<Object> inputQ;
	private boolean connected;

	/**
	 * Starts the client listener
	 * 
	 * @param server-
	 *            The socket it is connected two
	 */
	public ClientListner(Socket server) {
		this.server = server;

		Events.on(GameFullEvent.class, this::gameFull);
		Events.on(GameFinishedEvent.class, this::endGame);
		inputQ = new LinkedList<Object>();
		manageEvents();
	}

	/**
	 * Listens for objects being sent from Server listener and puts them into
	 * input queue
	 */
	@Override
	public void run() {
		try {
			is = new ObjectInputStream(this.server.getInputStream());
			connected = true;
			while (connected) {
				Object input = is.readObject();
				inputQ.offer(input);
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Takes an event of the queue and triggers it
	 */
	private void manageEvents() {
		Thread manageInputs = new Thread(() -> {
			connected = true;
			while (connected) {
				try {
					Thread.sleep(5);
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

	private void gameFull(GameFullEvent e) {
		System.out.println("got game full event");
		connected = false;
	}

	private void endGame(GameFinishedEvent e) {
		//TODO the client isn't closing properly
		connected = false;		
	}
}
