package game.ai;

import game.ai.logic.LogicEasy;
import game.ai.ruleBasedAI.FireRules;
import game.ai.ruleBasedAI.Rules;
import game.ai.ruleBasedAI.UpdateMemory;
import game.ai.ruleBasedAI.WorkingMemory;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;

/**
 * @author Atanas K. Harbaliev Created on 18/01/2017
 */

public class AIPlayer extends Player {;

	// the working memory for a given player
	public WorkingMemory wm;

	// use this to update the working memory
	public UpdateMemory uwm;

	// those are the rules
	public Rules rules;

	// fire rules object
	public FireRules fr;

	// serialVersion to shut eclipse
	private static final long serialVersionUID = 1L;

	// constructor from Player class
	public AIPlayer(String name, Direction facing, Location location) {
		super(name, facing, location);
		isAI = true;

		// initialise everything
		initialise();
	}

	// the logic for the AI player
	public LogicEasy easylogic = new LogicEasy();

	public void initialise() {
		// get the player who's closest to winning the game
		Player player = easylogic.closestToWin();

		wm = new WorkingMemory(player); // create the working memory
		// create the object to update working memory
		uwm = new UpdateMemory(this, wm);
		rules = new Rules(); // create the rule set
		// create the object to fire rules
		fr = new FireRules(this, rules, wm, uwm);
	}

	// create the working memory, the update working memory object,
	// the rules and the fire rules object for every player,
	// then start the fireRules() method for every one of them
	@Override
	public void update() {
		super.update();

		// get the player who's closest to winning the game
		Player player = easylogic.closestToWin();

		wm = new WorkingMemory(player); // create the working memory

		if (!fr.isMoving) {
			Thread fire = new Thread(() -> {
				fr.fireRules();
			});
			fire.start();
		}
	}
}