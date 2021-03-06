package game.networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import game.core.event.ConnectionAttemptEvent;
import game.core.event.ConnectionFailedEvent;
import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.GameFullEvent;
import game.core.event.chat.ChatMessageCreatedEvent;
import game.core.event.player.PlayerCreatedEvent;
import game.core.event.player.PlayerInputEvent;
import game.core.player.Player;

public class Client {

	private Socket server;
	private ObjectOutputStream od;
	public boolean connected = false;
	

	/**
	 * Listens for events
	 */
	public Client() {
		Events.on(ConnectionAttemptEvent.class, this::connectToServer);
		Events.on(PlayerCreatedEvent.class, this::setLocalPlayer);
		Events.on(PlayerInputEvent.class, this::sendDataToServer);
		Events.on(ChatMessageCreatedEvent.class, this::sendDataToServer);
		Events.on(GameFullEvent.class, this::gameFull);
		Events.on(GameFinishedEvent.class, this::endGame);
	}

	/**
	 * Sends Data to the server for updating the players
	 * @param data- The data to end
	 */
	public void sendDataToServer(Object data) {
		try {
			od.writeObject(data);
			od.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Attempt to connect to the server when ConnectionAttemptEvent is received
	 * If it fails then send connectionFailedEvent
	 * @param event-The ConnectionAttemptEvent
	 */
	public void connectToServer(ConnectionAttemptEvent event) {
		int port = 8942;
		String hostname = event.ipAddress;

		try {
			this.server = new Socket();
			this.server.connect(new InetSocketAddress(hostname, port), 5000);
			try {
				od = new ObjectOutputStream(this.server.getOutputStream());
				connected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			Events.trigger(new ConnectionFailedEvent());

		} catch (IOException e) {
			System.err.println("The server doesn't seem to be running " + e.getMessage());
			Events.trigger(new ConnectionFailedEvent());
		}

		if (connected) {
			this.sendDataToServer(event.name);
			new ClientListner(this.server).start();
		}
	}

	/**
	 * Sets the local player name on PlayerCreatedEvent
	 * @param e-The playerCreatedEvent
	 */
	public void setLocalPlayer(PlayerCreatedEvent e) {
		if (Player.localPlayerName.equals("")) {
			Player.localPlayerName = e.localPlayerName;
		}
	}
	/**
	 * Disconnects from server if game is full
	 * @param e
	 */

	private void gameFull(GameFullEvent e) {
		System.out.println("got game full event");
		connected = false;
	}
	/**
	 * Disconnects from server at the end of the game
	 * @param e
	 */
	private void endGame(GameFinishedEvent e){
		connected = false;
	}

}
