package game.core.world.tile;

import game.core.player.Player;

/**
 * Created by samtebbs on 20/01/2017.
 */
public class TileTypeBasic extends TileType {

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public boolean canWalkOver() {
        return true;
    }

    @Override
    public void onInteraction(Player player) {

    }
}
