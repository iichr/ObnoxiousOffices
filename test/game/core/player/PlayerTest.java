package game.core.player;

import game.core.util.Coordinates;
import game.core.world.Direction;
import game.core.world.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 09/03/2017.
 */
public class PlayerTest {

    public static final Player player = new Player("memer", Direction.SOUTH, new Location(new Coordinates(0, 0, 0), null));

    @Test
    void move() {
        Location loc = player.getLocation();
        player.move(Direction.SOUTH);
        assertEquals(loc.forward(Direction.SOUTH), player.getLocation());
    }

    @Test
    void setFacing() {
        player.setFacing(Direction.NORTH);
        assertEquals(player.getFacing(), Direction.NORTH);
    }

    @Test
    void setLocation() {
        Location loc = new Location(0, 0, 0, null);
        player.setLocation(loc);
        assertEquals(player.getLocation(), loc);
    }

    @Test
    void moveForwards() {
        Location loc = player.getLocation();
        player.moveForwards();
        assertEquals(loc.forward(player.getFacing()), player.getLocation());
    }

    @Test
    void moveBackwards() {
        Location loc = player.getLocation();
        player.moveBackwards();
        assertEquals(loc.forward(player.getFacing().opposite()), player.getLocation());
    }

    @Test
    void rotateLeft() {
        Direction dir = player.getFacing();
        player.rotateLeft();
        assertEquals(dir.left(), player.getFacing());
    }

    @Test
    void rotateRight() {
        Direction dir = player.getFacing();
        player.rotateRight();
        assertEquals(dir.right(), player.getFacing());
    }

    @Test
    void setProgress() {
        player.setProgress(25);
        assertEquals(player.getProgress(), 25);
    }

    @Test
    void setHair() {
        player.setHair(2);
        assertEquals(2, player.getHair());
    }

}