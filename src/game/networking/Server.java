package game.networking;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import game.core.player.Player;

public class Server {

	public static Server server;
	
	//private ArrayList<Player> playerTable;
	private ArrayList<Player> playerTable;
	private ArrayList<ServerListener> connections;
	private ServerSocket serverSocket = null;
	
	public Server(){
		//playerTable = new ArrayList<Player>();
		playerTable = new ArrayList<Player>();
		connections = new ArrayList<ServerListener>();
		int port = 8942;
		
		// Open a server socket:
		

		// We must try because it may fail with a checked exception:
		try {
			this.serverSocket = new ServerSocket(port);
			System.out.println("Server registered to port " + port);
		} catch (IOException e) {
			System.err.println("Couldn't listen on port " + port);
			System.exit(1); // Give up.
		}
		
		
		boolean waiting = true;
		// Must try again for the same reason:
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
	public static void main(String[] args){
		server = new Server();
	}

}

