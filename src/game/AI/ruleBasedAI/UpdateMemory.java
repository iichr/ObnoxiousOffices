package game.ai.ruleBasedAI;

import game.ai.AIPlayer;
import game.ai.ruleBasedAI.WorkingMemory.activityValues;
import game.core.player.Player;
import game.core.player.action.PlayerActionDrink;
import game.core.player.action.PlayerActionHack;
import game.core.player.action.PlayerActionSleep;
import game.core.player.action.PlayerActionWork;

/**
 * @author Atanas K. Harbaliev. Created on 22.02.2017
 */
public class UpdateMemory {
	
	private AIPlayer ai;
	private Player p;
	private WorkingMemory wm;
	
	public UpdateMemory(AIPlayer ai, Player p, WorkingMemory wm) {
		this.ai = ai;
		this.p = p;
		this.wm = wm;
	}

	/**
	 * Monitors a player and updates the information about his actions in the
	 * working memory
	 */
	public void updateInfo() {
		// might be the wrong way of checking the the player is up to
		if (p.status.getActions().contains(PlayerActionWork.class)) //checks if the player is currently working
			wm.setAll(activityValues.Yes, activityValues.No, activityValues.No, wm.getHasProgressedMore());
		//checks if the player is currently drinking coffee or sleeping on the sofa TODO: change the PlayerActionSleep
		else if (p.status.getActions().contains(PlayerActionDrink.class) || p.status.getActions().contains(PlayerActionSleep.class)) 
			wm.setAll(activityValues.No, activityValues.No, activityValues.Yes, wm.getHasProgressedMore());
		//checks if the player is currently hacking someone
		else if (p.status.getActions().contains(PlayerActionHack.class))
			wm.setAll(activityValues.No, activityValues.Yes, activityValues.No, wm.getHasProgressedMore());
		else
			wm.setAll(activityValues.No, activityValues.No, activityValues.No, wm.getHasProgressedMore());
		compareProgress();
	}

	/**
	 * Checks if a player has done more work towards the goal than the ai player
	 * and updates the working memory accordingly
	 */
	public void compareProgress() {
		//if a given player is closer to winning than ai, put that info in the working memory
		if (ai.getProgress() <= p.getProgress())
			wm.setHasProgressedMore(activityValues.No);
		else
			wm.setHasProgressedMore(activityValues.Yes);
	}
}
