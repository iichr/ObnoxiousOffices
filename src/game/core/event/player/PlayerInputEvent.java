package game.core.event.player;


import game.core.input.InputType;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class PlayerInputEvent extends PlayerEvent {

    public final InputType inputType;

    public PlayerInputEvent(InputType inputType, String playerName) {
        super(playerName);
        this.inputType = inputType;
    }
}
