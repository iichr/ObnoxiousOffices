package game.core.world.tile.type;

import game.core.player.Player;
import game.core.player.action.PlayerAction;
import game.core.player.action.PlayerActionWork;

/**
 * Created by samtebbs on 23/01/2017.
 */
public class TileTypeComputer extends TileTypeAction {
    public TileTypeComputer(int id) {
        super(id, PlayerActionWork.class);
    }

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

    @Override
    protected PlayerAction getAction(Player player) {
        return new PlayerActionWork(player);
    }
}
