package game.ai.pathFinding.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import game.ai.AIPlayer;
import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.world.Direction;
import game.core.world.World;

public class LogicEasyTest {
	
	public World world;
	public AIPlayer ai;

	@Test
	public void testRefresh() {
		ai.getLogic().aiWork(ai);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//store the fatigue of ai before going to refresh
		double fatigeBeforeRefresh = ai.status.getAttribute(PlayerAttribute.FATIGUE);
		//make ai go refresh
		ai.getLogic().aiRefresh(ai);
		//store the new fatigue of ai after the refreshment
		double fatigueAfterRefresh = ai.status.getAttribute(PlayerAttribute.FATIGUE);
		
		System.out.println("fatigeBeforeRefresh " + fatigeBeforeRefresh);
		System.out.println("fatigueAfterRefresh " + fatigueAfterRefresh);
		
		assertTrue(fatigeBeforeRefresh > fatigueAfterRefresh);
	}
	
	@Test
	public void testWork() {
		double noWorkProgress = 0;
		//make ai work
		ai.getLogic().aiWork(ai);
		//store the progress of the ai
		double workProgress = ai.status.getAttribute(PlayerAttribute.FATIGUE);
		//make sure the ai has had the change to progress before doing the assertion
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("workProgress " + workProgress);
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
		ai = new AIPlayer("Volker", Direction.SOUTH, world.getSpawnPoint(1), "e");
		//add the ai to the world
		World.world.addPlayer(ai);		
	}

}
