package game.core.minigame;

import game.core.event.minigame.MiniGameEndedEvent;
import game.core.player.Player;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 22/03/2017.
 */
class MiniGameTest {

    String player = "sam";
    MiniGame game = new MiniGameHangman(player);

    @Test
    void addStat() {
        game.addStat(player, MiniGame.SCORE, 1);
        assertEquals(1, game.getStat(player, MiniGame.SCORE));
    }

    @Test
    void setStat() {
        game.setStat(player, MiniGame.SCORE, 2);
        assertEquals(2, game.getStat(player, MiniGame.SCORE));
    }

    @Test
    void addPlayer() {
        wrapped("bob", game::hasPlayer, game::addPlayer, game.getPlayers()::remove);
    }

    @Test
    void getStat() {
        setStat();
    }

    @Test
    void getPlayers() {
       game.addPlayer("bob");
        assertEquals(Arrays.asList(player, "bob"), game.getPlayers());
        game.getPlayers().remove("bob");
        assertEquals(Collections.singletonList(player), game.getPlayers());
    }

    @Test
    void ended() {
        wrapped(game::ended, game::end);
    }

    @Test
    void hasPlayer() {
        assertTrue(game.hasPlayer(player));
    }

    @Test
    void isLocal() {
        Player.localPlayerName = "adam";
        assertFalse(game.isLocal());
    }

    @Test
    void isPlaying() {
        wrapped("bianca", game::isPlaying, game::addPlayer);
    }

    @Test
    void onEnd() {
        Consumer<MiniGameEndedEvent> c = e -> {};
        game.onEnd(addLambda(c));
        game.end();
        assertCalled(c);
    }

}