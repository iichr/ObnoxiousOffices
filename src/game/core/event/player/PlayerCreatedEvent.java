package game.core.event.player;

import game.core.event.Event;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class PlayerCreatedEvent extends Event {

	public final String localPlayerName;
	public final int playersLeft;

	public PlayerCreatedEvent(String localPlayerName, int playersLeft) {
		this.localPlayerName = localPlayerName;
		this.playersLeft = playersLeft;
	}
}
