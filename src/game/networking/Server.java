package game.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;

import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.GameFullEvent;
import game.core.event.GameStartedEvent;
import game.core.sync.ServerSync;
import game.core.sync.Updater;
import game.core.world.World;
import game.util.Time;

public class Server {

	public ArrayList<ServerListener> connections;
	private ServerSocket serverSocket = null;
	private World world;
	public static boolean listen = true;
	private final int NUM_PLAYERS = 4;
	

	/**
	 * Starts the server
	 */
	public Server() {
		listen = true;
		connections = new ArrayList<ServerListener>();
		final int port = 8942;
		Events.on(GameStartedEvent.class, this::updateWorld);
		Events.on(GameFinishedEvent.class, this::endGame);
		// create the server socket
		createSocket(port);
		// load the world
		loadWorld();
		// listen for new connections
		listenForConnections();
	}

	/**
	 * Creates a server socket on the given port
	 * 
	 * @param port-
	 *            The port to create the socket on
	 */
	private void createSocket(int port) {
		// Open a server socket:
		// We must try because it may fail with a checked exception:
		try {
			this.serverSocket = new ServerSocket(port);
			// URL url = new URL("http://checkip.amazonaws.com/");
			// BufferedReader br = new BufferedReader(new
			// InputStreamReader(url.openStream()));
			// System.out.println("Server IP : "+br.readLine());
			System.out.println("local Address: " + Inet4Address.getLocalHost().getHostAddress());
			System.out.println("Server registered to port " + port);
		} catch (IOException e) {
			System.err.println("Couldn't listen on port " + port);
			System.exit(1); // Give up.
		}
	}

	/**
	 * Listen for connections from clients make a new server listener for each
	 */
	private void listenForConnections() {
		while (true) {
			try {
				// Listen to the socket, accepting connections from new clients:
				Socket socket = this.serverSocket.accept();
				if (listen) {
					ServerListener sl = new ServerListener(socket, this.connections, world);
					this.connections.add(sl);
					sl.start();
				} else {
					GameFullEvent e = new GameFullEvent();
					ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
					os.writeObject(e);
				}

			} catch (IOException e) {
				System.err.println("IO error " + e.getMessage());
			}
		}
	}

	/**
	 * Load the required world form file
	 */
	private void loadWorld() {
		try {
			this.world = World.load(Paths.get("data/office" + NUM_PLAYERS + "Player.level"), NUM_PLAYERS);
			World.world = this.world;
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Update the world
	 * 
	 * @param e-
	 *            Game started event object
	 */
	private void updateWorld(GameStartedEvent e) {
		Updater worldUpdater = new Updater(e.world, Time.UPDATE_RATE, true);
		Thread updateThread = new Thread(worldUpdater);
		try {
			// Give players a second to join
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		updateThread.start();
	}
	private void endGame(Object recieved){
		new Server();
		
	}

	/**
	 * Main method for starting server
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSync.init();
		new Server();
	}
	

}
