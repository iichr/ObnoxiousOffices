package game.core.player.action;

import game.core.player.Player;
import game.core.player.PlayerTest;
import game.core.world.World;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class PlayerActionTest {

    Player player = PlayerTest.player;
    World world = (World.world = World.load(Paths.get("data/office2Player.level"), 2));

    Map<PlayerAction, Boolean> assertionMap = new HashMap<PlayerAction, Boolean>() {{
        put(new PlayerActionDrink(player), true);
        put(new PlayerActionHack(player, player), true);
        put(new PlayerActionHackTimed(player, player), true);
        put(new PlayerActionSleep(player), true);
        put(new PlayerActionWork(player), true);
        put(new PlayerActionWorkTimed(player), true);
    }};

    PlayerActionTest() throws IOException {
    }

    @Test
    void getDuration() {
        assertTrue(new PlayerActionDrink(player).getDuration() > 0);
        assertTrue(new PlayerActionWorkTimed(player).getDuration() > 0);
        assertTrue(new PlayerActionHackTimed(player, player).getDuration() > 0);
        assertTrue(new PlayerActionSleep(player).getDuration() > 0);
    }

    @Test
    void getMinigame() {
        assertNotNull(new PlayerActionWork(player).getMiniGame());
        assertNotNull(new PlayerActionHack(player, player).getMiniGame());
    }

    @Test
    void allowsMove() {
        assertTrue(assertionMap.entrySet().stream().allMatch(e -> {
            boolean canMove = e.getKey().allowsMove();
            return canMove == e.getValue();
        }));
    }

    @Test
    void getMaxRepetitions() {
        assertTrue(assertionMap.keySet().stream().allMatch(a -> a.getMaxRepetitions(new Random()) >= 0));
    }

    @Test
    void start() {
        assertionMap.keySet().forEach(PlayerAction::start);
    }

    @Test
    void cancel() {
        assertionMap.keySet().forEach(PlayerAction::cancel);
        assertTrue(assertionMap.keySet().stream().allMatch(PlayerAction::ended));
    }

    @Test
    void end() {
        assertionMap.keySet().forEach(a -> {
            a.end();
            assertTrue(a.ended());
        });
    }

    @Test
    void cancelsOnMove() {
        assertionMap.keySet().forEach(a -> assertEquals(a.cancelsOnMove(), a.cancelsOnMove()));
    }

    @Test
    void allowsInteraction() {
        assertionMap.keySet().forEach(a -> assertEquals(a.allowsInteraction(), a.allowsInteraction()));
    }

}