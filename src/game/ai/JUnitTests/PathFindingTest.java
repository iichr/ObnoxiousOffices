package game.ai.JUnitTests;

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

public class PathFindingTest {
	
	public World world;
	public AIPlayer ai;
	public Pair<Integer, Integer> p;
	ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();

	@Test
	public void test() {
		//fail("Not yet implemented");
		
		//add all steps to the path
		p = new Pair<Integer, Integer>(13, 7);
		path.add(p);
		//-------------------
		p = new Pair<Integer, Integer>(13, 6);
		path.add(p);
		//-------------------
		p = new Pair<Integer, Integer>(13, 5);
		path.add(p);
		//-------------------
		p = new Pair<Integer, Integer>(13, 4);
		path.add(p);
		//-------------------
		p = new Pair<Integer, Integer>(13, 3);
		path.add(p);
		//-------------------
		p = new Pair<Integer, Integer>(13, 2);
		path.add(p);
		//-------------------
		p = new Pair<Integer, Integer>(13, 1);
		path.add(p);
		//-------------------
		p = new Pair<Integer, Integer>(14, 1);
		path.add(p);
		//-------------------
		p = new Pair<Integer, Integer>(15, 1);
		path.add(p);
			
		System.out.println(path);
		System.out.println(ai.getLogic().findPath(world, ai, "cm").get(0));
		assertTrue(path.toString().contentEquals(ai.getLogic().findPath(world, ai, "cm").get(0).toString()));
	}
	
	@Before
	public void createWorld() {
		//create the world
		try {
			this.world = World.load(Paths.get("data/office" + 2 + "Player.level"), 2);
			World.world = this.world;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create the ai player
		ai = new AIPlayer("Volker", Direction.SOUTH, world.getSpawnPoint(1), "h");
		//create normal player
		Player p = new Player("Player", Direction.SOUTH, world.getSpawnPoint(0));
		
		//add them to the world
		world.addPlayer(p);
		world.addPlayer(ai);
	}

}
