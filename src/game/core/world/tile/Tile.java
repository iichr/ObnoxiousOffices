package game.core.world.tile;

import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;

/**
 * Created by samtebbs on 20/01/2017.
 */
public class Tile {

    public final Location location;
    public final TileType type;
    public final Direction facing;
    public final int multitileID;

    Tile(Location location, TileType type, Direction facing, int multitileID) {
        this.location = location;
        this.type = type;
        this.facing = facing;
        this.multitileID = multitileID;
    }

    Tile(Location location, TileType type, Direction facing) {
        this(location, type, facing, 0);
    }

    public void onWalkOver(Player player) {
        type.onWalkOver(player);
    }

    public void onInteraction(Player player) {
        type.onInteraction(player);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Tile tile = (Tile) o;

        if (multitileID != tile.multitileID) return false;
        if (!location.equals(tile.location)) return false;
        if (!type.equals(tile.type)) return false;
        return facing == tile.facing;

    }

    @Override
    public int hashCode() {
        int result = location.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + facing.hashCode();
        result = 31 * result + multitileID;
        return result;
    }
}
