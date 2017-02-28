package game.ai;

import java.util.Set;

import game.ai.logic.LogicEasy;
import game.ai.ruleBasedAI.FireRules;
import game.ai.ruleBasedAI.Rules;
import game.ai.ruleBasedAI.UpdateMemory;
import game.ai.ruleBasedAI.WorkingMemory;
import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * @author Atanas K. Harbaliev Created on 18/01/2017
 */

public class AIPlayer extends Player {
	
	//the working memory for a given player
	public WorkingMemory wm;
	
	//use this to update the working memory
	public UpdateMemory uwm;
	
	//those are the rules
	public Rules rules;
	
	//fire rules object
	public FireRules fr;
	
	//the list of players in the world
	public Set<Player> players = World.world.getPlayers();
	
	//serialVersion to shut eclipse
	private static final long serialVersionUID = 1L;
	
	//constructor from Player class
	public AIPlayer(String name, Direction facing, Location location) {
		super(name, facing, location);
		isAI = true;
		status.initialising = true;
		// set bot attributes
		//set the FATIGUE to 0.85 just for testing the demo for week 6
		//TODO: change FATIGUE TO 0.0, once the presentation is over
		status.setAttribute(PlayerAttribute.FATIGUE, 0.85);
		status.setAttribute(PlayerAttribute.PRODUCTIVITY, 1.0);
		status.initialising = false;
	}
	
	//the logic for the AI player
	public LogicEasy easylogic = new LogicEasy();
	
	/**
	 * createAIPlayer creates an AI bot to play instead of real people.
	 * 
	 * @param name
	 *            the name of the bot
	 * @param dir
	 *            facing of the bot on the map
	 * @param loc
	 *            location on the map
	 * @return a bot
	 */
	public static Player createAIPalyer(String name, Direction dir, Location loc) {
		// calls Player constructor with: name, direction, location
		AIPlayer aiPlayer = new AIPlayer(name, dir, loc);
		// return bot
		return aiPlayer;
	}
	
	// create the working memory, the update working memory object, 
	// the rules and the fire rules object for every player, 
	// then start the fireRules() method for every one of them
	@Override
    public void update() {
		for (Player player: players) {
			wm = new WorkingMemory(); //create the working memory
			uwm = new UpdateMemory(this, player, wm); // create the object to update working memory
			rules = new Rules(); //create the rule set
			fr = new FireRules(this, player, rules, wm, uwm); //create the onject to fire rules
			fr.fireRules(); //start firing rules
		}
    }
}