package game.AI;

import java.util.ArrayList;
import java.util.Collections;

import game.core.player.Player;
import game.core.world.World;

/**
 * @author Atanas Harbaliev Created on 18/01/2017
 */

public class LogicEasy implements Logic{

	// thresholds for attributes
	public final double energyThreshold = 0.2;

	// create a PathFinding object
	public PathFinding pf;
	
	//paths
	public ArrayList<Integer> toBed, toCM, fromBed, fromCM;

	/**
	 * Tells the bot/bots what to do while the player is drinking coffee.
	 */
	@Override
	public void reactToPlayerDrink() {
		// TODO
	}

	/**
	 * Tells the bot/bots what to do while the player is working on his own
	 * project.
	 */
	@Override
	public void reactToPlayerWork() {
		// TODO
	}

	/**
	 * Tells the bot/bots what to do while the player is trying to hack
	 * its/their computer/computers.
	 */
	@Override
	public void reactToPlayerHack() {
		// TODO
	}

	/**
	 * If energy is below a certain level, refresh. Will change chance of
	 * getting hyperactive from coffe for different difficulties.
	 */
	@Override
	public void gainEnergy() {

	}

	/**
	 * Finds the shortest path to the coffee machine from the current place
	 * 
	 * @param w
	 *            the world
	 * @param p
	 *            the player that needs to go to the coffee machine
	 * @param cordI
	 *            the i coordinate of the coffee machine
	 * @param cordJ
	 *            the j coordinate of the coffee machine
	 */
	@Override
	public void findCoffeeMachine(World w, Player p, int cordI, int cordJ) {
		// create the list of blocks in the grid that represents the path
		ArrayList<Integer> path = new ArrayList<Integer>();

		// call the constructor of PathFinding and run the run() method
		pf = new PathFinding(w, p, "cm");
		pf.run();

		// get the path so it is from goal to start, save it
		path = pf.getPath();
		fromCM = path;
		
		//reverse it, and save
		Collections.reverse(path);
		toBed = path;
	}

	/**
	 * Finds the shortest path to the bed from the current place
	 * 
	 * @param w
	 *            the world
	 * @param p
	 *            the player that needs to go to the coffee machine
	 * @param cordI
	 *            the i coordinate of the bed
	 * @param cordJ
	 *            the j coordinate of the bed
	 */
	@Override
	public void findBed(World w, Player p, int cordI, int cordJ) {
		// create the list of blocks in the grid that represents the path
		ArrayList<Integer> path = new ArrayList<Integer>();

		// call the constructor of PathFinding and run the run() method
		pf = new PathFinding(w, p, "b");
		pf.run();

		// get the path so it is from goal to start, save it
		path = pf.getPath();
		fromBed = path;
		
		//reverse the path and save it
		Collections.reverse(path);
		toBed = path;

	}

	@Override
	public void goToCoffeeMachine() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void goToBed() {
		// TODO Auto-generated method stub
		
	}
}
