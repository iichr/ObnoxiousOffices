package game.ai.pathFinding.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import game.ai.AIPlayer;
import game.ai.pathFinding.Pair;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.World;

public class PathFindingTestEasy {
	
	public World world;
	public AIPlayer ai;
	public Pair<Integer, Integer> p;
	ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();

	@Test
	public void testGoToCM() {
		// fail("Not yet implemented");

		// add all steps to the path
		p = new Pair<Integer, Integer>(13, 7);
		path.add(p);
		// -------------------
		p = new Pair<Integer, Integer>(13, 6);
		path.add(p);
		// -------------------
		p = new Pair<Integer, Integer>(13, 5);
		path.add(p);
		// -------------------
		p = new Pair<Integer, Integer>(13, 4);
		path.add(p);
		// -------------------
		p = new Pair<Integer, Integer>(13, 3);
		path.add(p);
		// -------------------
		p = new Pair<Integer, Integer>(13, 2);
		path.add(p);
		// -------------------
		p = new Pair<Integer, Integer>(13, 1);
		path.add(p);
		// -------------------
		p = new Pair<Integer, Integer>(14, 1);
		path.add(p);
		// -------------------
		p = new Pair<Integer, Integer>(15, 1);
		path.add(p);

		assertTrue(path.equals(ai.getLogic().findPath(world, ai, "cm").get(0)));
	}
	
	@Test
	public void testBackToDeskFromCM () {
		//check if the ai is going back to its chair from the CM
		Pair<Integer, Integer> toChair = new Pair<Integer, Integer>(13, 7);
		//get the size of the ArrayList path to the desk
		int size = ai.getLogic().findPath(world, ai, "cm").get(1).size() - 1;
		
		assertTrue(toChair.equals(ai.getLogic().findPath(world, ai, "cm").get(1).get(size)));
	}
	
	@Before
	public void createWorld() {
		// create the world
		try {
			this.world = World.load(Paths.get("data/office" + 2 + "Player.level"), 2);
			World.world = this.world;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
