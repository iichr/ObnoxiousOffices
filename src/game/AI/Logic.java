package game.AI;

import game.core.player.Player;
import game.core.world.World;

public interface Logic {

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
	 * @param cordI
	 *            the i coordinate of the coffee machine
	 * @param cordJ
	 *            the j coordinate of the coffee machine
	 */
	public void findCoffeeMachine(World w, Player p, int i, int j);

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
	 * If energy is below a certain level, refresh. Will change chance of
	 * getting hyperactive from coffe for different difficulties.
	 * 
	 * @param p
	 *            the player that needs to go
	 * @param i
	 *            i coordinate of the closest coffee machine on the map
	 * @param j
	 *            j coordinate of the closest coffee machine on the map
	 */
	public void goToCoffeeMachine(Player p, int i, int j);

	/**
	 * If energy is below a certain level, refresh. Sleeping on the sofa takes
	 * longer than drinking cofee but doesn't have side effects
	 * 
	 * @param p
	 *            the player that needs to sleep
	 * @param i
	 *            i coordinate of the closest sofa on the map
	 * @param j
	 *            j coordinate of the closest sofa on the map
	 */
	public void goToBed(Player p, int i, int j);
}
