package game.ai;

import game.core.player.Player;
import game.core.world.World;

/**
 * 
 * @author Atanas K. Harabliev. Created on 6/02/2017
 *
 */
public interface Logic {

	// if player's energy is below the threshold, he needs to go for a refresh
	static double energyThreshold = 0.2;

	// might need it for something
	static double something = 0.0;

	/**
	 * Tells the bot/bots what to do while the player is drinking coffee.
	 */
	public void reactToPlayerDrink();

	/**
	 * Tells the bot/bots what to do while the player is working on his own
	 * project.
	 */
	public void reactToPlayerWork();

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
	 * @param cordI
	 *            the i coordinate of the bed
	 * @param cordJ
	 *            the j coordinate of the bed
	 */
	public void findBed(World w, Player p, int i, int j);

	/**
	 * When player is low on energy, he goes to the coffee machine. It is a fast
	 * way to regenerate energy, but it has a change of giving a coffee buzz
	 * which will lower ones productivity.
	 * 
	 * @param p
	 *            player who needs to go to the coffee machine
	 */
	public void goToCoffeeMachine(World w, Player p);

	/**
	 * When player is low on energy, he goes to sleep on the sofa. It is a slow
	 * but safe way to regenerate energy.
	 * 
	 * @param p
	 *            player who needs to go to the coffee machine
	 * @param w
	 *            the world of the player
	 */
	public void goToBed(World w, Player p);

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
	 * @param i
	 *            i coordinate of the tile the player wants to go to
	 * @param j
	 *            j coordinate of the tile the player wants to go to
	 */
	public void figureOutFacing(Player p, int i, int j);

	/**
	 * Go back to the player's desk
	 * 
	 * @param p
	 *            the player that needs to go back to his desk
	 * @param w
	 *            the world of the player
	 */
	public void toTheDesk(World w, Player p);
}
