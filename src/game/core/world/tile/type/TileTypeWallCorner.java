package game.core.world.tile.type;

/**
 * Created by samtebbs on 31/01/2017.
 */
public class TileTypeWallCorner extends TileTypeBasic {

    public TileTypeWallCorner(int id) {
        super(id);
    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

}
