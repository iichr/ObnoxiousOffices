package game.core.world.tile.type;

import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.tile.Tile;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by samtebbs on 04/02/2017.
 */
public class TileTypeSofa extends TileType {

    public TileTypeSofa(int id) {
        super(id);
    }

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

    @Override
    public void onInteraction(Player player, Tile tile) {
        player.status.addState(PlayerState.sleeping);
    }

    @Override
    public Collection<Tile> getTiles(Location location, Direction facing) {
        return Arrays.asList(new Tile(location, this, facing, 0), new Tile(location.forward(facing.right()), this, facing, 1));
    }
}
