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
	public static String[] OPPONENT = new String[3];
	public static boolean Connected = false;

	public static void main(String[] args) {

	

		// Initialize information:
		String nickname = getName();
		int port = 8942;
		String hostname = "localhost";
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

	private static String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
