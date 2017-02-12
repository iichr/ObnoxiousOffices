package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;

import game.core.event.*;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

public class ServerListener extends Thread {
	private ArrayList<Player> playerTable;
	private ArrayList<ServerListener> connections;

	private Socket socket = null;
	private ObjectInputStream is;
	private ObjectOutputStream os;
	public World world;

	public ServerListener(Socket socket, ArrayList<Player> hash, ArrayList<ServerListener> connection) {
		Events.on(PlayerRotatedEvent.class,this::sendToAllClients);
		Events.on(PlayerProgressUpdateEvent.class,this::sendToAllClients);
		Events.on(PlayerMovedEvent.class,this::sendToAllClients);
		Events.on(PlayerActionAddedEvent.class,this::sendToAllClients);
		Events.on(PlayerActionEndedEvent.class,this::sendToAllClients);
		Events.on(PlayerEffectAddedEvent.class,this::sendToAllClients);
		Events.on(PlayerEffectEndedEvent.class,this::sendToAllClients);
		Events.on(PlayerAttributeChangedEvent.class,this::sendToAllClients);

		Events.on(ConnectionAttemptEvent.class, this::processConnectionAttempt);

		this.playerTable = hash;
		this.socket = socket;
		this.connections = connection;
		try {
			this.world = World.load(Paths.get("data/office4player.level"), 4);
			World.world = this.world;
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			this.is = new ObjectInputStream(this.socket.getInputStream());
			this.os = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Can't make Input and Output for connect ~ Droping connection");
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				System.out.println("Can't close socket");
				e1.printStackTrace();
			}
		}

	}

	private void processConnectionAttempt(ConnectionAttemptEvent event) {
		if(playerTable.size() <= 4) addPlayerToGame(event.name);
		if(playerTable.size() == 4) sendToAllClients(new GameStartedEvent(world));
	}

	@Override
	public void run() {
		while (true) {
			Event eventObject = null;
			try {
				eventObject = (Event) is.readObject();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			Events.trigger(eventObject);
		}
	}

	/**
	 * Sends info to all clients
	 * 
	 * @param obj
	 *            The info to send
	 */
	public void sendToAllClients(Object obj) {
		for (int i = 0; i < this.connections.size(); i++) {
			this.connections.get(i).forwardInfo(obj);
		}
	}

	/**
	 * Forwards the info to one client
	 * 
	 * @param recieved
	 *            The info to send
	 */
	private void forwardInfo(Object recieved) {
		try {
			os.writeObject(recieved);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendToOne(Object recieved, String name) {
		for (int i = 0; i < this.playerTable.size(); i++) {
			if(this.playerTable.get(i).name.equals(name)){
				try {
					os.writeObject(recieved);
					os.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * Adds a player to the game.
	 * 
	 * @param name
	 *            The name of the player to add.
	 */
	private void addPlayerToGame(String name) {
		if (!this.playerNameUsed(name)) {
			//creates player
			int playerNumber = playerTable.size();
			Player playerObject = new Player(name, Direction.SOUTH, world.getSpawnPoint(playerNumber));
			playerObject.setHair(playerNumber);
			
			//adds player to player table and world
			this.playerTable.add(playerObject);
			world.addPlayer(playerObject);
			System.out.println("Player " + name + " added to the game!");
			
			//send player created event to client for that player
			PlayerCreatedEvent event = new PlayerCreatedEvent(playerObject);
			Events.trigger(event);
			sendToOne(event, name);
		} else {
			System.out.println("Player " + name + " has already been added to the game!");
		}
	}

	/**
	 * Remove a player from the game.
	 * 
	 * @param name
	 *            The name of the player to be removed.
	 */
	private void removePlayerFromGame(String name) {
		if (this.playerNameUsed(name)) {
			this.playerTable.remove(name);
			System.out.println("Player " + name + " has been removed from the game!");
		} else {
			System.out.println("Player " + name + " is not currently in the game!");
		}
	}

	/**
	 * Check to see if the player name has been used
	 * 
	 * @param name
	 *            The name to check
	 * @return Whether or not the name is being used
	 */
	private boolean playerNameUsed(String name) {
		for (int i = 0; i < this.playerTable.size(); i++) {
			if (this.playerTable.get(i).name.equals(name)) {
				return true;
			}
		}
		return false;
	}

}
