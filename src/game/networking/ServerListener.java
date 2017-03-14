package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import game.core.event.CreateAIPlayerRequest;
import game.core.event.Event;
import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.GameStartedEvent;
import game.core.event.chat.ChatMessageReceivedEvent;
import game.core.event.player.PlayerAttributeChangedEvent;
import game.core.event.player.PlayerCreatedEvent;
import game.core.event.player.PlayerMovedEvent;
import game.core.event.player.PlayerProgressUpdateEvent;
import game.core.event.player.PlayerQuitEvent;
import game.core.event.player.PlayerRotatedEvent;
import game.core.event.player.PlayerStateAddedEvent;
import game.core.event.player.PlayerStateRemovedEvent;
import game.core.event.player.action.PlayerActionAddedEvent;
import game.core.event.player.action.PlayerActionEndedEvent;
import game.core.event.player.effect.PlayerEffectAddedEvent;
import game.core.event.player.effect.PlayerEffectElapsedUpdate;
import game.core.event.player.effect.PlayerEffectEndedEvent;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.World;

public class ServerListener extends Thread {
	private ArrayList<ServerListener> connections;
	private boolean makingAI = false;
	private Socket socket = null;
	private ObjectInputStream is;
	private ObjectOutputStream os;
	private int playerNumber;
	private Queue<Object> outputQ;
	public World world;
	private final Object queueWait;
	boolean running = true;
	public static int NUM_AI_PLAYERS = 1;
	public String playerName;

	/**
	 * 
	 * @param socket-The
	 *            socket its connected to
	 * @param connection-The
	 *            server listeners
	 * @param world-
	 *            The game world
	 */
	public ServerListener(Socket socket, ArrayList<ServerListener> connection, World world) {
		this.queueWait = new Object();
		this.socket = socket;
		this.connections = connection;
		this.playerNumber = connections.size();
		this.world = world;

		// set up the event listeners
		listenForEvents();
		// make the object streams
		createObjectStreams();
		outputQ = new ConcurrentLinkedQueue<Object>();
		sendQueue();
	}

	/**
	 * Set up the list of events that the server should listen for
	 */
	private void listenForEvents() {
		Events.on(PlayerRotatedEvent.class, this::forwardInfo);
		Events.on(PlayerProgressUpdateEvent.class, this::forwardInfo);
		Events.on(PlayerMovedEvent.class, this::forwardInfo);
		Events.on(PlayerActionAddedEvent.class, this::forwardInfo);
		Events.on(PlayerActionEndedEvent.class, this::forwardInfo);
		Events.on(PlayerEffectAddedEvent.class, this::forwardInfo);
		Events.on(PlayerEffectEndedEvent.class, this::forwardInfo);
		Events.on(PlayerAttributeChangedEvent.class, this::forwardInfo);
		Events.on(PlayerStateAddedEvent.class, this::forwardInfo);
		Events.on(PlayerStateRemovedEvent.class, this::forwardInfo);
		Events.on(PlayerEffectElapsedUpdate.class, this::forwardInfo);
		Events.on(GameFinishedEvent.class, this::closeConnection);
		Events.on(ChatMessageReceivedEvent.class, this::forwardInfo);
	}

	/**
	 * Attempt to make the input and output object streams If it fails then
	 * close the server socket
	 */
	private void createObjectStreams() {
		try {
			this.is = new ObjectInputStream(this.socket.getInputStream());
			this.os = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Can't make Input and Output for connect ~ Dropping connection");
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				System.out.println("Can't close socket");
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Add conections and any hard coded AI players, trigger game start event
	 */
	@Override
	public void run() {
		while (running) {
			if (!makingAI) {
				if (world.getPlayers().size() < connections.size()) {
					try {
						playerName = is.readObject().toString();
						this.addPlayerToGame(playerName);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					// Allows hard coded AI player to be added for prototype
					if (world.getPlayers().size() == world.getMaxPlayers() - NUM_AI_PLAYERS && NUM_AI_PLAYERS > 0) {
						for (int i = 0; i < NUM_AI_PLAYERS; i++) {
							makingAI = true;
							Events.trigger(new CreateAIPlayerRequest(this, i));
						}
					}
					if (world.getPlayers().size() == world.getMaxPlayers()) {
						Server.listen = false;
						GameStartedEvent gameStart = new GameStartedEvent(world);
						sendToAllClients(gameStart);
						Events.trigger(gameStart);
					}
				} else {
					try {
						Event eventObject = (Event) is.readObject();
						System.out.println("recieved: " + eventObject);
						Events.trigger(eventObject);
					} catch (Exception e) {
						Events.trigger(new PlayerQuitEvent(playerName));
					}
				}
			}
		}
	}

	/**
	 * Sends info to all clients
	 * 
	 * @param obj-The
	 *            info to send
	 */
	public void sendToAllClients(Object obj) {
		for (int i = 0; i < this.connections.size(); i++) {
			System.out.println("sending " + obj + "to connection " + i);
			this.connections.get(i).forwardInfo(obj);
		}
	}

	/**
	 * Forwards the info to one client
	 * 
	 * @param recieved-
	 *            The info to send
	 */
	private void forwardInfo(Object recieved) {
		outputQ.offer(recieved);
		synchronized (this.queueWait) {
			this.queueWait.notifyAll();
		}

	}

	/**
	 * Takes a message of the queue and tried to send it, if it can't it closes
	 * the connection and adds and ai player instead
	 */
	private void sendQueue() {
		Thread outputThread = new Thread(() -> {
			Object output;
			boolean notQuit = true;
			while (notQuit) {
				output = outputQ.poll();
				if (output != null) {
					if (!socket.isClosed()) {
						try {
							os.writeObject(output);
							os.flush();
						} catch (IOException e) {
							try {
								Events.trigger(new PlayerQuitEvent(playerName));
								System.out.println("ai added");
								output = null;
								running = false;
								notQuit = false;
								socket.close();
								connections.remove(playerNumber);
								NUM_AI_PLAYERS = NUM_AI_PLAYERS + 1;
								System.out.println("Player Removed");
							} catch (IOException e1) {
								System.out.println("e1");
							}
						}
					} else {
						System.out.println("Player left");
					}
				} else {
					if (running) {
						try {
							synchronized (this.queueWait) {
								this.queueWait.wait();
							}
						} catch (InterruptedException e1) {
							System.out.print("INterrupted exception:  ");
							e1.printStackTrace();

						}
					}
				}
			}
		});
		outputThread.start();
	}

	/**
	 * Adds a player to the game.
	 * 
	 * @param name-The
	 *            name of the player to add.
	 */
	private void addPlayerToGame(String name) {
		if (!this.playerNameUsed(name)) {
			Player playerObject = new Player(name, Direction.SOUTH, world.getSpawnPoint(playerNumber));
			playerObject.setHair(playerNumber);
			world.addPlayer(playerObject);

			PlayerCreatedEvent event = new PlayerCreatedEvent(name);
			Events.trigger(event);
			forwardInfo(event);
			System.out.println("Player " + name + " added to the game!");
		} else {
			System.out.println("Player " + name + " has already been added to the game!");
		}
	}

	/**
	 * Adds ai to the game
	 * 
	 * @param playerToAdd
	 */
	public void addAIToGame(Player playerToAdd) {
		playerToAdd.setHair(world.getPlayers().size());
		world.addPlayer(playerToAdd);
		if (playerToAdd.isAI)
			makingAI = false;
	}

	/**
	 * Remove a player from the game.
	 * 
	 * @param name-The
	 *            name of the player to be removed.
	 */
	private void removePlayerFromGame(String name) {
		if (this.playerNameUsed(name)) {
			System.out.println("Player " + name + " has been removed from the game!");
		} else {
			System.out.println("Player " + name + " is not currently in the game!");
		}
	}

	/**
	 * Check to see if the player name has been used
	 * 
	 * @param name-The
	 *            name to check
	 * @return Whether or not the name is being used
	 */
	private boolean playerNameUsed(String name) {
		for (Player p : world.getPlayers()) {
			if (p.name.equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Close the connection when the game ends
	 * 
	 * @param recieved-
	 *            Object reiceved from the game closed event
	 */
	private void closeConnection(Object recieved) {
		forwardInfo(recieved);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			this.is.close();
			this.os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.socket = null;
	}

}
