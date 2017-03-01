package game.ai.ruleBasedAI;

import java.io.Serializable;
import java.util.ArrayList;

import game.ai.AIPlayer;
import game.ai.ruleBasedAI.WorkingMemory.activityValues;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.world.World;

public class FireRules implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private AIPlayer ai; //the ai player that is being controled
	private Rules rules; // the rules 
	private WorkingMemory wm; // the working memory of the player
	private UpdateMemory uwm; // the update working memory object
	
	public boolean hasHacked = false;

	// constructor
	public FireRules(AIPlayer ai, Rules rules, WorkingMemory wm, UpdateMemory uwm) {
		this.ai = ai;
		this.rules = rules;
		this.wm = wm;
		this.uwm = uwm;
	}

	/**
	 * Update the working memory. Checks if a rules matches the working memory.
	 * If it does, fire the rule, update memory when needed and always check for
	 * the fatigue attribute of the AI 
	 */
	public void fireRules() {
		ArrayList<WorkingMemory> r = rules.getRules(wm); // the arraylist of rules
		// the arraylist of matched rules
		ArrayList<WorkingMemory> matchedRules = new ArrayList<WorkingMemory>();

		// while the game isn't over TODO: find a better solution, maybe with
		// GameOver event
		while (ai.getProgress() < 100 && ai.status.getAttribute(PlayerAttribute.FATIGUE) < 0.8) {

			// update the working memory
			uwm.updateInfo();

			// iterate over the rules
			for (int i = 0; i < r.size(); i++) {
				if (r.get(i) == wm) // check if the rule == working memory
					matchedRules.add(r.get(i));
			}
			for (int i = 0; i < matchedRules.size(); i++) {
				WorkingMemory w = matchedRules.get(i);
				// if the monitored player is working and has progressed more
				// than ai - hack him
				if (w.getIsWorking() == activityValues.Yes && w.getHasProgressedMore() == activityValues.Yes) {
					ai.easylogic.hackPlayer(wm.getWMplayer());
					hasHacked = true; // change the flag to true
					// if the monitored player is working and hasn't progressed
					// more than ai -
					// keep doing what you were doing before
				} else if (w.getIsWorking() == activityValues.Yes && w.getHasProgressedMore() == activityValues.No) {
					// TODO: make sure the ai keeps working
					// if the monitored player is hacking/being hacked and
					// has progressed more than ai -
					// wait for him to finish and then hack him
				} else if (w.getIsHacking() == activityValues.Yes && w.getHasProgressedMore() == activityValues.Yes) {
					while (w.getIsHacking() == activityValues.Yes && ai.status.getAttribute(PlayerAttribute.FATIGUE) < 80) {
						// TODO: make sure the ai keeps working
						uwm.updateInfo();
					}
					// if the ai has enough energy - hack, else go to the CM
					if (ai.status.getAttribute(PlayerAttribute.FATIGUE) < 80) {
						ai.easylogic.hackPlayer(wm.getWMplayer());
						hasHacked = true; // change the flag to true
					} else
						ai.easylogic.goToCoffeeMachineAndBack(World.world, ai);
					// if the monitored player is hacking/being hacked and
					// hasn't progressed more than ai -
				    // keep doing what you were doing
				} else if (w.getIsHacking() == activityValues.Yes && w.getHasProgressedMore() == activityValues.No) {
					// TODO: make sure the ai keeps working
					// if the monitored player is refreshing and has progressed
					// more - wait for him to go back to his desk and hack him
				} else if (w.getIsRefreshing() == activityValues.Yes && w.getHasProgressedMore() == activityValues.Yes) {
					while (w.getIsHacking() == activityValues.Yes && ai.status.getAttribute(PlayerAttribute.FATIGUE) < 80) {
						// TODO: make sure the ai keeps working
						uwm.updateInfo();
					}
					// if the ai has enough energy - hack, else go to the CM
					if (ai.status.getAttribute(PlayerAttribute.FATIGUE) < 80) {
						ai.easylogic.hackPlayer(wm.getWMplayer());
						hasHacked = true; //change the flag to true
					} else
						ai.easylogic.goToCoffeeMachineAndBack(World.world, ai);
					// if the monitored player is refreshing and has not progressed
					// more - keep doing what you were doing
				} else if (w.getIsRefreshing() == activityValues.Yes && w.getHasProgressedMore() == activityValues.No) {
					// TODO: make sure the ai keeps working
				}
			}
		}
		// if the ai is being too fatigued, go refresh
		if (ai.status.getAttribute(PlayerAttribute.FATIGUE) > 0.8) {
			ai.easylogic.aiRefresh(ai);
			hasHacked = true; //change the flag to true
		}
	}
}
