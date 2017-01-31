package Networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class ClientReceiver extends Thread {
	ArrayList<SendableObject> commands = new ArrayList<SendableObject>();
	private BufferedReader server;
	// private GUI gui;
	private boolean notQuit = true;

	/**
	 * Initalises the server and GUI
	 * 
	 * @param BufferReader
	 * @param NoughtandCrossesGUI
	 */
	ClientReceiver(BufferedReader server) { // INSERTGUI gui
		this.server = server;
		// this.gui = gui;
	}

	/**
	 * Gets messeages from the server and depending on the message either
	 * performs a move, exits the program or restarts the game
	 */
	public void run() {
		String t;
		try {
			t = server.readLine();
			// Pick of name of people online
			String t1 = "";
			int len = t.length();
			for (int n = 4; n <= (len - 3); n++) {
				char ch = t.charAt(n);
				t1 = t1 + ch;
			}

			System.out.println("The people online are: " + t1);
			while (Client.Connected == false) {
				String request = server.readLine();
				if (request != null) {
					int length = request.length();
				}
			}
			Thread.sleep(1);
		}

		catch (IOException | InterruptedException e1) {
			e1.printStackTrace();
		}

		while (true)
			try {
				Thread.sleep(100);
				while (notQuit && Client.Connected) {
							popCommand();
					}
				}
			catch(Exception e)
	{
		e.printStackTrace();
	}

}

	synchronized public SendableObject popCommand() {
		if (commands.isEmpty()) {
			return null;
		} else {
			SendableObject comm = commands.get(0);
			commands.remove(0);
			return comm;
		}
	}
}
