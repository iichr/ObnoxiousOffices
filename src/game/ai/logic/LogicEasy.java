package game.ai.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import game.ai.AIPlayer;
import game.ai.pathFinding.Pair;
import game.ai.pathFinding.PathFinding;
import game.core.input.InteractionType.InteractionTypeOther;
import game.core.input.InteractionType.InteractionTypeSit;
import game.core.input.InteractionType.InteractionTypeWork;
import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.player.action.PlayerActionHackTimed;
import game.core.player.action.PlayerActionWorkTimed;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * @author Atanas K. Harbaliev. Created on 18/01/2017
 */

public class LogicEasy implements Logic, Serializable {

	private static final long serialVersionUID = 1L;

	// speed of AI, depends on difficulty
	public int aiSpeed = 500;

	// create a PathFinding object
	public PathFinding pf;

	// paths
	public ArrayList<Pair<Integer, Integer>> toSofa = new ArrayList<Pair<Integer, Integer>>();
	public ArrayList<Pair<Integer, Integer>> toCM = new ArrayList<Pair<Integer, Integer>>();
	public ArrayList<Pair<Integer, Integer>> fromSofa = new ArrayList<Pair<Integer, Integer>>();
	public ArrayList<Pair<Integer, Integer>> fromCM = new ArrayList<Pair<Integer, Integer>>();

	// @Override
	public void aiRefresh(AIPlayer ai) {
		goToCoffeeMachineAndBack(World.world, ai);
	}

	@Override
	public void aiWork(AIPlayer ai) {
		if (!ai.status.hasAction(PlayerActionWorkTimed.class))
			ai.status.addAction(new PlayerActionWorkTimed(ai));
	}

	@Override
	public void findCoffeeMachine(World w, AIPlayer p) {
		fromCM = findPaths(w, p, "cm").get(1);
		toCM = findPaths(w, p, "cm").get(0);
	}

	@Override
	public void findSofa(World w, AIPlayer p) {
		fromSofa = findPaths(w, p, "s").get(1);
		toSofa = findPaths(w, p, "s").get(0);
	}

	public void figureOutFacing(AIPlayer p, Pair<Integer, Integer> pair) {

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
	public void goToCoffeeMachineAndBack(World w, AIPlayer p) {
		findCoffeeMachine(w, p);

		// go through the array list of i, j coords
		// to the coffee machine
		for (int i = 0; i < toCM.size(); i++) {

			// check if you are on the last element, if true - don't do the
			// moving, just the facing
			if (toCM.size() - i == 1)
				figureOutFacing(p, toCM.get(i));
			else {

				// make a move
				move(p, toCM, i);
			}
		}

		// interact with the tile
		Location l = p.getLocation().forward(p.getFacing());
		l.getTile().onInteraction(p, new InteractionTypeOther());

		while (p.status.getAttribute(PlayerAttribute.FATIGUE) != 0) {
			// do nothing
		}

		// back to the dest
		toTheDesk(w, p);
	}

	@Override
	public void goToSofaAndBack(World w, AIPlayer p) {

		// find the sofas on the map
		findSofa(w, p);

		// go through the array list of i, j coords
		// to the sofa
		for (int i = 0; i < toSofa.size(); i++) {

			// check if you are on the last element, if true - don't do the
			// moving, just the facing
			if (toSofa.size() - i == 1)
				figureOutFacing(p, toSofa.get(i));
			else {

				// make a move
				move(p, toSofa, i);
			}
		}

		// interact with the tile
		Location l = p.getLocation().forward(p.getFacing());
		l.getTile().onInteraction(p, new InteractionTypeOther());

		// go back to your desk
		while (p.status.getAttribute(PlayerAttribute.FATIGUE) != 0) {
			// do nothing
		}

		// back to the desk
		toTheDesk(w, p);
	}

	@Override
	public void toTheDesk(World w, AIPlayer p) {

		// check whether the player is at the coffee machine or sofa
		if (p.getLocation().coords.x == fromCM.get(1).getL() && p.getLocation().coords.y == fromCM.get(1).getR()) {
			// if at the coffee machine, go through the array list of i, j
			// coords to the desk from the coffee machine
			for (int i = 2; i < fromCM.size(); i++) {

				// make a move
				move(p, fromCM, i);
			}
		} else {

			// if at the sofa, go through the array list of i, j coords
			// to the desk from the sofa
			for (int i = 1; i < fromSofa.size(); i++) {

				// make a move
				move(p, fromSofa, i);
			}

		}

		// interact with the tile
		Location l = p.getLocation().forward(p.getFacing());
		l.getTile().onInteraction(p, new InteractionTypeSit());

		// interact with the computer and start working
		l = p.getLocation().forward(p.getFacing());
		l.getTile().onInteraction(p, new InteractionTypeWork());
	}

	@Override
	public Player closestToWin(AIPlayer ai) {
		// get all players from the world
		List<Player> players = World.world.getPlayers();

		// choose a random player
		Random r = new Random();
		int random = r.nextInt(players.size());
		Player randomPlayer = players.get(random);

		// if the chosen player is an AI, check again
		while (randomPlayer.isAI && !randomPlayer.name.equals(ai.name)) {
			random = ThreadLocalRandom.current().nextInt(0, players.size());
			randomPlayer = players.get(random);
		}

		return randomPlayer;

	}

	public void hackPlayer(AIPlayer ai, Player player) {
		// if the ai is not hacking anyone, hack
		if (!ai.status.hasAction(PlayerActionHackTimed.class))
			ai.status.addAction(new PlayerActionHackTimed(ai, player));
	}

	@Override
	public void move(AIPlayer ai, ArrayList<Pair<Integer, Integer>> path, int i) {
		// get the right facing
		figureOutFacing(ai, path.get(i));

		// make a move
		ai.moveForwards();

		try {
			// stop the AI from moving, so it doesn't teleport
			Thread.sleep(aiSpeed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<ArrayList<Pair<Integer, Integer>>> findPaths(World w, AIPlayer p, String go) {

		// create fromSomewhere and toSomewhere arrays
		ArrayList<Pair<Integer, Integer>> to = new ArrayList<Pair<Integer, Integer>>();
		ArrayList<Pair<Integer, Integer>> from = new ArrayList<Pair<Integer, Integer>>();
		// create the list of blocks in the grid that represents the path
		ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();
		// create the list for the return
		ArrayList<ArrayList<Pair<Integer, Integer>>> listOfArrayLists = new ArrayList<ArrayList<Pair<Integer, Integer>>>();

		if (go.equals("s")) {

			// call the constructor of PathFinding and run the run() method
			pf = new PathFinding(w, p, "s");
			pf.run();
		} else {

			// call the constructor of PathFinding and run the run() method
			pf = new PathFinding(w, p, "cm");
			pf.run();
		}

		// get the path so it is from goal to start, save it
		path = pf.getPath();
		from = path;

		// create a new arraylist, in which you copy the first one, so you don't
		// copy the first one
		ArrayList<Pair<Integer, Integer>> pathRev = new ArrayList<Pair<Integer, Integer>>();
		pathRev.addAll(path);

		// reverse it, and save
		Collections.reverse(pathRev);
		to = pathRev;

		// add both arrays to the array that will be returned
		listOfArrayLists.add(to);
		listOfArrayLists.add(from);
		return listOfArrayLists;
	}

	@Override
	public void findChair(World w, AIPlayer ai) {
		//get the pathfinding running
		pf = new PathFinding(w, ai,"c");
		pf.run();
		
		// get the path to the chair
		ArrayList<Pair<Integer, Integer>> pathToCHair = new ArrayList<Pair<Integer, Integer>>();
		pathToCHair = pf.getPath();
		
		// do all moves in the ArrayList
		for (int i = 0; i < pathToCHair.size() - 1; i++) {
			move(ai, pathToCHair, i);
			// interact with the tile
			Location l = ai.getLocation().forward(ai.getFacing());
			l.getTile().onInteraction(ai, new InteractionTypeSit());
		}
	}
}
