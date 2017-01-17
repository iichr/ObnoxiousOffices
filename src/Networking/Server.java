package Networking;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	public static Map<String, Integer> SCORES = new HashMap<>();

	public static void main(String[] args) {
		int NumberOfClients = 0;
		boolean waiting = true;
		if (args.length != 1) {
			System.err.println("Usage: java Server port");
			System.exit(1); // Give up.
		}
		// Initialize information:
		int port;
		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			System.out.println("Not a valid port");
			return;
		}

		// This will be shared by the server threads:
		ClientTable clientTable = new ClientTable();

		// Open a server socket:
		ServerSocket serverSocket = null;

		// We must try because it may fail with a checked exception:
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.err.println("Couldn't listen on port " + port);
			System.exit(1); // Give up.
		}

		// Must try again for the same reason:
		try {

			while (waiting = true) {
				// Listen to the socket, accepting connections from new clients:
				Socket socket = serverSocket.accept();

				// This is so that we can use readLine():
				BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				// Ask the client what its name is:
				String clientName = fromClient.readLine();

				// For debugging:
				System.out.println(clientName + " connected");
				NumberOfClients = NumberOfClients + 1;
				// We add the client to the table:
				clientTable.add(clientName);
				SCORES.put(clientName, 0);

				// Create and start a new thread to read from the client:
				(new ServerReceiver(clientName, fromClient, clientTable)).start();

				// Create and start a new thread to write to the client:
				PrintStream toClient = new PrintStream(socket.getOutputStream());
				(new ServerSender(clientTable.getQueue(clientName), toClient)).start();
				
				if(NumberOfClients >= 4){
				waiting = false;
					//START GAME
				}
			}
		} catch (IOException e) {
			System.err.println("IO error " + e.getMessage());
		}
	}
}