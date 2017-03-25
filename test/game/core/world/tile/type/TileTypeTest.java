package game.core.world.tile.type;

import game.core.world.Direction;
import game.core.world.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 25/03/2017.
 */
class TileTypeTest {
    @Test
    void addTileType() {
        System.out.println(TileType.types);
        wrapped(TileType.COMPUTER, t -> TileType.getType("c") == t, t -> TileType.addTileType("c", t));
    }

    @Test
    void getTiles() {
        assertEquals(TileType.SOFA.getTiles(new Location(3, 3), Direction.EAST).size(), 2);
    }

    @Test
    void canWalkOver() {
        assertFalse(TileType.CHAIR.canWalkOver());
        assertTrue(TileType.FLOOR.canWalkOver());
    }

}