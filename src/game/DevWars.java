package game;

import game.networking.Client;
import game.ui.Game;

public class DevWars {

	public static Client client;

	public static void main(String[] args) {
		Game g = new Game("DevWars");
		client = new Client();
	}
}
