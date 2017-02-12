package game.core.world.tile;

import game.core.player.Player;

/**
 * Created by samtebbs on 23/01/2017.
 */
public class TileTypeDesk extends TileType {
    public TileTypeDesk(int id) {
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
    public void onInteraction(Player player) {

    }
}
