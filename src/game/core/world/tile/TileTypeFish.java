package game.core.world.tile;

import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by samtebbs on 04/02/2017.
 */
public class TileTypeFish extends TileType {

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

    @Override
    public void onInteraction(Player player) {

    }

    @Override
    public Collection<Tile> getTiles(Location location, Direction facing) {
        return Arrays.asList(new Tile(location, this, facing, 0), new Tile(location.forward(facing.right()), this, facing, 1));
    }
}
