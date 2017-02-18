package game.core.minigame;

import game.core.Input;
import game.core.event.PlayerInputEvent;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGamePong extends MiniGame2Player {

    public static final String Y_POS = "y", X_POS = "x";
    public static final int BOUND_Y = 10;

    public MiniGamePong(String player1, String player2) {
        super(player1, player2);
        setStat(player1, X_POS, 0);
        setStat(player2, X_POS, 0);
        setStat(player1, Y_POS, 0);
        setStat(player2, Y_POS, 0);
    }

    @Override
    public void onInput(PlayerInputEvent event) {
        Input.InputType type = event.inputType;
        String player = event.playerName;
        // Using this check as we may want to process other input types (like interaction)
        if(type.isMovement) {
            int yAdd = 0;
            switch (type) {
                case MOVE_UP:
                    yAdd = -1;
                    break;
                case MOVE_DOWN:
                    yAdd = 1;
                    break;
            }
            int newPos = yAdd + getStat(player, Y_POS);
            if(newPos < BOUND_Y && newPos > 0) setStat(player, Y_POS, newPos);
        }
    }

}
