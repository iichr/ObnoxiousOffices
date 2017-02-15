package game.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import game.core.ifc.ServerSync;
import game.core.player.Player;

public class Server {

	// private ArrayList<Player> playerTable;
	private ArrayList<Player> playerTable;
	public ArrayList<ServerListener> connections;
	private ServerSocket serverSocket = null;

	public Server() {
		playerTable = new ArrayList<Player>();
		connections = new ArrayList<ServerListener>();
		final int port = 8942;

		//create the server socket
		createSocket(port);

		//listen for new connections
		listenForConnections();
	}

	/**
	 * Creates a server socket on the given port
	 * 
	 * @param port
	 *            The port to create the socket on
	 */
	private void createSocket(int port) {
		// Open a server socket:
		// We must try because it may fail with a checked exception:
		try {
			this.serverSocket = new ServerSocket(port);
			System.out.println("Server registered to port " + port);
		} catch (IOException e) {
			System.err.println("Couldn't listen on port " + port);
			System.exit(1); // Give up.
		}
	}

	/**
	 * Listen for connections from clients
	 * make a new server listener for each
	 */
	private void listenForConnections() {
		boolean waiting = true;
		try {
			while (waiting) {
				// Listen to the socket, accepting connections from new clients:
				Socket socket = this.serverSocket.accept();
				ServerListener sl = new ServerListener(socket, this.playerTable, this.connections);
				this.connections.add(sl);
				sl.start();
			}
		} catch (IOException e) {
			System.err.println("IO error " + e.getMessage());
		}
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSync.init();
		new Server();
	}

}
