package game.core.event.player;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerProgressUpdateEvent extends PlayerEvent {

    public final double newVal;

    public PlayerProgressUpdateEvent(double newVal, String playerName) {
        super(playerName);
        this.newVal = newVal;
    }
}
