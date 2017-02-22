package game.core.world.tile;

import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.tile.type.TileType;

/**
 * Created by samtebbs on 22/02/2017.
 */
public class MetaTile extends Tile {

    public final TileMetadata metadata;

    public MetaTile(Location location, TileType type, Direction facing, int multitileID, TileMetadata metadata) {
        super(location, type, facing, multitileID);
        this.metadata = metadata;
    }

    public MetaTile(Location location, TileType type, Direction facing, TileMetadata metadata) {
        super(location, type, facing);
        this.metadata = metadata;
    }

}
