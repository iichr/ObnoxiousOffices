package game.core.player.effect;

import game.core.player.PlayerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class PlayerEffectCoffeeBuzzTest {
    @Test
    void update() {
        PlayerEffectCoffeeBuzz e = new PlayerEffectCoffeeBuzz(100, PlayerTest.player);
        e.update();
        assertNotEquals(e.elapsed, 0);
    }

}