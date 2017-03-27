package game.core.input;

import game.core.player.PlayerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 25/03/2017.
 */
class InputTypeTest {
    @Test
    void isMovement() {
        assertTrue(new InputTypeMovement(MovementType.MOVE_DOWN).isMovement());
        assertFalse(new InputTypeCharacter('c').isMovement());
        assertFalse(new InputTypeInteraction(new InteractionType.InteractionTypeOther()).isMovement());
        assertFalse(new InputTypeInteraction(new InteractionType.InteractionTypeWork()).isMovement());
        assertFalse(new InputTypeInteraction(new InteractionType.InteractionTypeSit()).isMovement());
        assertFalse(new InputTypeInteraction(new InteractionType.InteractionTypeHack(PlayerTest.player.name)).isMovement());
    }

}