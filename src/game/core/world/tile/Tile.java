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

    public Tile(Location location, TileType type, Direction facing) {
        this.location = location;
        this.type = type;
        this.facing = facing;
    }

    public void onWalkOver(Player player) {
        type.onWalkOver(player);
    }

    public void onInteraction(Player player) {
        type.onInteraction(player);
    }

}
