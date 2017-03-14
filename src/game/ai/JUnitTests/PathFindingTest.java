package game.ai.JUnitTests;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import game.ai.AIPlayer;
import game.core.world.World;

public class PathFindingTest {
	
	public World world;
	public AIPlayer ai;

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Before
	public void createWorld() {
		world = new World(4, sizeX, sizeY, sizeZ);
		ai = new AIPlayer("Volker", facing, location, mode)
	}

}
