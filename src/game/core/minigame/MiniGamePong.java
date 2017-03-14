package game.core.minigame;

import game.core.event.player.PlayerInputEvent;
import game.core.input.InputType;
import game.core.input.InputTypeMovement;
import game.util.Pair;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGamePong extends MiniGame2Player {

    public static final String Y_POS = "y", X_POS = "x", BALL_X_VEL = "bvx", BALL_Y_VEL = "bvy";
    public static final int BOUND_Y = 15, BOUND_X = 20;
    public static final float PADDLE_LEN = 5, BALL_SIZE = 1;

    public MiniGamePong(String player1, String player2) {
        super(player1, player2);
        initialising = true;
        newRound();
        initialising = false;
    }

    public Pair<Float, Float> getBallPos() {
        return new Pair<>((float)getVar(X_POS), (float)getVar(Y_POS));
    }

    public Pair<Float, Float> getPlayerPos(String player) {
        return new Pair<>((float)getStat(player, X_POS), (float)getStat(player, Y_POS));
    }

    @Override
    public void update() {
        super.update();
        if(!ended) {
            addVar(X_POS, (float)getVar(BALL_X_VEL));
            addVar(Y_POS, (float)getVar(BALL_Y_VEL));
            float ballX = (float)getVar(X_POS), ballY = (float)getVar(Y_POS);
            if(ballX + BALL_SIZE/2 <= 0) {
                addStat(player1, SCORE, 1);
                newRound();
            } else if(ballX + BALL_SIZE/2>= BOUND_X) {
                addStat(player2, SCORE, 1);
                newRound();
            } else if (ballY + BALL_SIZE/2 >= BOUND_Y || ballY + BALL_SIZE/2 <= 0) bounceBall(BALL_Y_VEL, null, 0);
            else {
                checkPaddleBounce(player2, ballX, ballY);
                checkPaddleBounce(player1, ballX, ballY);
            }
        }
    }

    private void checkPaddleBounce(String player, float ballX, float ballY) {
        float playerX = (float)getStat(player, X_POS), playerY = (float)getStat(player, Y_POS);
        float yDiff = ballY - playerY;
        if(ballX + BALL_SIZE/2 == playerX && yDiff >= 0 && yDiff < PADDLE_LEN) bounceBall(BALL_X_VEL, BALL_Y_VEL, PADDLE_LEN - yDiff);
    }

    private void bounceBall(String varToNegate, String otherVer, float velVal) {
        negVar(varToNegate);
        if(otherVer != null) setVar(otherVer, velVal);
    }

    private void newRound() {
        setStat(player1, X_POS, 1f);
        setStat(player2, X_POS, (float)BOUND_X - 1);
        setStat(player1, Y_POS, 0f);
        setStat(player2, Y_POS, 0f);

        setVar(X_POS, 0f);
        setVar(Y_POS, 0f);
        setVar(BALL_X_VEL, 1f);
        setVar(BALL_Y_VEL, 0f);
    }

    @Override
    public void onInput(PlayerInputEvent event) {
        InputType type = event.inputType;
        String player = event.playerName;
        // Using this check as we may want to process other input types (like interaction)
        if(type.isMovement()) {
            float yAdd = 0;
            switch (((InputTypeMovement) type).type) {
                case MOVE_UP:
                    yAdd = -0.25f;
                    break;
                case MOVE_DOWN:
                    yAdd = 0.25f;
                    break;
            }
            if(yAdd != 0) {
                float newPos = yAdd + getIntStat(player, Y_POS);
                if (newPos < BOUND_Y && newPos > 0) setStat(player, Y_POS, newPos);
            }
        }
    }

}
