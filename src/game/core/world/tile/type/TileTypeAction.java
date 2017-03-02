package game.core.world.tile.type;

import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.player.action.PlayerAction;
import game.core.world.tile.Tile;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by samtebbs on 15/02/2017.
 */
public abstract class TileTypeAction extends TileType {

    public final Class<? extends PlayerAction> actionClass;

    protected TileTypeAction(int id, Class<? extends PlayerAction> actionClass) {
        super(id);
        this.actionClass = actionClass;
    }

    public Collection<PlayerState> getRequiredStates() {
        return new HashSet<>();
    }

    @Override
    public void onInteraction(Player player, Tile tile) {
        if(!player.status.hasAction(actionClass) && getRequiredStates().stream().allMatch(player.status::hasState)) player.status.addAction(getAction(player, tile));
    }

    protected abstract PlayerAction getAction(Player player, Tile tile);
}
