package game.core.input;

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
    }

}