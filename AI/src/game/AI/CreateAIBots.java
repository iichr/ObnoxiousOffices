package game.AI;

/**
 * @author Atanas Harbaliev
 * Created on 18/01/2017
 */

public class CreateAIBots {
	
	public Player aiPlayer;
	
	/**
	 * getAIPlayer creates an AI bot to play instead of real people. 
	 * @param name - the name of the bot
	 * @return a bot 
	 */
	public Player getAIPalyer (String name) {	
		//calls Player constructor with a name and then returns the actual bot
		aiPlayer = new Player(name);
		return aiPlayer;
	}
}
 