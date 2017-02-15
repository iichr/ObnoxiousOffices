package game.AI;

import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * @author Atanas K. Harbaliev Created on 18/01/2017
 */

public class AIPlayer extends Player {
	
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

	public void test(Player p, World w) {
		
	}
	
	//method for presentation in week 6
	//if you are fatigued, find the coffee machine, go there, drink coffee, and go back to the desk
	@Override
    public void update() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (this.status.getAttribute(PlayerAttribute.FATIGUE) > 0.8) {
			easylogic.findCoffeeMachine(this.getLocation().world, this);
			easylogic.goToCoffeeMachine(this.getLocation().world, this);
			if (this.status.getAttribute(PlayerAttribute.FATIGUE) == 0)
				easylogic.toTheDesk(this.getLocation().world, this); 
		}
    }
}
