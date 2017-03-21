package game.ai.ruleBasedAI;

import java.io.Serializable;

import game.ai.AIPlayer;
import game.ai.ruleBasedAI.WorkingMemory.activityValues;
import game.core.player.action.PlayerActionDrink;
import game.core.player.action.PlayerActionHack;
import game.core.player.action.PlayerActionHackTimed;
import game.core.player.action.PlayerActionSleep;
import game.core.player.action.PlayerActionWork;
import game.core.player.action.PlayerActionWorkTimed;

/**
 * @author Atanas K. Harbaliev. Created on 22.02.2017
 */
public class UpdateMemory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private AIPlayer ai;
	private WorkingMemory wm;
	
	public UpdateMemory(AIPlayer ai, WorkingMemory wm) {
		this.ai = ai;
		this.wm = wm;
	}

	/**
	 * Monitors a player and updates the information about his actions in the
	 * working memory
	 */
	public void updateInfo() {
		if (wm.getWMplayer().isAI) {
			// might be the wrong way of checking the the player is up to
			if (wm.getWMplayer().status.hasAction(PlayerActionWorkTimed.class)) //checks if the player is currently working
				wm.setAll(activityValues.Yes, activityValues.No, activityValues.No, compareProgress());
			//checks if the player is currently drinking coffee or sleeping on the sofa TODO: change the PlayerActionSleep
			else if (wm.getWMplayer().status.hasAction(PlayerActionDrink.class) || wm.getWMplayer().status.hasAction(PlayerActionSleep.class)) 
				wm.setAll(activityValues.No, activityValues.No, activityValues.Yes, compareProgress());
			//checks if the player is currently hacking someone
			else if (wm.getWMplayer().status.hasAction(PlayerActionHackTimed.class))
				wm.setAll(activityValues.No, activityValues.Yes, activityValues.No, compareProgress());
			else
				wm.setAll(activityValues.No, activityValues.No, activityValues.No, compareProgress());

		} else {
			// might be the wrong way of checking the the player is up to
			if (wm.getWMplayer().status.hasAction(PlayerActionWork.class)) //checks if the player is currently working
				wm.setAll(activityValues.Yes, activityValues.No, activityValues.No, compareProgress());
			//checks if the player is currently drinking coffee or sleeping on the sofa TODO: change the PlayerActionSleep
			else if (wm.getWMplayer().status.hasAction(PlayerActionDrink.class) || wm.getWMplayer().status.hasAction(PlayerActionSleep.class)) 
				wm.setAll(activityValues.No, activityValues.No, activityValues.Yes, compareProgress());
			//checks if the player is currently hacking someone
			else if (wm.getWMplayer().status.hasAction(PlayerActionHack.class))
				wm.setAll(activityValues.No, activityValues.Yes, activityValues.No, compareProgress());
			else
				wm.setAll(activityValues.No, activityValues.No, activityValues.No, compareProgress());

		}
	}

	/**
	 * Checks if a player has done more work towards the goal than the ai player
	 * and updates the working memory accordingly
	 */
	public activityValues compareProgress() {
		//if a given player is closer to winning than ai, put that info in the working memory
		if (ai.getProgress() + 10 <= wm.getWMplayer().getProgress())
			return activityValues.Yes;
		else
			return activityValues.No;
	}
}
