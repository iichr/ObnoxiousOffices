package game.core.world.tile.type;

import game.core.player.Player;
import game.core.player.action.PlayerAction;
import game.core.player.action.PlayerActionDrink;
import game.core.world.tile.Tile;

/**
 * Created by samtebbs on 27/01/2017.
 */
public class TileTypeCoffeeMachine extends TileTypeAction {

    public TileTypeCoffeeMachine(int id) {
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
    protected PlayerAction getAction(Player player, Tile tile) {
        return new PlayerActionDrink(player);
    }
}
