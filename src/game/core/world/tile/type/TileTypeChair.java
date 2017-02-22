package game.core.world.tile.type;

import game.core.player.Player;

/**
 * Created by samtebbs on 27/01/2017.
 */
public class TileTypeChair extends TileType {

    public TileTypeChair(int id) {
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
        // TODO: Do some stuff
    }

}