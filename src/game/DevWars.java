package game;

import game.core.ifc.ClientSync;
import game.networking.Client;
import game.ui.Game;

public class DevWars {

	public static Client client;
	public static boolean isClient = false;

	public static void main(String[] args) {
		isClient = true;
		ClientSync.init();
		client = new Client();
		Game g = new Game("DevWars");
		g.init();
	}

	public static boolean isClient() {
		return isClient;
	}
}
