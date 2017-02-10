package game.core.event;

import game.core.world.World;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class GameStartedEvent extends Event {

    public final World world;

    public GameStartedEvent(World world) {
        this.world = world;
    }
}
