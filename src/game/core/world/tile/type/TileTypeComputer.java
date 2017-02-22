package game.core.world.tile.type;

import game.core.player.Player;
import game.core.player.action.PlayerAction;
import game.core.player.action.PlayerActionWork;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;

import java.util.Collection;
import java.util.Collections;

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

    @Override
    public Collection<Tile> getTiles(Location location, Direction facing) {
        return Collections.singletonList(new MetaTile(location, this, facing, 0, null));
    }

}
