package game.core.input;

import java.io.Serializable;

/**
 * Created by samtebbs on 10/02/2017.
 */
public abstract class InputType implements Serializable {

    /**
     * True if is a movement input type
     * @return
     */
    public abstract boolean isMovement();

}
