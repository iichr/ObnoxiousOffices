package game.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 26/03/2017.
 */
class PairTest {

    Pair<Integer, Integer> p = new Pair<>(3, 7), p2 = new Pair<>(1, 6);

    @Test
    void getL() {
        assertEquals((int)p.getL(), 3);
    }

    @Test
    void getR() {
        assertEquals((int)p.getR(), 7);
    }

    @Test
    void setL() {
        wrapped(0, i -> p.getL() == i, p::setL);
    }

    @Test
    void setR() {
        wrapped(0, i -> p.getR() == i, p::setR);
    }

    @Test
    void equals() {
        assertFalse(p.equals(p2));
        assertTrue(p.equals(new Pair<Integer, Integer>(p.getL(), p.getR())));
    }

}