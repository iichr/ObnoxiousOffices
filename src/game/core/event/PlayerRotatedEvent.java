package game.core.event;

import game.core.world.Direction;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerRotatedEvent extends PlayerEvent {

    public final Direction newFacing;

    public PlayerRotatedEvent(Direction newFacing, String playerName) {
        super(playerName);
        this.newFacing = newFacing;
    }
}
