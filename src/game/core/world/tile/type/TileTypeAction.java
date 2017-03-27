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

    /**
     * Gets all required states before the action is added
     * @return the states
     */
    public Collection<PlayerState> getRequiredStates() {
        return new HashSet<>();
    }

    @Override
    public void onInteraction(Player player, Tile tile, InteractionType type) {
        PlayerAction action = getAction(player, tile, type);
        if(action != null && !player.status.hasAction(action.getClass()) && getRequiredStates().stream().allMatch(player.status::hasState)) player.status.addAction(action);
    }

    /**
     * Gets the action to add to the player on interaction
     * @param player the player
     * @param tile the tile they interacted with
     * @param type the interaction type
     * @return the action to add
     */
    protected abstract PlayerAction getAction(Player player, Tile tile, InteractionType type);
}
