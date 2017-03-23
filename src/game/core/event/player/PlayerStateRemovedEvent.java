package game.core.event.player;

import game.core.player.state.PlayerState;

/**
 * Created by samtebbs on 28/02/2017.
 */
public class PlayerStateRemovedEvent extends PlayerStateEvent {
    public PlayerStateRemovedEvent(String name, PlayerState state) {
        super(name, state);
    }
}
