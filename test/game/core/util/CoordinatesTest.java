package game.core.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 25/03/2017.
 */
class CoordinatesTest {

    Coordinates coords = new Coordinates(0, 0, 0), coords2 = new Coordinates(1, 2, 3), coords3 = new Coordinates(-1, -2, -3);

    @Test
    void add() {
        assertEquals(coords.add(1, 2, 3), coords2);
    }

    @Test
    void neg() {
        assertEquals(coords2.neg(), coords3);
    }

    @Test
    void diff() {
        assertEquals(coords.diff(coords2), coords3);
    }

}