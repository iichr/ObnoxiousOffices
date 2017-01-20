package game.core.world.tile;

import game.core.player.Player;
import game.core.world.Location;

/**
 * Created by samtebbs on 20/01/2017.
 */
public class Tile {

    public final Location location;
    public final TileType type;

    public Tile(Location location, TileType type) {
        this.location = location;
        this.type = type;
    }

    public void onWalkOver(Player player) {
        type.onWalkOver(player);
    }

    public void onInteraction(Player player) {
        type.onInteraction(player);
    }

}
