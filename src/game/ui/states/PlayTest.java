package game.ui.states;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.newdawn.slick.Input;

import game.core.Input.InputType;
import game.core.event.Events;
import game.core.event.PlayerInputEvent;
import game.core.player.Player;
import game.core.test.Test;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.ui.interfaces.Vals;

public class PlayTest extends Play{

	public PlayTest(int state) {
		super(state);
		testSetup();
	}
	
	@Override
	public int getID() {
		return Vals.PLAY_TEST_STATE;
	}

	/**
	 * Generates a fake world and set of players to be used for testing
	 */
	private void testSetup() {
		// testing methods
		int noPlayers = 6;
		World w = createWorld(noPlayers);
		w = addPlayers(w, noPlayers);

		playSetup(w);
	}
	
	@Override
	public void playSetup(World world) {
		this.world = world;
		this.localPlayerName = Player.localPlayerName;
		
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
		World.world = w;
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
	private World addPlayers(World w, int noPlayers) {
		Random r = new Random();
			int x = r.nextInt(w.xSize);
			int y = r.nextInt(w.ySize - 1);
			Location l = new Location(x, y, w);
			Player testPlayer = new Player("Test_Player", Direction.SOUTH, l);
			testPlayer.setHair(Player.BLONDE);
			Test.localPlayer = testPlayer.name;
			w.addPlayer(testPlayer);
		return w;
	}
}
