package Networking;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

// Gets messages from other clients via the server (by the
// ServerSender thread).

public class ClientReceiver extends Thread {

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
					char ch = request.charAt(length - 1);
					if (ch == 'W') {
						String playerName = "";
						// get name out and set it as player in client if person
						// types yes then set this as player
						for (int name = 5; name <= (length - 4); name++) {
							char character = request.charAt(name);
							playerName = playerName + character;
						}
						Client.OPPONENT = playerName;
						System.out.println(request
								+ "ould you like to play a game? Type 'Yes' to accept or 'No' to reject then press enter. If you have rejected the request after pressing enter, enter then name of the person you want to play against");
					} else if (ch == 'o') {
						System.out.println("Your request has been rejected. Please enter soneone elses name");
						Scanner s = new Scanner(System.in);
						while (s.hasNextLine()) {
							Client.OPPONENT = s.nextLine();
						}
						s.close();

					} else {
						Client.Connected = true;
					}

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
					String s = server.readLine();
					if (s != null) {
						//DECODE MESSAGE
					}
				}
			}
		catch(Exception e){
			e.printStackTrace();
		}
}
}
