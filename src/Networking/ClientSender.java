package Networking;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Repeatedly reads recipient's nickname and text from the user in two separate
 * lines, sending them to the server (read by ServerReceiver thread).
 */

public class ClientSender extends Thread {

	private String nickname;
	private PrintStream server;
	// private gui HERE;
	private boolean NotQuit = true;
	private boolean notassigned = false;
	private String[] players = new String[3];

	/**
	 * Initalise the nickname, printstream and gui
	 * 
	 * @param nickname
	 * @param PrintStream
	 * @param NoughtsandCrossesGUI
	 */
	ClientSender(String nickname, PrintStream server) { // INSERTGUI gui) {
		this.nickname = nickname;
		this.server = server;
		// this.gui = gui;
	}

	/**
	 * Runs the client sender thread
	 */
	public void run() {
		// So that we can use the method readLine:
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

		// Tell the server what my nickname is:
		server.println(nickname);

		while (!notassigned) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}

			if (Client.Connected) {
				// Opens GUI
				// gui.newGUI(this);
				notassigned = true;
			}
		}
		// Then loop forever sending messages to recipients via the server:
		while (NotQuit && Client.Connected) {
			try {
				Thread.sleep(200);
			} catch (Exception e) {

			}
		}
	}

}