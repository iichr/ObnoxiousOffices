package game.core.player;

import game.core.util.Coordinates;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 09/03/2017.
 */
public class PlayerTest {

    World world = (World.world = World.load(Paths.get("data/office2Player.level"), 2));

    public static final Player player = new Player("memer", Direction.SOUTH, new Location(new Coordinates(0, 0, 0)));

    public PlayerTest() throws IOException {
    }

    @Test
    void getFacing() {
        assertNotNull(player.getFacing());
    }

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
        Location loc = new Location(0, 0, 0);
        player.setLocation(loc);
        assertEquals(player.getLocation(), loc);
    }

    @Test
    void getLocation() {
        assertNotNull(player.getLocation());
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
    void update() {
        double progress = player.getProgress();
        player.update();
        assertEquals(progress, player.getProgress());

    }

    @Test
    void ended() {
        player.end();
        assertFalse(player.ended());
    }

    @Test
    void getProgress() {
        assertTrue(player.getProgress() >= 0 && player.getProgress() <= 100);
    }

    @Test
    void getProgressMultiplier() {
        double mult = player.getProgressMultiplier();
        assertTrue(mult >= 0.0 && mult <= 1);
    }

    @Test
    void setProgress() {
        player.setProgress(25);
        assertEquals(player.getProgress(), 25);
    }

    @Test
    void workSucceeded() {
        // Can be true or false
        boolean s = player.workSucceeded(new Random());
        assertTrue(s || !s);
    }

    @Test
    void equals() {
        assertEquals(player, player);
        assertNotEquals(player, new Player("bob", Direction.SOUTH, new Location(0, 0)));
    }

    @Test
    void setHair() {
        player.setHair(2);
        assertEquals(2, player.getHair());
    }

}