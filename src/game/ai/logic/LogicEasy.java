package game.ai.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import game.ai.AIPlayer;
import game.ai.pathFinding.Pair;
import game.ai.pathFinding.PathFinding;
import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.player.action.PlayerActionWork;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * @author Atanas K. Harbaliev. Created on 18/01/2017
 */

public class LogicEasy implements Logic, Serializable {

	private static final long serialVersionUID = 1L;

	// thresholds for attributes
	public final double energyThreshold = 0.2;

	// hack after what %
	public final double hackAfter = 65;

	// create a PathFinding object
	public PathFinding pf;

	// paths
	public ArrayList<Pair<Integer, Integer>> toBed, toCM, fromBed, fromCM;

	// @Override
	public void aiRefresh(AIPlayer ai) {
		ai.easylogic.goToCoffeeMachineAndBack(World.world, ai); // TODO: put
																// some logic
		// behind this
	}

	@Override
	public void aiWork(AIPlayer ai) {
		ai.status.addAction(new PlayerActionWork(ai));
	}

	@Override
	public void reactToPlayerHack() {
		// TODO
	}

	@Override
	public void findCoffeeMachine(World w, Player p) {
		// create the list of blocks in the grid that represents the path
		ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();

		// call the constructor of PathFinding and run the run() method
		pf = new PathFinding(w, p, "cm");
		pf.run();

		// get the path so it is from goal to start, save it
		path = pf.getPath();
		fromCM = path;

		// create a new arraylist, in which you copy the first one, so you don't
		// copy the first one
		ArrayList<Pair<Integer, Integer>> pathRev = new ArrayList<Pair<Integer, Integer>>();
		pathRev.addAll(path);

		// reverse it, and save
		Collections.reverse(pathRev);
		toCM = pathRev;
	}

	@Override
	public void findBed(World w, Player p) {
		// create the list of blocks in the grid that represents the path
		ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();

		// call the constructor of PathFinding and run the run() method
		pf = new PathFinding(w, p, "b");
		pf.run();

		// get the path so it is from goal to start, save it
		path = pf.getPath();
		fromBed = path;

		// create a new arraylist, in which you copy the first one, so you don't
		// copy the first one
		ArrayList<Pair<Integer, Integer>> pathRev = new ArrayList<Pair<Integer, Integer>>();
		pathRev.addAll(path);

		// reverse the path and save it
		Collections.reverse(pathRev);
		toBed = pathRev;

	}

	public void figureOutFacing(Player p, Pair<Integer, Integer> pair) {

		// get the i, j coords of the tile the player is on
		// i == y; j == x;
		Location location = p.getLocation();

		int x = location.coords.x;
		int y = location.coords.y;

		// if the player needs to move down, check his facing
		// change it
		if (y < pair.getR()) {
			p.setFacing(Direction.SOUTH);
			return;
		}

		// if the player needs to move up, check his facing
		// change it
		if (y > pair.getR()) {
			p.setFacing(Direction.NORTH);
			return;
		}

		// if the player needs to move left, check his facing
		// change it
		if (x > pair.getL()) {
			p.setFacing(Direction.WEST);
			return;
		}

		// if the player needs to move right, check his facing
		// change it
		if (x < pair.getL()) {
			p.setFacing(Direction.EAST);
			return;
		}
	}

	@Override
	public void goToCoffeeMachineAndBack(World w, Player p) {

		findCoffeeMachine(w, p);

		Thread move = new Thread(() -> {
			// go through the array list of i, j coords
			// to the coffee machine
			for (int i = 0; i < toCM.size(); i++) {

				// check if you are on the last element, if true - don't do the
				// moving, just the facing
				if (toCM.size() - i == 1)
					figureOutFacing(p, toCM.get(i));
				else {
					// get the right facing
					figureOutFacing(p, toCM.get(i));

					// make a move
					p.moveForwards();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// interact with the tile
			p.status.setAttribute(PlayerAttribute.FATIGUE, 0);
//			Location l = p.getLocation().forward(p.getFacing());
//			System.out.println(l.getTile().type);
//			l.getTile().onInteraction(p);
			
			System.out.println(p.status.getAttribute(PlayerAttribute.FATIGUE));

			while (p.status.getAttribute(PlayerAttribute.FATIGUE) != 0) {
//				System.out.println("doing nothing");
				// do nothing
			}
			
			// back to the dest
			toTheDesk(w, p);
		});

		// start the thread
		move.start();
	}

	@Override
	public void goToBedAndBack(World w, Player p) {

		Thread move = new Thread(() -> {
			// go through the array list of i, j coords
			// to the sofa
			for (int i = 0; i < toBed.size(); i++) {

				// check if you are on the last element, if true - don't do the
				// moving, just the facing
				if (toCM.size() - i == 1)
					figureOutFacing(p, toCM.get(i));
				else {
					// get the right facing
					figureOutFacing(p, toCM.get(i));

					// make a move
					p.moveForwards();

					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// interact with the tile
			Location l = p.getLocation().forward(p.getFacing());
			l.getTile().onInteraction(p);

			// go back to your desk
			while (p.status.getAttribute(PlayerAttribute.FATIGUE) != 0) {
				// do nothing
			}
		});

		// start the thread
		move.start();

		// back to the desk
		toTheDesk(w, p);
	}

	@Override
	public void toTheDesk(World w, Player p) {

		Thread move = new Thread(() -> {
			// check whether the player is at the coffee machine or sofa
			if (toCM.get(toCM.size() - 1) == fromCM.get(0)) {

				// if at the coffee machine, go through the array list of i, j
				// coords
				// to the desk from the coffee machine
				for (int i = 2; i < fromCM.size(); i++) {

					// get the right facing
					figureOutFacing(p, fromCM.get(i));

					// make a move
					p.moveForwards();
				}
			} else {

				// if at the sofa, go through the array list of i, j coords
				// to the desk from the sofa
				for (int i = 2; i < fromBed.size(); i++) {

					// get the right facing
					figureOutFacing(p, fromBed.get(i));

					// make a move
					p.moveForwards();
				}

			}
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// interact with the tile
			Location l = p.getLocation().forward(p.getFacing());
			l.getTile().onInteraction(p);

		});

		// start the thread
		move.start();
	}

	@Override
	public boolean lowEngergy(Player p) {
		if (p.status.getAttribute(PlayerAttribute.FATIGUE) < energyThreshold)
			return true;
		return false;
	}

	@Override
	public Player closestToWin() {
		// get all players from the world
		List<Player> players = World.world.getPlayers();

		// progress of the current player we are looking at
		double currentPlayerProgress;

		// the player who has done most towards completing his project
		Player winner = null;

		double highestProgr = -1; // progress of the player with highest
									// progress

		System.out.println(players);
		for (Player player : players) {

			// get the progress of the current player in the set double
			currentPlayerProgress = player.getProgress();
			// compare the work each player has completed
			if (currentPlayerProgress > highestProgr) {
				winner = player; // set the current player as the winner player
				highestProgr = currentPlayerProgress;
				// set the current player's progress as the highest
			}
		}
		return winner;
	}

	public void hackPlayer(Player player) {
		// TODO: hacking logic
	}
}
