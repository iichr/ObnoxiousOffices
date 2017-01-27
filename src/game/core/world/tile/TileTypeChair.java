package game.core.world.tile;

import game.core.player.Player;

/**
 * Created by samtebbs on 27/01/2017.
 */
public class TileTypeChair extends TileType {

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

    @Override
    public void onInteraction(Player player) {
        // TODO: Do some stuff
    }

}
