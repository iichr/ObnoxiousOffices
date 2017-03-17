package game;

import game.core.sync.ClientSync;
import game.networking.Client;
import game.ui.Game;

public class DevWars {

	public static Client client;
	public static void main(String[] args) {
		ClientSync.init();
		client = new Client();
		Game g = new Game("DevWars");
		g.init();
	}

}
