package game.core.world.tile;

import game.core.player.Player;

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
    public void onInteraction(Player player) {

    }
}
