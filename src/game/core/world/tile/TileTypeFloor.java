package game.core.world.tile;

/**
 * Created by samtebbs on 23/01/2017.
 */
public class TileTypeFloor extends TileTypeBasic {

    public TileTypeFloor(int id) {
        super(id);
    }

    @Override
    public boolean canWalkOver() {
        return true;
    }

}
