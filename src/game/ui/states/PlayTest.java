package game.ui.states;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import game.core.player.Player;
import game.core.test.Test;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.ui.interfaces.Vals;

public class PlayTest extends Play {

	public PlayTest(int state) {
		super(state);
	}

	@Override
	public int getID() {
		return Vals.PLAY_TEST_STATE;
	}

	/**
	 * Generates a fake world and set of players to be used for testing
	 */
	public void testSetup() {
		// testing methods
		int noPlayers = 6;
		World w = createWorld(noPlayers);
		w = addPlayers(w);

		this.world = w;
		this.localPlayerName = Test.localPlayer;
		World.world = w;
	}

	/**
	 * Testing method used to create a fake world
	 * 
	 * @param noPlayers
	 *            the number of player in the game
	 * @return The world
	 */
	private World createWorld(int noPlayers) {
		World w = null;
		Path p = Paths.get("data/office4Player.level");
		if (noPlayers == 6) {
			p = Paths.get("data/office6Player.level");
		}
		try {
			w = World.load(p, noPlayers);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return w;
	}

	/**
	 * Testing method used to create a fake set of players
	 * 
	 * @param w
	 *            The world
	 * @param noPlayers
	 *            The number of players to be made
	 * @return The world
	 */
	private World addPlayers(World w) {
		Random r = new Random();
		int x = r.nextInt(w.xSize);
		int y = r.nextInt(w.ySize - 1);
		Location l = new Location(x, y, w);

		Player testPlayer = new Player("Test_Player", Direction.SOUTH, l);
		testPlayer.setHair(Player.BLONDE);

		localPlayerName = testPlayer.name;
		Test.localPlayer = localPlayerName;
		w.addPlayer(testPlayer);
		return w;
	}
}
