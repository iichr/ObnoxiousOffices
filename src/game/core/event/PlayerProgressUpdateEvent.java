package game.core.event;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerProgressUpdateEvent extends PlayerEvent {

    public final double change;

    public PlayerProgressUpdateEvent(double change, String playerName) {
        super(playerName);
        this.change = change;
    }
}
