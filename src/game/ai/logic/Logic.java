package game.ai.logic;

import java.io.Serializable;

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

	// if player's energy is below the threshold, he needs to go for a refresh
	static double energyThreshold = 0.8;

	// if a player has done more work than the AI, and that player's progress
	// is above 65, the AI must hack that player
	static double hackAfter = 65;

	/**
	 * Tells the bot/bots what to do while the player is drinking coffee.
	 */
	public void aiRefresh(AIPlayer p); // TODO: possibly don't need that

	/**
	 * Make the ai work
	 * @param p the ai that will start working
	 */
	public void aiWork(AIPlayer p);

	/**
	 * Tells the bot/bots what to do while the player is trying to hack
	 * its/their computer/computers.
	 */
	public void reactToPlayerHack();

	/**
	 * Finds the shortest path to the coffee machine from the current place
	 * 
	 * @param w
	 *            the world
	 * @param p
	 *            the player that needs to go to the coffee machine
	 */
	public void findCoffeeMachine(World w, Player p);

	/**
	 * Finds the shortest path to the bed from the current place
	 * 
	 * @param w
	 *            the world
	 * @param p
	 *            the player that needs to go to the coffee machine
	 */
	public void findBed(World w, Player p);

	/**
	 * When player is low on energy, he goes to the coffee machine. It is a fast
	 * way to regenerate energy, but it has a change of giving a coffee buzz
	 * which will lower ones productivity.
	 * 
	 * @param p
	 *            player who needs to go to the coffee machine
	 */
	public void goToCoffeeMachineAndBack(World w, Player p);

	/**
	 * When player is low on energy, he goes to sleep on the sofa. It is a slow
	 * but safe way to regenerate energy.
	 * 
	 * @param p
	 *            player who needs to go to the coffee machine
	 * @param w
	 *            the world of the player
	 */
	public void goToBedAndBack(World w, Player p);

	/**
	 * Checks if a player is low on energy
	 * 
	 * @param p
	 *            the player whose energy is being checked
	 * @return true if the player's energy is below 0.2, otherwise false
	 */
	public boolean lowEngergy(Player p);

	/**
	 * Before every move, check if the player on the map is facing in the right
	 * direction. e.g. if you want to move to a tile south of your current one
	 * you must be facing south
	 * 
	 * @param p
	 *            player that needs to move
	 * @param pair
	 *            the pair of coordinates of the tile the player wants to go to
	 */
	public void figureOutFacing(Player p, Pair<Integer, Integer> pair);

	/**
	 * Go back to the player's desk
	 * 
	 * @param p
	 *            the player that needs to go back to his desk
	 * @param w
	 *            the world of the player
	 */
	public void toTheDesk(World w, Player p);

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
	public void hackPlayer(Player player);

	public Player closestToWin();
}
