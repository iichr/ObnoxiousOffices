package game.core.minigame;

import java.io.Serializable;
import java.util.Random;

import game.core.event.player.PlayerInputEvent;
import game.core.input.InputType;
import game.core.input.InputTypeMovement;
import game.util.Pair;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGamePong extends MiniGame2Player implements Serializable {

	public static final String Y_POS = "y", X_POS = "x", BALL_X_VEL = "bvx", BALL_Y_VEL = "bvy";
	public static final int BOUND_Y = 15, BOUND_X = 20;
	public static final float PADDLE_LEN = 5, BALL_SIZE = 1;
	private static final long serialVersionUID = 4633248866421187983L;

	public MiniGamePong(String player1, String player2) {
		super(player1, player2);
		newRound();
	}

	public Pair<Float, Float> getBallPos() {
		return new Pair<>((float) getVar(X_POS), (float) getVar(Y_POS));
	}

	public Pair<Float, Float> getPlayerPos(String player) {
		return new Pair<>((float) getStat(player, X_POS), (float) getStat(player, Y_POS));
	}

	@Override
	public void update() {
		super.update();
		if (!ended) {
			addVar(X_POS, (float) getVar(BALL_X_VEL));
			addVar(Y_POS, (float) getVar(BALL_Y_VEL));
			float ballX = (float) getVar(X_POS), ballY = (float) getVar(Y_POS);
			if (ballX - BALL_SIZE / 2 <= 0) {
				addStat(player1, SCORE, 1);
				newRound();
			} else if (ballX + BALL_SIZE / 2 >= BOUND_X) {
				addStat(player2, SCORE, 1);
				newRound();
			} else if (ballY + BALL_SIZE / 2 >= BOUND_Y || ballY - BALL_SIZE / 2 <= 0) {
				bounceBall(BALL_Y_VEL, null, 0);
			} else {
				checkPaddleBounce(player2, false, ballX, ballY);
				checkPaddleBounce(player1, true, ballX, ballY);
			}
		}
	}

	private void checkPaddleBounce(String player, boolean left, float ballX, float ballY) {
		float playerX = (float) getStat(player, X_POS), playerY = (float) getStat(player, Y_POS);
		float yDiff = ballY - (playerY + PADDLE_LEN / 2);
		if (((ballX + BALL_SIZE / 2 >= playerX && !left) || (ballX - BALL_SIZE / 2 <= playerX && left))
				&& yDiff >= -(PADDLE_LEN / 2) && yDiff < PADDLE_LEN / 2) {
			float ballVel = (float)getVar(BALL_X_VEL);
			setVar(BALL_X_VEL, ballVel + Math.signum(ballVel)*0.2f);
			bounceBall(BALL_X_VEL, BALL_Y_VEL, yDiff);
		}
	}

	private void bounceBall(String varToNegate, String otherVer, float velVal) {
		negVar(varToNegate);
		if (otherVer != null) {
			setVar(otherVer, velVal / 5);
		}
	}

	private void newRound() {
		Random r = new Random();
		float xVel = 1.0f;
		float yVel = r.nextFloat() - 0.5f;

		setStat(player1, X_POS, 1f);
		setStat(player2, X_POS, (float) BOUND_X - 1);
		setStat(player1, Y_POS, 0f);
		setStat(player2, Y_POS, 0f);

		setVar(X_POS, (float) (BOUND_X / 2));
		setVar(Y_POS, (float) (BOUND_Y / 2));
		setVar(BALL_X_VEL, xVel);
		setVar(BALL_Y_VEL, yVel);
	}

	@Override
	public void onInput(PlayerInputEvent event) {
		InputType type = event.inputType;
		String player = event.playerName;
		// Using this check as we may want to process other input types (like
		// interaction)
		if (type.isMovement()) {
			float yAdd = 0;
			switch (((InputTypeMovement) type).type) {
			case MOVE_UP:
				yAdd = -0.5f;
				break;
			case MOVE_DOWN:
				yAdd = 0.5f;
				break;
			}
			if (yAdd != 0) {
				float newPos = yAdd + (float) getStat(player, Y_POS);
				if (newPos > 0 && newPos + PADDLE_LEN < BOUND_Y) {
					setStat(player, Y_POS, newPos);
				}
			}
		}
	}

}
