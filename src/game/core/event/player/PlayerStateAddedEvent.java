package game.core.event.player;

import game.core.event.Event;
import game.core.player.Player;
import game.core.player.PlayerState;

/**
 * Created by samtebbs on 28/02/2017.
 */
public class PlayerStateAddedEvent extends PlayerStateEvent {

    public PlayerStateAddedEvent(String name, PlayerState state) {
        super(name, state);
    }
}
