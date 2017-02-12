package game;

import game.core.ifc.Net;
import game.networking.Client;
import game.ui.Game;

public class DevWars {

	public static Client client;

	public static void main(String[] args) {
		Net.initClient();
		client = new Client();
		Game g = new Game("DevWars");
		g.init();
	}
}
