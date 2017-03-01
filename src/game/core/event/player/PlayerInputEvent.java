package game.core.event.player;

import game.core.Input;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class PlayerInputEvent extends PlayerEvent {

    public final Input.InputType inputType;

    public PlayerInputEvent(Input.InputType inputType, String playerName) {
        super(playerName);
        this.inputType = inputType;
    }
}
