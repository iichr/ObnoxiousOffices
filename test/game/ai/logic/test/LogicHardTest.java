package game.ai.logic.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.ai.AIPlayer;
import game.ai.pathFinding.Pair;
import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.sync.Updater;
import game.core.world.Direction;
import game.core.world.World;

public class LogicHardTest {

	public World world;
	public AIPlayer ai;
	public Pair<Integer, Integer> p;
	ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();

	@Test
	public void testRefresh() {

		// start updating the world, so we can check the progress, fatigue, etc.
		try {
			Thread t = new Thread(new Updater(world, 100, true));
			t.start();
		} catch (Exception e) {
		}
		// make the ai work
		ai.getLogic().aiWork(ai);

		// wait for 10sec so the ai can do some work
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// store the fatigue of ai before going to refresh
		double fatigeBeforeRefresh = ai.status.getAttribute(PlayerAttribute.FATIGUE);
		// make ai go refresh
		ai.getLogic().aiRefresh(ai);
		// store the new fatigue of ai after the refreshment
		double fatigueAfterRefresh = ai.status.getAttribute(PlayerAttribute.FATIGUE);

		assertTrue(fatigeBeforeRefresh > fatigueAfterRefresh);
	}

	@Test
	public void testWork() {

		// start updating the world, so we can check the progress, fatigue, etc.
		try {
			Thread t = new Thread(new Updater(world, 100, true));
			t.start();
		} catch (Exception e) {
		}

		double noWorkProgress = 0;
		// make ai work
		ai.getLogic().aiWork(ai);
		// make sure the ai has had the change to progress before doing the
		// assertion
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// store the progress of the ai
		double workProgress = ai.status.getAttribute(PlayerAttribute.FATIGUE);
		assertTrue(noWorkProgress < workProgress);
	}

	@Before
	public void createWorld() {
		// create the world
		try {
			this.world = World.load(Paths.get("data/office" + 2 + "Player.level"), 2);
			World.world = this.world;

		} catch (IOException e) {
			e.printStackTrace();
		}

		// create normal player
		Player p = new Player("Player", Direction.SOUTH, world.getSpawnPoint(0));

		// add them to the world
		World.world.addPlayer(p);

		// create the ai player
		ai = new AIPlayer("Volker", Direction.SOUTH, world.getSpawnPoint(1), "h");
		// add the ai to the world
		World.world.addPlayer(ai);
	}

}
