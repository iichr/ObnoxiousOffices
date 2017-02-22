package game.ai.logic;

import java.util.Set;

import game.ai.AIPlayer;
import game.ai.logic.WorkingMemory.activityValues;
import game.core.player.Player;
import game.core.player.action.PlayerActionDrink;
import game.core.player.action.PlayerActionHack;
import game.core.player.action.PlayerActionWork;
import game.core.world.World;

/**
 * @author Atanas K. Harbaliev. Created on 22.02.2017
 */
public class Rules {

	/**
	 * Monitors a player and updates the information about his actions in the
	 * working memory
	 * 
	 * @param ai
	 *            the ai player that is monitoring the normal player
	 * @param p
	 *            the normal player that is being monitored
	 * @param w
	 *            the working memory
	 */
	public void updateInfo(AIPlayer ai, Player p, WorkingMemory w) {
		// might be the wrong way of checking the the player is up to
		if (p.status.getActions().contains(PlayerActionWork.class)) //checks if the player is currently working
			w.setAll(activityValues.Yes, activityValues.No, activityValues.No, w.getHasProgressedMore());
		//checks if the player is currently drinking coffee or sleeping on the sofa TODO: change the PlayerActionSleep
		else if (p.status.getActions().contains(PlayerActionDrink.class) || p.status.getActions().contains(PlayerActionSleep.class)) 
			w.setAll(activityValues.No, activityValues.No, activityValues.Yes, w.getHasProgressedMore());
		//checks if the player is currently hacking someone
		else if (p.status.getActions().contains(PlayerActionHack.class))
			w.setAll(activityValues.No, activityValues.Yes, activityValues.No, w.getHasProgressedMore());
		else
			w.setAll(activityValues.No, activityValues.No, activityValues.No, w.getHasProgressedMore());
	}

	/**
	 * Checks if a player has done more work towards the goal than the ai player
	 * and updates the working memory accordingly
	 * 
	 * @param ai
	 *            the ai player
	 * @param p
	 *            the real player
	 * @param w
	 *            the working memory
	 */
	public void compareProgress(AIPlayer ai, Player p, WorkingMemory w) {
		//if a given player is closer to winning than ai, put that info in the working memory
		if (ai.getProgress() <= p.getProgress())
			w.setHasProgressedMore(activityValues.No);
		else
			w.setHasProgressedMore(activityValues.Yes);
	}

	/**
	 * Checks who is the player with highest progress, doesn't include the ai
	 * player
	 * 
	 * @param w
	 *            the world with all players in it
	 * @return the player closest to winning the game
	 */
	public Player playerToHack(World w) {
		Player playerToHack = null; // the player that will be returned
		double maxProgr = 0; 

		Set<Player> playerSet = w.getPlayers(); // all players in the world

		//go through all non-ai players and get the one with the highest progress so far
		for (Player p : playerSet) {
			if (!p.isAI && p.getProgress() > maxProgr) {
				maxProgr = p.getProgress();
				playerToHack = p;
			}
		}

		return playerToHack;
	}
}
