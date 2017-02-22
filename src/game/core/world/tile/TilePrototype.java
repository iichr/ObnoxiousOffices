package game.core.world.tile;

import game.core.world.Direction;
import game.core.world.tile.type.TileType;

/**
 * Created by samtebbs on 04/02/2017.
 */
public class TilePrototype {

    public final TileType type;
    public final Direction facing;

    public TilePrototype(TileType type, Direction facing) {
        this.type = type;
        this.facing = facing;
    }
}
