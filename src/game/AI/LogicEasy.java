package game.AI;

import java.util.ArrayList;
import java.util.Collections;

import javax.xml.ws.handler.LogicalHandler;

import game.core.player.Player;
import game.core.world.World;

/**
 * @author Atanas Harbaliev Created on 18/01/2017
 */

public class LogicEasy implements Logic {

	// thresholds for attributes
	public final double energyThreshold = 0.2;

	// create a PathFinding object
	public PathFinding pf;
	
	//paths
	public ArrayList<Integer> toBed, toCM, fromBed, fromCM;


	@Override
	public void reactToPlayerDrink() {
		// TODO
	}


	@Override
	public void reactToPlayerWork() {
		// TODO
	}

	@Override
	public void reactToPlayerHack() {
		// TODO
	}

	@Override
	public void goToCoffeeMachine() {

	}

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

}
