package game.core.world;

import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class LocationTest {

    Location loc = new Location(3, 3, 0);
    World world = World.load(Paths.get("data/office2Player.level"), 2);

    LocationTest() throws IOException {
    }

    @Test
    void forward() {
        assertEquals(loc.forward(Direction.NORTH), new Location(3, 2, 0));
    }

    @Test
    void backward() {
        assertEquals(loc.backward(Direction.SOUTH), new Location(3, 2, 0));
    }

    @Test
    void setTile() {
        Tile t = new Tile(loc, TileType.CHAIR, Direction.SOUTH);
        loc.setTile(t);
        assertEquals(t, loc.getTile());
    }

    @Test
    void getTile() {
        setTile();
    }

    @Test
    void checkBounds() {
        assertTrue(loc.checkBounds());
    }

    @Test
    void equals() {
        assertEquals(loc, new Location(3, 3, 0));
    }

    @Test
    void diff() {
        assertEquals(loc.diff(new Location(0, 0, 0)), loc);
    }

    @Test
    void add() {
        assertEquals(loc.add(1, 1, 1), new Location(4, 4, 1));
    }

    @Test
    void neg() {
        assertEquals(loc.neg(), new Location(-3, -3, 0));
    }

}