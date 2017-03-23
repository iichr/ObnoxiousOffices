package game.core.input;

/**
 * Created by samtebbs on 03/03/2017.
 */
public class InputTypeCharacter extends InputType {

    public final char ch;

    public InputTypeCharacter(char ch) {
        this.ch = ch;
    }

    @Override
    public boolean isMovement() {
        return false;
    }
}
