package game.core.world.tile.type;

import game.core.player.Player;
import game.core.world.tile.Tile;

/**
 * Created by samtebbs on 20/01/2017.
 */
public abstract class TileTypeBasic extends TileType {

    public TileTypeBasic(int id) {
        super(id);
    }

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public void onInteraction(Player player, Tile tile) {

    }
}
