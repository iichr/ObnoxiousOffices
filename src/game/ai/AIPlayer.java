package game.ai;

import game.ai.logic.Logic;
import game.ai.logic.LogicEasy;
import game.ai.logic.LogicHard;
import game.ai.ruleBasedAI.FireRules;
import game.ai.ruleBasedAI.Rules;
import game.ai.ruleBasedAI.UpdateMemory;
import game.ai.ruleBasedAI.WorkingMemory;
import game.core.event.Event;
import game.core.event.Events;
import game.core.event.player.PlayerJoinedEvent;
import game.core.event.player.PlayerQuitEvent;
import game.core.player.Player;
import game.core.player.action.PlayerActionHack;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * @author Atanas K. Harbaliev Created on 18/01/2017
 */

public class AIPlayer extends Player {

	// the working memory for a given player
	public WorkingMemory wm;

	// use this to update the working memory
	public UpdateMemory uwm;

	// those are the rules
	public Rules rules;

	// fire rules object
	public FireRules fr;

	// the easy mode logic for the AI player
	public LogicEasy logicEasy;

	// the hard mode logic for the AI player
	public LogicHard logicHard;

	// the mode of the AI
	public String mode;

	// serialVersion to shut eclipse
	private static final long serialVersionUID = 1L;

	static {
		Events.on(Events.EventPriority.HIGH, PlayerQuitEvent.class, AIPlayer::onPlayerQuit);
	}

	// constructor from Player class
	/**
	 * Constructor
	 * 
	 * @param name
	 *            name of the AI
	 * @param facing
	 *            the facing of the AI
	 * @param location
	 *            the location of the AI
	 * @param mode
	 *            difficulty of the AI - easy/hard. Write 'e' for easy and 'h'
	 *            for hard
	 */
	public AIPlayer(String name, Direction facing, Location location, String mode) {
		// set the name, facing, and location
		super(name, facing, location);
		// set the AI flag to true
		isAI = true;
		// set the mode of the AI
		this.mode = mode;
		// check for the mode and initialise the correct logic
		if (mode.equals("e"))
			logicEasy = new LogicEasy();
		else
			logicHard = new LogicHard();

		// initialise everything
		initialise();
	}

	public AIPlayer(Player player) {
		this(player.name, player.getFacing(), player.getLocation(), "e");
		this.status = player.status;
		this.setHair(player.getHair());
		this.setProgress(player.getProgress());
		this.timesDrunkCoffee = player.timesDrunkCoffee;
	}

	public void initialise() {
		// get the player who's closest to winning the game
		Player player;
		if (mode.equals("e"))
			player = logicEasy.closestToWin(this);
		else
			player = logicHard.closestToWin(this);

		wm = new WorkingMemory(player); // create the working memory
		// create the object to update working memory
		uwm = new UpdateMemory(this, wm);
		rules = new Rules(); // create the rule set
		// create the object to fire rules
		fr = new FireRules(this, rules, wm, uwm);
	}

	private static void onPlayerQuit(PlayerQuitEvent event) {
		AIPlayer player = new AIPlayer(event.player);
		World.world.removePlayer(event.player);
		World.world.addPlayer(player);
		Events.trigger(new PlayerJoinedEvent(player.name, player), true);
	}
	
	/**
	 * @return the logic object depending on the mode of the AI
	 */
	public Logic getLogic() {
		if (mode.equals("e"))
			return logicEasy;
		return logicHard;

	}

	// create the working memory, the update working memory object,
	// the rules and the fire rules object for every player,
	// then start the fireRules() method for every one of them
	@Override
	public void update() {
		super.update();

		// get the player who's closest to winning the game
		Player player;
		if (this.mode.equals("e"))
			player = logicEasy.closestToWin(this);
		else
			player = logicHard.closestToWin(this);

		wm = new WorkingMemory(player); // create the working memory

		if (!fr.isMoving && !this.status.hasAction(PlayerActionHack.class)) {
			Thread fire = new Thread(() -> {
				fr.fireRules();
			});
			fire.start();
		}
	}
}