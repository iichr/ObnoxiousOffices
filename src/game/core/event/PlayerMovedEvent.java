package game.core.event;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class PlayerMovedEvent extends PlayerEvent {

    public final int dX, dY, dZ;

    public PlayerMovedEvent(int dX, int dY, int dZ, String playerName) {
        super(playerName);
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
    }
}
