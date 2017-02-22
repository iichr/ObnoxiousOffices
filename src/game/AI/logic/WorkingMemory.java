package game.ai.logic;

import game.ai.AIPlayer;
import game.core.player.Player;

/**
 * @author Atanas K. Harbaliev. Created on 22.02.2017
 */

public class WorkingMemory {
	enum activityValues {
		Yes, No, Unknown
	}; // Work, Hack, Refresh, Progress, Unknown

	// checks if a player is working
	private activityValues isWorking;

	// checks if a player is hacking someone else
	private activityValues isHacking;

	// checks if a player is refreshing his energy
	private activityValues isRefreshing;

	// checks if a player has progressed more than the AI
	private activityValues hasProgressedMore;
	
	//the player that you will monitor
	@SuppressWarnings("unused")
	private Player player;
	
	//the ai player
	@SuppressWarnings("unused")
	private AIPlayer ai;

	/**
	 * Constructonr
	 * 
	 * @param isW
	 *            the value of the isWorking field
	 * @param isH
	 *            the value of the isHacking field
	 * @param isR
	 *            the value of the isRefreshing field
	 * @param hPM
	 *            the value of the hasProgressedMore field
	 */
	public WorkingMemory(AIPlayer aiP, Player p) {
		ai = aiP;
		player = p;
		setAll(activityValues.Unknown, activityValues.Unknown, activityValues.Unknown, activityValues.Unknown);
	}

	/**
	 * Get method
	 * 
	 * @return answer to whether or not a player is working
	 */
	public activityValues getIsWorking() {
		return isWorking;
	}

	/**
	 * Sets the value of isWorking
	 * 
	 * @param val
	 *            if the player is working - Yes, if he isn't - No, else Unknown
	 */
	public void setIsWorking(activityValues val) {
		isWorking = val;
	}

	/**
	 * Get method
	 * 
	 * @return answer to whether or not a player is hacking
	 */
	public activityValues getIsHacking() {
		return isHacking;
	}

	/**
	 * Sets the value of isWorking
	 * 
	 * @param val
	 *            if the player is hacking - Yes, if he isn't - No, else Unknown
	 */
	public void setIsHacking(activityValues val) {
		isHacking = val;
	}

	/**
	 * Get method
	 * 
	 * @return answer to whether or not a player is drinking coffee or sleeping
	 *         on the sofa
	 */
	public activityValues getIsRefreshing() {
		return isRefreshing;
	}

	/**
	 * Sets the value of isWorking
	 * 
	 * @param val
	 *            if the player is refreshing - Yes, if he isn't - No, else
	 *            Unknown
	 */
	public void setIsRefreshing(activityValues val) {
		isRefreshing = val;
	}

	/**
	 * Get method
	 * 
	 * @return answer to whether or not a player is closer to completing his
	 *         project than the AI
	 */
	public activityValues getHasProgressedMore() {
		return hasProgressedMore;
	}

	/**
	 * Sets the value of isWorking
	 * 
	 * @param val
	 *            if the player is closer to completing the project than the AI
	 *            - Yes, if he isn't - No, else Unknown
	 */
	public void setHasProgressedMore(activityValues val) {
		hasProgressedMore = val;
	}

	/**
	 * Sets the initial values of all fields
	 * 
	 * @param isW
	 *            whether or not the player is working
	 * @param isH
	 *            whether or not the player is hacking
	 * @param isR
	 *            whether or not the player is refreshing
	 * @param hasPM
	 *            whether or not the player has progressed more than the AI
	 *            towards the final goal
	 */
	public void setAll(activityValues isW, activityValues isH, activityValues isR, activityValues hasPM) {
		setIsWorking(isW);
		setIsHacking(isH);
		setIsRefreshing(isR);
		setHasProgressedMore(hasPM);
	}
}
