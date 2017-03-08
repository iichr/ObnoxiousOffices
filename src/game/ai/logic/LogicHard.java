package game.ai.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.ai.AIPlayer;
import game.ai.pathFinding.Pair;
import game.ai.pathFinding.PathFinding;
import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.player.action.PlayerActionHack;
import game.core.player.action.PlayerActionWork;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * @author Atanas K. Harbaliev. Created on 18/01/2017
 */

public class LogicHard implements Logic, Serializable {

	private static final long serialVersionUID = 1L;

	// create a PathFinding object
	public PathFinding pf;
	
	//speed of AI, depends on difficulty 
	public int aiSpeed = 250;

	// paths
	public ArrayList<Pair<Integer, Integer>> toBed, toCM, fromBed, fromCM;

	// the variable that counts the consecutive times the coffee machine was used
	int usedCoffeeMachine = 0;
	
	// @Override
	public void aiRefresh(AIPlayer ai) {
		if (usedCoffeeMachine < 3) {
			goToCoffeeMachineAndBack(World.world, ai);
			usedCoffeeMachine++;
		} else {
			goToBedAndBack(World.world, ai);
			usedCoffeeMachine = 0;
		}	
	}

	@Override
	public void aiWork(AIPlayer ai) {
		if(!ai.status.hasAction(PlayerActionWork.class))
			ai.status.addAction(new PlayerActionWork(ai));
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
				Thread.sleep(aiSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// interact with the tile
		 Location l = p.getLocation().forward(p.getFacing());
		 l.getTile().onInteraction(p);

		while (p.status.getAttribute(PlayerAttribute.FATIGUE) != 0) {
			// do nothing
		}

		// back to the dest
		toTheDesk(w, p);
	}

	@Override
	public void goToBedAndBack(World w, Player p) {

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
					Thread.sleep(aiSpeed);
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

		// back to the desk
		toTheDesk(w, p);
	}

	@Override
	public void toTheDesk(World w, Player p) {
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
				
				try {
					Thread.sleep(aiSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {

			// if at the sofa, go through the array list of i, j coords
			// to the desk from the sofa
			for (int i = 2; i < fromBed.size(); i++) {

				// get the right facing
				figureOutFacing(p, fromBed.get(i));

				// make a move
				p.moveForwards();
				
				try {
					Thread.sleep(aiSpeed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		// interact with the tile
		Location l = p.getLocation().forward(p.getFacing());
		l.getTile().onInteraction(p);
		
		//interact with the computer and start working
		l = p.getLocation().forward(p.getFacing());
		l.getTile().onInteraction(p);
	}

	@Override
	public Player closestToWin(AIPlayer ai) {
		// get all players from the world
		List<Player> players = World.world.getPlayers();

		// the player who has done most towards completing his project
		Player winner = null;

		// progress of the current player we are looking at
		double currentPlayerProgress;
		// fatigue of the current player
		double currentPlayerFatigue = 0.0;

		// progress of the player closest to win
		double winnerProgress = -1.0;
		// fatigue of the player closest to win
		double winnerFatigue = 1.0;
		
		//check if there is a player closer to winning than the AI
		for (Player player : players) {
			// get the progress, productivity, and fatigue of the current player in the set 
			currentPlayerProgress = player.getProgress();
			currentPlayerFatigue = player.status.getAttribute(PlayerAttribute.FATIGUE);
			
			// compare the work done, productivity and fatigue each player
			if ((currentPlayerProgress >= winnerProgress
			 && currentPlayerFatigue < winnerFatigue)
			 || (currentPlayerProgress + 10 >= winnerProgress
			  && currentPlayerFatigue < winnerFatigue)) {
				
				// set the current player as the winner player
				winner = player; 
				// set the current player's progress as the highest
				winnerProgress = currentPlayerProgress;
				//set the current player's productivity as the winner's productivity
				//set the current player's fatigue as the winner's fatigue
				winnerFatigue = currentPlayerFatigue;
			} 
		}
		
		//if there is no player closer to winning than AI, return the AI
		if (winner == null) {
			//loop through the player list
			for (Player player : players) {
				//check if the player is the AI
				if (player.isAI && player.name.equals(ai.name))
					//assign the winner to be the AI
					winner = player;
			}
		}
		
		return winner;
	}

	@Override
	public void hackPlayer(AIPlayer ai, Player player) {
		//if the ai and the target are not the same player, and the ai is not hacking anyone, hack
		if (!ai.name.equals(player.name) && !ai.status.hasAction(PlayerActionHack.class))
			player.status.addAction(new PlayerActionHack(ai, player));
	}

}
