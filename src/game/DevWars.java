package game;

import game.core.sync.ClientSync;
import game.networking.Client;
import game.ui.Game;

/**
 * The Game DevWars.
 */
public class DevWars {

	/** The client. */
	public static Client client;
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		ClientSync.init();
		client = new Client();
		Game g = new Game("DevWars");
		g.init();
	}

}
