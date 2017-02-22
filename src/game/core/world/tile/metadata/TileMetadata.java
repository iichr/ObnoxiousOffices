package game.core.world.tile.metadata;

import game.core.event.Event;
import game.core.event.MiniGameVarChangedEvent;
import game.core.event.TileMetadataUpdatedEvent;
import game.core.util.Coordinates;
import game.core.util.DataHolder;

/**
 * Created by samtebbs on 22/02/2017.
 */
public abstract class TileMetadata extends DataHolder {

    public final Coordinates tile;

    protected TileMetadata(Coordinates tile) {
        this.tile = tile;
    }

    @Override
    protected Event getUpdateEvent(String var, Object val) {
        return new TileMetadataUpdatedEvent(tile, var, val);
    }

}
