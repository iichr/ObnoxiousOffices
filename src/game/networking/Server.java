package game.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.net.SocketTimeoutException;

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
	public String startTime;
	public int startMins;
	public int startHours;
	private final int SO_TIMEOUT = 120000;

	/**
	 * Starts the server
	 */
	public Server() {
		listen = true;
		connections = new ArrayList<ServerListener>();
		final int port = 8942;
		Events.on(GameStartedEvent.class, this::updateWorld);
		Events.on(GameFinishedEvent.class, this::endGame);
		DateFormat df = new SimpleDateFormat("HH:mm");
		DateFormat dfMin = new SimpleDateFormat("mm");
		DateFormat dfHr = new SimpleDateFormat("HH");
		Date startdate = new Date();
		startTime = df.format(startdate);
		System.out.println("HERE");
		startMins = Integer.parseInt((dfMin.format(startdate)));
		startHours = Integer.parseInt((dfHr.format(startdate)));
		System.out.println(startTime);
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
			serverSocket.setSoTimeout(SO_TIMEOUT);
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
				/*
				 * System.out.println("Before if"); if(waitingToLong() &&
				 * listen){ ServerListener.NUM_AI_PLAYERS =
				 * ServerListener.NUM_AI_PLAYERS + 1; }
				 */
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
			}
			catch (SocketTimeoutException s) {
				System.out.println("timeout");
				ServerListener.NUM_AI_PLAYERS = ServerListener.NUM_AI_PLAYERS + 1;
				//listenForConnections();
			} catch (IOException e) {
				System.err.println("IO error " + e.getMessage());
			}

		}
	}

	private boolean waitingToLong() {
		System.out.println("CHECKING TIME");
		DateFormat dfMin = new SimpleDateFormat("mm");
		DateFormat dfHr = new SimpleDateFormat("HH");
		Date currentdate = new Date();
		int CurrentMins = Integer.parseInt((dfMin.format(currentdate)));
		int CurrentHours = Integer.parseInt((dfHr.format(currentdate)));
		if ((startHours == CurrentHours && ((startMins - CurrentMins) <= -4))
				|| ((startHours != CurrentHours) && (((60 - CurrentMins)) - startMins) <= 4)) {
			return true;
		} else {
			return false;
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

	private void endGame(Object recieved) {
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
