package game.core.event.player;

import game.core.util.Coordinates;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class PlayerMovedEvent extends PlayerEvent {

    public final Coordinates coords;

    public PlayerMovedEvent(Coordinates coords, String playerName) {
        super(playerName);
        this.coords = coords;
    }
}
