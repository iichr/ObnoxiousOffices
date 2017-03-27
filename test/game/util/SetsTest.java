package game.util;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 26/03/2017.
 */
class SetsTest {
    @Test
    void asSet() {
        String s = "hi", s2 = "s3";
        assertEquals(Sets.asSet().size(), 0);
        assertEquals(Sets.asSet(s, s2).size(), 2);
    }

}