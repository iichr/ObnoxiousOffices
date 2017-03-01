package game.core.input;

/**
 * Created by samtebbs on 01/03/2017.
 */
public class InputTypeMovement extends InputType {

    public final MovementType type;

    public InputTypeMovement(MovementType type) {
        this.type = type;
    }

    @Override
    public boolean isMovement() {
        return true;
    }
}
