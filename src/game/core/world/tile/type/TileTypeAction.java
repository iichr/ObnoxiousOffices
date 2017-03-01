package game.core.world.tile.type;

import game.core.player.Player;
import game.core.player.action.PlayerAction;
import game.core.world.tile.Tile;

/**
 * Created by samtebbs on 15/02/2017.
 */
public abstract class TileTypeAction extends TileType {

    public final Class<? extends PlayerAction> actionClass;

    protected TileTypeAction(int id, Class<? extends PlayerAction> actionClass) {
        super(id);
        this.actionClass = actionClass;
    }

    @Override
    public void onInteraction(Player player, Tile tile) {
        if(!player.status.hasAction(actionClass)) player.status.addAction(getAction(player));
    }

    protected abstract PlayerAction getAction(Player player);
}
