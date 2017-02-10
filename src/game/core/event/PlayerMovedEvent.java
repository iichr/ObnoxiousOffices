package game.core.event;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class PlayerMovedEvent extends Event {

    public final int dX, dY, dZ;
    public final String playerName;

    public PlayerMovedEvent(int dX, int dY, int dZ, String playerName) {
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
        this.playerName = playerName;
    }
}
