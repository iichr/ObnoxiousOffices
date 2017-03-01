package game.core.event.player;

import game.core.player.PlayerState;

/**
 * Created by samtebbs on 28/02/2017.
 */
public class PlayerStateEvent extends PlayerEvent {

    public final PlayerState state;

    public PlayerStateEvent(String playerName, PlayerState state) {
        super(playerName);
        this.state = state;
    }
}
