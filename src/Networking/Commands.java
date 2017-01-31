package Networking;
/**
 * SHARED OBJECTS
 * Used to represent a single commands
 */
public class Commands {
	private String player;
	private Object command;
	/**
	 * Constructor
	 * @param player The specific player
	 * @param command The command to send
	 */
	public Commands(String player, Object command) {
		this.player = player;
		this.command = command;
	}
	/**
	 * Get the player
	 * @return The player name
	 */
	public String getplayer() {
		return player;
	}
	/**
	 * Get the command
	 * @return The command
	 */
	public Object getCommand() {
		return command;
	}
}