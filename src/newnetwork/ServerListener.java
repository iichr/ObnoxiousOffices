package newnetwork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import game.core.player.Player;

public class ServerListener extends Thread {
	private ArrayList<Player> playerTable;
	private ArrayList<ServerListener> connections;
	
	private Socket socket = null;
	private ObjectInputStream is;
	private ObjectOutputStream os;
	
	public ServerListener(Socket socket, ArrayList<Player> hash, ArrayList<ServerListener> connection) {
		this.playerTable = hash;
		this.socket = socket;
		this.connections = connection;
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
	public void run(){
		boolean running = true;
		while(running){
			if(this.playerTable.size() < 4 ){
				try {
					String playerName = is.readObject().toString();
					//System.out.println(playerName);
					this.addPlayerToGame(playerName);
					this.sendToAllClients("Player " + playerName + " has joined the game!");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(this.playerTable.size() == 4){
					this.sendToAllClients("START_GAME!");
				}
			}else{
				
				
				//PLAYIN THE GAME
				
			}
		}
		//this.forwardInfo("Hello");
		
//		// This is so that we can use readLine():
//		BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		
//		// Ask the client what its name is:
//		String clientName = fromClient.readLine();
//		// For debugging:
//		System.out.println(clientName + " connected");
//						
//		// We add the client to the table:
//		this.addPlayerToGame(clientName);
//		
//		if(this.playerTable.size() == 4){
//			System.out.println("Four players Ready");
//			waiting = false;
//			
//			ObjectOutputStream tom = new ObjectOutputStream(socket.getOutputStream());
//			tom.writeObject("HAHAH");
//			tom.flush();
//		}

	}
	
	/**
	 * Sends info to all clients
	 * @param obj The info to send
	 */
	private void sendToAllClients(Object obj){
		for (int i = 0; i < this.connections.size(); i++) {
			this.connections.get(i).forwardInfo(obj);;
		}
	}
	
	/**
	 * Forwards the info to one client
	 * @param recieved The info to send
	 */
	private void forwardInfo(Object recieved){
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

	/**
	 * Adds a player to the game.
	 * @param name The name of the player to add.
	 */
	private void addPlayerToGame(String name){
		if(!this.playerNameUsed(name)){
			this.playerTable.add(new Player(name));
			System.out.println("PLayer " + name + " added to the game!");
		}else{
			System.out.println("Player " + name + " has already been added to the game!");
		}
	}
	
	/**
	 * Remove a player from the game.
	 * @param name The name of the player to be removed.
	 */
	private void removePlayerFromGame(String name){
		if(this.playerNameUsed(name)){
			this.playerTable.remove(name);
			System.out.println("Player " + name + " has been removed from the game!");
		}else{
			System.out.println("Player " + name + " is not currently in the game!");
		}
	}
	
	/**
	 * Check to see if the player name has been used
	 * @param name The name to check
	 * @return Whether or not the name is being used
	 */
	private boolean playerNameUsed(String name){
		for (int i = 0; i < this.playerTable.size(); i++) {
			if(this.playerTable.get(i).name.equals(name)){
				return true;
			}
		}
		return false;
	}
	
	
}
