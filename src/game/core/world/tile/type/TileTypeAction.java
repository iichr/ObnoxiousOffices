package game.core.world.tile.type;

import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.player.action.PlayerAction;
import game.core.world.tile.Tile;

import java.util.Arrays;
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
    public void onInteraction(Player player, Tile tile) {
        PlayerAction action = getAction(player, tile);
        if(!player.status.hasAction(action.getClass()) && getRequiredStates().stream().allMatch(player.status::hasState)) player.status.addAction(action);
    }

    protected abstract PlayerAction getAction(Player player, Tile tile);
}
