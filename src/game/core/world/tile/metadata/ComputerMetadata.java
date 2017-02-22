package game.core.world.tile.metadata;

import game.core.util.Coordinates;

/**
 * Created by samtebbs on 22/02/2017.
 */
public class ComputerMetadata extends TileMetadata {

    public final String owningPlayer;

    public ComputerMetadata(Coordinates tile, String owningPlayer) {
        super(tile);
        this.owningPlayer = owningPlayer;
    }

}
