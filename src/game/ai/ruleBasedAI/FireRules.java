
package game.ai.ruleBasedAI;

import java.io.Serializable;
import java.util.ArrayList;

import game.ai.AIPlayer;
import game.ai.ruleBasedAI.WorkingMemory.activityValues;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.player.action.PlayerActionHackTimed;
/**
 * This object is going to tell the AI player what to do when certain events occurr.
 */
public class FireRules implements Serializable {

	private static final long serialVersionUID = 1L;
	private AIPlayer ai; // the ai player that is being controled
	private Rules rules; // the rules
	private WorkingMemory wm; // the working memory of the player
	private UpdateMemory uwm; // the update working memory object

	// this is true if the ai is moving around the map
	public boolean isMoving = false;

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
		ArrayList<WorkingMemory> r = rules.getRules(wm); // the arraylist of
															// rules
		// the arraylist of matched rules
		ArrayList<WorkingMemory> matchedRules = new ArrayList<WorkingMemory>();

		// while the game isn't over
		if (ai.getProgress() < 100 && ai.status.getAttribute(PlayerAttribute.FATIGUE) < 0.5) {

			// update the working memory
			uwm.updateInfo();

			// iterate over the rules
			for (int i = 0; i < r.size(); i++) {
				if (r.get(i).getHasProgressedMore() == wm.getHasProgressedMore() && r.get(i).getIsHacking() == wm.getIsHacking()
					&&	r.get(i).getIsRefreshing() == wm.getIsRefreshing() && r.get(i).getIsWorking() == wm.getIsWorking()) {
					matchedRules.add(r.get(i));
				}
			}
			if (!matchedRules.isEmpty()) {
				for (int i = 0; i < matchedRules.size(); i++) {
					WorkingMemory w = matchedRules.get(i);
					// if the monitored player is working and has progressed
					// more than ai - hack him
					if (w.getIsWorking() == activityValues.Yes && w.getHasProgressedMore() == activityValues.Yes) {
						if (!ai.status.hasAction(PlayerActionHackTimed.class))
							ai.getLogic().hackPlayer(ai, wm.getWMplayer());
						// if the monitored player is working and hasn't
						// progressed more than ai - keep doing what you were doing before
					} else if (w.getIsWorking() == activityValues.Yes
							&& w.getHasProgressedMore() == activityValues.No) {
						// if the monitored player is hacking/being hacked and
						// has progressed more than ai - wait for him to finish and then hack him
					} else if (w.getIsHacking() == activityValues.Yes
							&& w.getHasProgressedMore() == activityValues.Yes) {
						while (w.getIsHacking() == activityValues.Yes
								&& ai.status.getAttribute(PlayerAttribute.FATIGUE) < 0.5) {
							uwm.updateInfo();
						}
						// if the ai has enough energy - hack, else go to the CM
						if (ai.status.getAttribute(PlayerAttribute.FATIGUE) < 0.5) {
							if (!ai.status.hasAction(PlayerActionHackTimed.class))
								ai.getLogic().hackPlayer(ai, wm.getWMplayer());
						} else {
							isMoving = true;
							ai.getLogic().aiRefresh(ai);
							isMoving = false;
						}
						// if the monitored player is hacking/being hacked and
						// hasn't progressed more than ai - keep doing what you were doing
					} else if (w.getIsHacking() == activityValues.Yes
							&& w.getHasProgressedMore() == activityValues.No) {
						// if the monitored player is refreshing and has
						// progressed more - wait for him to go back to his desk and hack him
					} else if (w.getIsRefreshing() == activityValues.Yes
							&& w.getHasProgressedMore() == activityValues.Yes) {
						while (w.getIsHacking() == activityValues.Yes
								&& ai.status.getAttribute(PlayerAttribute.FATIGUE) < 0.5) {
							uwm.updateInfo();
						}
						// if the ai has enough energy - hack, else go to the CM
						if (ai.status.getAttribute(PlayerAttribute.FATIGUE) < 0.5) {
							if (!ai.status.hasAction(PlayerActionHackTimed.class))
								ai.getLogic().hackPlayer(ai, wm.getWMplayer());
						} else {
							isMoving = true;
							ai.getLogic().aiRefresh(ai);
							isMoving = false;
						}
						// if the monitored player is refreshing and has not
						// progressed more - keep doing what you were doing
					} else if (w.getIsRefreshing() == activityValues.Yes
							&& w.getHasProgressedMore() == activityValues.No) {
					}
				}
			} else {
				// if no rules were matched, keep doing your work
			}
		}
		// if the ai is being too fatigued, go refresh
		if (ai.status.getAttribute(PlayerAttribute.FATIGUE) > 0.5) {

			// the ai is moving so we need to stop the update method
			isMoving = true;
			ai.getLogic().aiRefresh(ai);
			// the ai is not moving so we need to reset the update method
			isMoving = false;
		}
	}
}