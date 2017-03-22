package game.ai.logic;

import java.io.Serializable;
import java.util.ArrayList;

import game.ai.AIPlayer;
import game.ai.pathFinding.Pair;
import game.core.player.Player;
import game.core.world.World;

/**
 *
 * @author Atanas K. Harabliev. Created on 6/02/2017
 *
 */
public interface Logic extends Serializable {

	/**
	 * Sends the ai to the coffee machine
	 * 
	 * @param ai
	 *            the ai that goes to the coffee machine
	 */
	public void aiRefresh(AIPlayer ai);

	/**
	 * Make the ai work
	 *
	 * @param ai
	 *            the ai that will start working
	 */
	public void aiWork(AIPlayer ai);

	/**
	 * Tells the bot/bots what to do while the player is trying to hack
	 * its/their computer/computers.
	 */
	// public void reactToPlayerHack();

	/**
	 * Finds the shortest path to the coffee machine from the current place
	 *
	 * @param w
	 *            the world
	 * @param ai
	 *            the player that needs to go to the coffee machine
	 */
	public void findCoffeeMachine(World w, AIPlayer ai);

	/**
	 * Finds the shortest path to the Sofa from the current place
	 *
	 * @param w
	 *            the world
	 * @param ai
	 *            the player that needs to go to the coffee machine
	 */
	public void findSofa(World w, AIPlayer ai);

	/**
	 * When player is low on energy, he goes to the coffee machine. It is a fast
	 * way to regenerate energy, but it has a change of giving a coffee buzz
	 * which will lower ones productivity.
	 *
	 *@param w the world where the coffee machine is
	 * @param ai
	 *            player who needs to go to the coffee machine
	 */
	public void goToCoffeeMachineAndBack(World w, AIPlayer ai);

	/**
	 * When player is low on energy, he goes to sleep on the sofa. It is a slow
	 * but safe way to regenerate energy.
	 *
	 * @param ai
	 *            player who needs to go to the coffee machine
	 * @param w
	 *            the world of the player
	 */
	public void goToSofaAndBack(World w, AIPlayer ai);

	/**
	 * Before every move, check if the player on the map is facing in the right
	 * direction. e.g. if you want to move to a tile south of your current one
	 * you must be facing south
	 *
	 * @param ai
	 *            player that needs to move
	 * @param pair
	 *            the pair of coordinates of the tile the player wants to go to
	 */
	public void figureOutFacing(AIPlayer ai, Pair<Integer, Integer> pair);

	/**
	 * Go back to the player's desk
	 *
	 * @param ai
	 *            the player that needs to go back to his desk
	 * @param w
	 *            the world of the player
	 */
	public void toTheDesk(World w, AIPlayer ai);

	/**
	 * Checks all players and the work load they have completed. Then compare
	 * them to the AI palyer.
	 *
	 * @param w
	 *            the world where the players complete
	 * @return the player who is closest to completing his project, and if the
	 *         AI player is closest to the goal, return NULL
	 */
	// public Player closestToWin(World w);

	/**
	 * AI starts hacking another player
	 *
	 * @param ai
	 *            the ai player which will initiate the hacking
	 * @param player
	 *            the player to be hacked
	 * @return the player closest to winning the game
	 */
	public void hackPlayer(AIPlayer ai, Player player);

	/**
	 * Returns a player from the list of players in the world. If the player is
	 * the AI, then no player is closer to winning.
	 *
	 * @param ai
	 *            the AI player that checks if someone else is closer to winning
	 *            the game than it
	 * @return a player from the list of players
	 */
	public Player closestToWin(AIPlayer ai);

	/**
	 * Make the AI move one tile at a time, which is one element from the path
	 * ArrayList
	 * 
	 * @param ai
	 *            the ai player that is moving
	 * 
	 * @param path
	 *            ArrayList of pairs containing the x,y coordinates of the path
	 *            to a given object
	 * @param i
	 *            the index of the element containing the move in the path
	 */
	public void move(AIPlayer ai, ArrayList<Pair<Integer, Integer>> path, int i);

	/**
	 * Finds path to somewhere and back to the initial starting point.
	 * 
	 * @param w
	 *            the world of the player
	 * @param ai
	 *            the player that is going to move
	 * @param go
	 *            the destination of the player; "cm" for coffee machine and "s"
	 *            for sofa
	 * @return array list containing two array lists. The first one is to the
	 *         destination and the second one is from the destination to the
	 *         start point
	 */
	public ArrayList<ArrayList<Pair<Integer, Integer>>> findPaths(World w, AIPlayer p, String go);
	
	/**
	 * When a certain player disconnects, an AI replaces him/her. When this is
	 * done, if the player was not on his chair, find the path to it and sit.
	 * Else, do nothing.
	 * 
	 * @param w
	 *            the world of the player
	 * @param ai
	 *            the ai that needs to move
	 */
	public void findChair(World w, AIPlayer ai);
}