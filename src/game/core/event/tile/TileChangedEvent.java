package game.core.event.tile;

import game.core.world.tile.Tile;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class TileChangedEvent extends TileEvent {

    public final Tile tile;

    public TileChangedEvent(int x, int y, int z, Tile tile) {
        super(x, y, z);
        this.tile = tile;
    }
}
