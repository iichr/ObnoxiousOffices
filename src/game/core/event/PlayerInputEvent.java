package game.core.event;

import game.core.Input;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class PlayerInputEvent extends Event {

    public final Input.InputType inputType;

    public PlayerInputEvent(Input.InputType inputType) {
        this.inputType = inputType;
    }
}
