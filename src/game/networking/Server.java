package game.networking;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

import game.ai.AIPlayer;
import game.core.event.CreateAIPlayerRequest;
import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.GameFullEvent;
import game.core.event.GameStartedEvent;
import game.core.sync.ServerSync;
import game.core.sync.Updater;
import game.core.world.World;
import game.util.Time;

public class Server {
	/**
	 * Makes sure there's a properties file and creates it if it doesn't exist 
	 */
	private static File propertiesFile = new File("data/server.properties") {{
		if (!this.exists()) try {
			this.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}};
	
	/**
	 * Loads properties from file
	 */
	Properties properties = new Properties() {{
		try {
			this.load(new FileInputStream(propertiesFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}};

	public ArrayList<ServerListener> connections;
	private ServerSocket serverSocket = null;
	private World world;
	public static boolean listen;
	public final int NUM_PLAYERS = Integer.parseInt(properties.getProperty("players", "4"));
	public static final int timeToWait = 60000;

	/**
	 * Starts the server
	 */
	public Server() {
		AIPlayer.init();
		try {
			properties.store(new PrintWriter(propertiesFile), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServerListener.NUM_AI_PLAYERS = Integer.parseInt(properties.getProperty("ai", "1"));
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
	 * @param port- The port to create the socket on
	 */
	private void createSocket(int port) {
		// Open a server socket:
		// We must try because it may fail with a checked exception:
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(timeToWait);
			System.out.println("local Address: " + Inet4Address.getLocalHost().getHostAddress());
			System.out.println("Server registered to port " + port);
		} catch (IOException e) {
			System.err.println("Couldn't listen on port " + port);
			System.exit(1);
		}
	}

	/**
	 * Listen for connections from clients make a new server listener for each
	 */
	private void listenForConnections() {
		while (listen) {
			try {
				// Listen to the socket, accepting connections from new clients:
				Socket socket = serverSocket.accept();
				if (listen) {
					ServerListener sl = new ServerListener(socket, connections, world);
					connections.add(sl);
					sl.start();
				} else {
					GameFullEvent e = new GameFullEvent();
					ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
					os.writeObject(e);
				}
			} catch (SocketTimeoutException s) {
				if (listen && connections.size() > 0) {
					ServerListener.NUM_AI_PLAYERS = NUM_PLAYERS - connections.size();
					for (int i = 0; i < ServerListener.NUM_AI_PLAYERS; i++) {
						Events.trigger(new CreateAIPlayerRequest(connections.get(0), i));
					}
					connections.get(0).startGame();
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
	 * @param e-Game started event object
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
	/**
	 * Closes server and starts a new one when game ends
	 * @param recieved
	 */
	private void endGame(Object recieved) {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Server();
	}

	/**
	 * Main method for starting server
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSync.init();
		new Server();
	}

}
