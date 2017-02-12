package game.networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.core.event.PlayerCreatedEvent;
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

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			if (this.playerTable.size() < 4) {
				try {
					String playerName = is.readObject().toString();
					// System.out.println(playerName);
					this.addPlayerToGame(playerName);
		
					// this.sendToAllClients("Player " + playerName + " has
					// joined the game!");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * //Allows hard coded AI player to be added for prototype if
				 * (this.playerTable.size() == 3) { Events.trigger(new
				 * CreateAIPlayerRequest()); }
				 */
				if (this.playerTable.size() == 4) {
					for(int i = 0; i < playerTable.size(); i++){
						Player p = playerTable.get(i);
						p.setLocation(new Location(i, i, world));
						world.addPlayer(p);
					}
					Events.trigger(new GameStartedEvent(world));
					sendToAllClients(new GameStartedEvent(world));
				}
			} else {
				try {
					Event eventObject = (Event) is.readObject();
					// this.sendToAllClients(eventObject);
					Events.trigger(eventObject);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		// this.forwardInfo("Hello");

		// // This is so that we can use readLine():
		// BufferedReader fromClient = new BufferedReader(new
		// InputStreamReader(socket.getInputStream()));
		//
		// // Ask the client what its name is:
		// String clientName = fromClient.readLine();
		// // For debugging:
		// System.out.println(clientName + " connected");
		//
		// // We add the client to the table:
		// this.addPlayerToGame(clientName);
		//
		// if(this.playerTable.size() == 4){
		// System.out.println("Four players Ready");
		// waiting = false;

	}

	/**
	 * Sends info to all clients
	 * 
	 * @param obj
	 *            The info to send
	 */
	private void sendToAllClients(Object obj) {
		for (int i = 0; i < this.connections.size(); i++) {
			this.connections.get(i).forwardInfo(obj);
			;
		}
	}

	/**
	 * Forwards the info to one client
	 * 
	 * @param recieved
	 *            The info to send
	 */
	private void forwardInfo(Object recieved) {
		for (int i = 0; i < this.playerTable.size(); i++) {
			System.out.println(this.playerTable.get(i).name);

		}

		try {
			os.writeObject(recieved);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void sendToOne(Object recieved, String name) {
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
			Player playerObject = new Player(name, Direction.SOUTH, null);
			this.playerTable.add(playerObject);
			System.out.println("PLayer " + name + " added to the game!");
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
