package Networking;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Connects the client to the server and starts the client sender and client
 * reciever threads
 */
class Client {
	public static int PLAYERNUM = 0;
	public static String OPPONENT = "";
	public static boolean Connected = false;

	public static void main(String[] args) {

		// Check correct usage:
		if (args.length != 3) {
			System.err.println("Usage: java Client user-nickname hostname port");
			System.exit(1); // Give up.
		}

		// Initialize information:
		String nickname = args[0];
		int port;
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			System.out.println("Not a valid port");
			return;
		}
		String hostname = args[2];
		// Open sockets:
		PrintStream toServer = null;
		BufferedReader fromServer = null;
		Socket server = null;

		try {
			server = new Socket(hostname, port);
			toServer = new PrintStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1); // Give up.
		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			System.exit(1); // Give up.
		}
		
		//Initalise gui HERE!!!!!!!!!!!!!!!!!!
		

		// Create two client threads:
		ClientSender sender = new ClientSender(nickname, toServer); //ADD GUI TO PARAMETER
		ClientReceiver receiver = new ClientReceiver(fromServer); //ADD GUI TO PARAMETER

		// Run them in parallel:
		sender.start();
		receiver.start();

		// Wait for them to end and close sockets.
		try {
			sender.join();
			toServer.close();
			receiver.join();
			fromServer.close();
			server.close();
		} catch (IOException e) {
			System.err.println("Something wrong " + e.getMessage());
			System.exit(1); // Give up.
		} catch (InterruptedException e) {
			System.err.println("Unexpected interruption " + e.getMessage());
			System.exit(1); // Give up.
		}
	}
}
