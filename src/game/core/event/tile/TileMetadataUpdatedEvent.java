package game.core.event.tile;

import game.core.event.Event;
import game.core.util.Coordinates;

/**
 * Created by samtebbs on 22/02/2017.
 */
public class TileMetadataUpdatedEvent extends Event {
    public final Object val;
    public final String var;
    public final Coordinates tile;

    public TileMetadataUpdatedEvent(Coordinates tile, String var, Object val) {
        super();
        this.tile = tile;
        this.var = var;
        this.val = val;
    }
}
