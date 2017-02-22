package game.core.world.tile;

import game.core.event.Event;
import game.core.event.TileMetadataUpdatedEvent;
import game.core.util.DataHolder;

/**
 * Created by samtebbs on 22/02/2017.
 */
public abstract class TileMetadata extends DataHolder {

    public final Tile tile;

    protected TileMetadata(Tile tile) {
        this.tile = tile;
    }

    @Override
    protected Event getUpdateEvent(String var, Object val) {
        return new TileMetadataUpdatedEvent(tile.location.coords, var, val);
    }
}
