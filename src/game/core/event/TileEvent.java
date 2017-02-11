package game.core.event;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class TileEvent extends Event {

    public final int x, y, z;

    public TileEvent(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
