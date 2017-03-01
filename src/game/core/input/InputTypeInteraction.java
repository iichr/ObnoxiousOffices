package game.core.input;

/**
 * Created by samtebbs on 01/03/2017.
 */
public class InputTypeInteraction extends InputType {

    public final InteractionType type;

    public InputTypeInteraction(InteractionType type) {
        this.type = type;
    }

    @Override
    public boolean isMovement() {
        return false;
    }
}
