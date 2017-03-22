package game.ai.ruleBasedAI.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.ai.AIPlayer;
import game.ai.pathFinding.Pair;
import game.ai.ruleBasedAI.WorkingMemory.activityValues;
import game.core.player.Player;
import game.core.player.action.PlayerActionDrink;
import game.core.player.action.PlayerActionHack;
import game.core.player.action.PlayerActionWork;
import game.core.world.Direction;
import game.core.world.World;;

public class FireRulesTest {
	
	public World world;
	public AIPlayer ai;
	public Player p;
	ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();


	@Test
	public void testCase1() {
		//add progress to the player so AI can start firing rules
		while (p.getProgress() < 20) {
			p.status.player.addProgress();
		}
		//
		p.status.addAction(new PlayerActionWork(p));
		
		ai.fr.fireRules();
		assertTrue(ai.wm.getHasProgressedMore() == activityValues.Yes);
		assertTrue(ai.wm.getIsWorking() == activityValues.Yes);
		assertTrue(ai.wm.getWMplayer() == p);
	}
	
	@Test
	public void testCase2() {
		//add progress to the player so AI can start firing rules
		while (p.getProgress() < 20) {
			p.status.player.addProgress();
		}
		//
		p.status.addAction(new PlayerActionDrink(p));
		ai.fr.fireRules();
		assertTrue(ai.wm.getHasProgressedMore() == activityValues.Yes);
		assertTrue(ai.wm.getIsRefreshing() == activityValues.Yes);
		assertTrue(ai.wm.getWMplayer() == p);
	}
	
	@Test
	public void testCase3() {
		//add progress to the AI 
		ai.addProgress();
		//
		p.status.addAction(new PlayerActionWork(p));
		ai.fr.fireRules();
		assertTrue(ai.wm.getHasProgressedMore() == activityValues.No);
		assertTrue(ai.wm.getIsWorking() == activityValues.Yes);
		assertTrue(ai.wm.getWMplayer() == p);
	}
	
	@Test
	public void testCase4() {
		//add progress to the player so AI can start firing rules
		while (p.getProgress() < 20) {
			p.status.player.addProgress();
		}
		p.status.addAction(new PlayerActionHack(p, ai));
		p.status.removeAction(PlayerActionHack.class);
		ai.fr.fireRules();
		assertTrue(ai.wm.getHasProgressedMore() == activityValues.Yes);
		assertTrue(ai.wm.getIsHacking() != activityValues.Yes);
		assertTrue(ai.wm.getWMplayer() == p);
	}
	
	@Test
	public void testCase5() {
		p.status.addAction(new PlayerActionHack(p, ai));
		p.status.removeAction(PlayerActionHack.class);
		ai.fr.fireRules();
		assertTrue(ai.wm.getHasProgressedMore() == activityValues.No);
		assertTrue(ai.wm.getIsHacking() != activityValues.Yes);
		assertTrue(ai.wm.getWMplayer() == p);
	}
	
	@Test
	public void testCase6() {
		//add progress to the player so AI can start firing rules
		ai.addProgress();
		//
		p.status.addAction(new PlayerActionDrink(p));
		ai.fr.fireRules();
		assertTrue(ai.wm.getHasProgressedMore() == activityValues.No);
		assertTrue(ai.wm.getIsRefreshing() == activityValues.Yes);
		assertTrue(ai.wm.getWMplayer() == p);
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
		p = new Player("Player", Direction.SOUTH, world.getSpawnPoint(0));
		
		// add them to the world
		World.world.addPlayer(p);
		
		// create the ai player
		ai = new AIPlayer("Volker", Direction.SOUTH, world.getSpawnPoint(1), "e");
		//add the ai to the world
		World.world.addPlayer(ai);		
	}

}
