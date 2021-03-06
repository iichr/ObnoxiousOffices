package game.core.event.player;

import game.core.event.Event;

import java.io.Serializable;

/**
 * Created by samtebbs on 11/02/2017.
 */
public abstract class PlayerEvent extends Event implements Serializable {

    public final String playerName;

    public PlayerEvent(String playerName) {
        this.playerName = playerName;
    }
}
