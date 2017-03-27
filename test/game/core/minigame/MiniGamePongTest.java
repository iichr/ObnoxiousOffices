package game.core.minigame;

import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeMovement;
import game.core.input.MovementType;
import game.util.Pair;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class MiniGamePongTest {

    MiniGamePong pong = new MiniGamePong("me", "you");

    @Test
    void getBallPos() {
        Pair<Float, Float> pos = pong.getBallPos();
        assertTrue(pos.getL() <= MiniGamePong.BOUND_X && pos.getL() >= 0);
        assertTrue(pos.getR() <= MiniGamePong.BOUND_Y && pos.getR() >= 0);
    }

    @Test
    void getPlayerPos() {
        Pair<Float, Float> pos = pong.getPlayerPos("me");
        assertTrue(pos.getL() <= MiniGamePong.BOUND_X && pos.getL() >= 0);
        assertTrue(pos.getR() <= MiniGamePong.BOUND_Y && pos.getR() >= 0);

        Pair<Float, Float> pos2 = pong.getPlayerPos("you");
        assertTrue(pos2.getL() <= MiniGamePong.BOUND_X && pos2.getL() >= 0);
        assertTrue(pos2.getR() <= MiniGamePong.BOUND_Y && pos2.getR() >= 0);
    }

    @Test
    void bounceBall() {
        pong.bounceBall(MiniGamePong.BALL_X_VEL, MiniGamePong.BALL_Y_VEL, 0);
    }

    @Test
    void checkPaddleBounce() {
        pong.checkPaddleBounce("me", true, 0, 0);
    }

    @Test
    void newRound() {
        pong.newRound();
    }

    @Test
    void update() {
        Pair<Float, Float> pos = pong.getBallPos();
        pong.update();
        assertNotEquals(pos, pong.getBallPos());
    }

    @Test
    void onInput() {
        pong.onInput(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_DOWN), "me"));
    }

}