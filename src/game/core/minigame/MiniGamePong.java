package game.core.minigame;

import game.core.event.player.PlayerInputEvent;
import game.core.input.InputType;
import game.core.input.InputTypeMovement;
import javafx.util.Pair;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGamePong extends MiniGame2Player {

    public static final String Y_POS = "y", X_POS = "x", BALL_X_VEL = "bvx", BALL_Y_VEL = "bvy";
    public static final int BOUND_Y = 10, BOUND_X = 10, PADDLE_LEN = 5, BALL_SIZE = 1;

    public MiniGamePong(String player1, String player2) {
        super(player1, player2);
        newRound();
    }

    public Pair<Integer, Integer> getBallPos() {
        return new Pair<>(getIntVar(X_POS), getIntVar(Y_POS));
    }

    public Pair<Integer, Integer> getPlayerPos(String player) {
        return new Pair<>(getIntStat(player, X_POS), getIntStat(player, Y_POS));
    }

    @Override
    public void update() {
        super.update();
        if(!ended) {
            addVar(X_POS, getIntVar(BALL_X_VEL));
            addVar(Y_POS, getIntVar(BALL_Y_VEL));
            int ballX = getIntVar(X_POS), ballY = getIntVar(Y_POS);
            if(ballX <= 0) {
                addStat(player1, SCORE, 1);
                newRound();
            } else if(ballX >= BOUND_X) {
                addStat(player2, SCORE, 1);
                newRound();
            } else if (ballY >= BOUND_Y || ballY <= 0) bounceBall(BALL_Y_VEL, null, 0);
            else {
                checkPaddleBounce(player2, ballX, ballY);
                checkPaddleBounce(player1, ballX, ballY);
            }
        }
    }

    private void checkPaddleBounce(String player, int ballX, int ballY) {
        int playerX = getIntStat(player, X_POS), playerY = getIntStat(player, Y_POS);
        int yDiff = ballY - playerY;
        if(ballX == playerX && yDiff >= 0 && yDiff < PADDLE_LEN) bounceBall(BALL_X_VEL, BALL_Y_VEL, PADDLE_LEN - yDiff);
    }

    private void bounceBall(String varToNegate, String otherVer, int velVal) {
        negVar(varToNegate);
        if(otherVer != null) setVar(otherVer, velVal);
    }

    private void newRound() {
        setStat(player1, X_POS, 1);
        setStat(player2, X_POS, BOUND_X - 1);
        setStat(player1, Y_POS, 0);
        setStat(player2, Y_POS, 0);

        setVar(X_POS, 0);
        setVar(Y_POS, 0);
        setVar(BALL_X_VEL, 1);
        setVar(BALL_Y_VEL, 0);
    }

    @Override
    public void onInput(PlayerInputEvent event) {
        InputType type = event.inputType;
        String player = event.playerName;
        // Using this check as we may want to process other input types (like interaction)
        if(type.isMovement()) {
            int yAdd = 0;
            switch (((InputTypeMovement) type).type) {
                case MOVE_UP:
                    yAdd = -1;
                    break;
                case MOVE_DOWN:
                    yAdd = 1;
                    break;
            }
            if(yAdd != 0) {
                int newPos = yAdd + getIntStat(player, Y_POS);
                if (newPos < BOUND_Y && newPos > 0) setStat(player, Y_POS, newPos);
            }
        }
    }

}
