package game.core.player.effect;

import game.core.player.PlayerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class PlayerEffectOnFireTest {

    PlayerEffectOnFire e = new PlayerEffectOnFire(100, PlayerTest.player);

    @Test
    void update() {
        e.update();
        assertNotEquals(e.elapsed, 0);
    }

    @Test
    void end() {
        e.end();
        assertTrue(e.ended());
    }

}