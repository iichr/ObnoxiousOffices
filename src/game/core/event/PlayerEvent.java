package game.core.event;

/**
 * Created by samtebbs on 11/02/2017.
 */
public abstract class PlayerEvent extends Event {

    public final String playerName;

    public PlayerEvent(String playerName) {
        this.playerName = playerName;
    }
}
