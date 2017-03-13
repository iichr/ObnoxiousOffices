package game.core.world.tile.type;

/**
 * Created by samtebbs on 23/01/2017.
 */
public class TileTypeDoor extends TileTypeBasic {

    public TileTypeDoor(int id) {
        super(id);
    }

    @Override
    public boolean canWalkOver() {
        return true;
    }

}
