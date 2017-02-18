package game.core.event;

import java.util.List;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameEvent extends Event {
    protected final List<String> players;

    public MiniGameEvent(List<String> players) {
        this.players = players;
    }
}
