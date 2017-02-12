package game.core;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class Input {

    public enum InputType {
        MOVE_UP(true), INTERACT(false), MOVE_DOWN(true), MOVE_LEFT(true), MOVE_RIGHT(true);

        public final boolean isMovement;

        InputType(boolean isMovement) {
            this.isMovement = isMovement;
        }
    }

}
