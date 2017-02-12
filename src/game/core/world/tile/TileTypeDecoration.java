package game.core.world.tile;

/**
 * Created by samtebbs on 31/01/2017.
 */
public class TileTypeDecoration extends TileTypeBasic {

    public TileTypeDecoration(int id) {
        super(id);
    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

}
