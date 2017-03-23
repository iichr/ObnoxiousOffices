package game.core.world.tile.type;

import game.core.input.InteractionType;
import game.core.player.Player;
import game.core.player.state.PlayerState;
import game.core.player.action.PlayerAction;
import game.core.world.tile.Tile;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by samtebbs on 15/02/2017.
 */
public abstract class TileTypeAction extends TileType {


    protected TileTypeAction(int id) {
        super(id);
    }

    public Collection<PlayerState> getRequiredStates() {
        return new HashSet<>();
    }

    @Override
    public void onInteraction(Player player, Tile tile, InteractionType type) {
        PlayerAction action = getAction(player, tile, type);
        if(action != null && !player.status.hasAction(action.getClass()) && getRequiredStates().stream().allMatch(player.status::hasState)) player.status.addAction(action);
    }

    protected abstract PlayerAction getAction(Player player, Tile tile, InteractionType type);
}
