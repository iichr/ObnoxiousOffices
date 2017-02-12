package game.ai;

import java.util.ArrayList;
import java.util.Collections;

import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * @author Atanas K. Harbaliev. Created on 18/01/2017
 */

public class LogicEasy implements Logic {

	// thresholds for attributes
	public final double energyThreshold = 0.2;

	// create a PathFinding object
	public PathFinding pf;

	// paths
	public ArrayList<Integer> toBed, toCM, fromBed, fromCM;

	// @Override
	public void reactToPlayerDrink() {
		// TODO
	}

	@Override
	public void reactToPlayerWork(World w) {		
	}

	@Override
	public void reactToPlayerHack() {
		// TODO
	}

	@Override
	public void findCoffeeMachine(World w, Player p) {
		// create the list of blocks in the grid that represents the path
		ArrayList<Integer> path = new ArrayList<Integer>();

		// call the constructor of PathFinding and run the run() method
		pf = new PathFinding(w, p, "cm");
		pf.run();

		// get the path so it is from goal to start, save it
		path = pf.getPath();
		fromCM = path;

		// reverse it, and save
		Collections.reverse(path);
		toBed = path;
	}

	@Override
	public void findBed(World w, Player p) {
		// create the list of blocks in the grid that represents the path
		ArrayList<Integer> path = new ArrayList<Integer>();

		// call the constructor of PathFinding and run the run() method
		pf = new PathFinding(w, p, "b");
		pf.run();

		// get the path so it is from goal to start, save it
		path = pf.getPath();
		fromBed = path;

		// reverse the path and save it
		Collections.reverse(path);
		toBed = path;

	}

	public void figureOutFacing(Player p, int i, int j) {

		// get the i, j coords of the tile the player is on
		// i == y; j == x;
		Location location = p.getLocation();
		
		int x = location.x;
		int y = location.y;

		// if the player needs to move down, check his facing
		// change it
		if (y < i)
			p.setFacing(Direction.SOUTH);

		// if the player needs to move up, check his facing
		// change it
		if (y > i)
			p.setFacing(Direction.NORTH);

		// if the player needs to move left, check his facing
		// change it
		if (x > j)
			p.setFacing(Direction.WEST);

		// if the player needs to move right, check his facing
		// change it
		if (x < j)
			p.setFacing(Direction.EAST);
	}

	@Override
	public void goToCoffeeMachine(World w, Player p) {

		// go through the array list of i, j coords
		// to the coffee machine
		for (int i = 0; i < toCM.size(); i++) {

			// get the right facing
			figureOutFacing(p, toCM.get(i), toCM.get(i + 1));

			// make a move
			p.moveForwards();

			// make sure you get the coords of the next tile
			i++;
		}
		//interact with the tile
		w.getTile(p.getLocation().x, p.getLocation().y, 0).onInteraction(p);
		
		//just for the presentation in week 6 TODO: remove that
		p.status.setAttribute(PlayerAttribute.FATIGUE, 0.0);
	}

	@Override
	public void goToBed(World w, Player p) {

		// go through the array list of i, j coords
		// to the sofa
		for (int i = 0; i < toBed.size(); i++) {

			// get the right facing
			figureOutFacing(p, toBed.get(i), toBed.get(i + 1));

			// make a move
			p.moveForwards();

			// make sure you get the coords of the next tile
			i++;
		}
		//interact with the tile
		w.getTile(p.getLocation().x, p.getLocation().y, 0).onInteraction(p);
	}

	@Override
	public void toTheDesk(World w, Player p) {

		// check whether the player is at the coffee machine or sofa
		if (toCM.get(toCM.size() - 2) == fromCM.get(0) && toCM.get(toCM.size() - 1) == fromCM.get(1)) {
			
			// if at the coffee machine, go through the array list of i, j coords
			// to the desk from the coffee machine
			for (int i = 0; i < fromCM.size(); i++) {

				// get the right facing
				figureOutFacing(p, fromCM.get(i), fromCM.get(i + 1));

				// make a move
				p.moveForwards();

				// make sure you get the coords of the next tile
				i++;
			}
		} else {
			
			// if at the sofa, go through the array list of i, j coords
			// to the desk from the sofa
			for (int i = 0; i < fromBed.size(); i++) {

				// get the right facing
				figureOutFacing(p, fromBed.get(i), fromBed.get(i + 1));

				// make a move
				p.moveForwards();

				// make sure you get the coords of the next tile
				i++;
			}
		}
		//interact with the tile
		w.getTile(p.getLocation().x, p.getLocation().y, 0).onInteraction(p);
	}

	@Override
	public boolean lowEngergy(Player p) {
		if (p.status.getAttribute(PlayerAttribute.FATIGUE) < energyThreshold)
			return true;
		return false;
	}

	@Override
	public Player closestToWin(World w) {
		/*
		//get all players from the world
		Set<Player> players = w.getPlayers();
		
		Player winner = null; //the player who has done most towards completing his project
		
		double highestProgr = 0; //progress of the player with highest progress
		
		//compare the work each player has completed
		for (Player player : players) {
			//get the progress of the current player in the set
			double currentPlayerProgress = player.getProgress();
			//if the current player has done more than the previous one, set this player as the winner
			if (currentPlayerProgress > highestProgr) { 
				winner = player; //set the current player as the winner player 
				highestProgr = player.getProgress(); //set the current player's progress as the highest
			}
		}
		
		if
		 */
		return null;
	}



}
