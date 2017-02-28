package game.core.world.tile.type;

import game.core.player.Player;
import game.core.world.tile.Tile;

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
    public void onInteraction(Player player, Tile tile) {

    }
}
