package game.core.sync;

import game.core.player.PlayerTest;
import game.util.Time;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class UpdaterTest {

    Updater updater = new Updater(PlayerTest.player, Time.UPDATE_RATE, false);

    @Test
    void start() {
        wrapped(updater, Updater::running, Updater::start, Updater::stop);
    }

    @Test
    void stop() {
        updater.start();
        wrapped(updater, u -> !u.running(), Updater::stop);
    }

    @Test
    void reset() {
        updater.reset();
        assertEquals(updater.getLastUpdate(), 0);
    }

}