package game.core.world.tile;

import game.core.player.Player;
import game.core.player.action.PlayerAction;

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
    public void onInteraction(Player player) {
        if(!player.status.hasAction(actionClass)) player.status.addAction(getAction(player));
    }

    protected abstract PlayerAction getAction(Player player);
}
