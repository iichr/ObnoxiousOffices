package game.core.minigame;

import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeCharacter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class MiniGameHangmanTest {

    MiniGameHangman hangman = new MiniGameHangman("me");

    @Test
    void onInput() {
        hangman.onInput(new PlayerInputEvent(new InputTypeCharacter('c'), "me"));
    }

    @Test
    void getDisplayWord() {
        assertNotNull(hangman.getDisplayWord());
    }

    @Test
    void getEntered() {
        assertTrue(hangman.getEntered().isEmpty());
    }

    @Test
    void getAttemptsLeft() {
        assertEquals(hangman.getAttemptsLeft(), MiniGameHangman.MAX_WRONG);
    }

}