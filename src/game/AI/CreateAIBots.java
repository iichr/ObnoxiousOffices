package game.ai;

import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * @author Atanas Harbaliev Created on 18/01/2017
 */

public class CreateAIBots {

	public Player aiPlayer;
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
	public Player createAIPalyer(String name, Direction dir, Location loc) {
		// calls Player constructor with: name, direction, location
		aiPlayer = new Player(name, dir, loc);
		// set bot attributes
		aiPlayer.status.setAttribute(PlayerAttribute.FATIGUE, 0.0);
		aiPlayer.status.setAttribute(PlayerAttribute.PRODUCTIVITY, 1.0);
		// return bot
		return aiPlayer;
	}

	//method for presentation in week 6
	//if you are fatigued, find the coffee machine, go there, drink coffee, and go back to the desk
	public void test(Player p, World w) {
		if (p.status.getAttribute(PlayerAttribute.FATIGUE) > 0.8) {
			easylogic.findCoffeeMachine(w, p);
			easylogic.goToCoffeeMachine(w, p);
			easylogic.toTheDesk(w, p); 
		}
	}
}
