package game.ui.overlay;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import game.core.minigame.MiniGame;
import game.core.minigame.MiniGamePong;

public class PongOverlay extends PopUpOverlay {

	public PongOverlay() throws SlickException {
		super();
	}

	@Override
	public void render(Graphics g) {
		MiniGamePong pong = (MiniGamePong) MiniGame.localMiniGame;

		// draw the background
		background.draw(x, y, width, height);

		float playWidth = 3 * width / 4;
		float playHeight = 3 * height / 4;

		float playX = x + (width - playWidth) / 2;
		float playY = y + (height - playHeight) / 2;

		float unitX = playWidth / (float) pong.BOUND_X;
		float unitY = playHeight / (float) pong.BOUND_Y;

		Rectangle playArea = new Rectangle(playX, playY, playWidth, playHeight);
		g.setColor(Color.black);
		g.fill(playArea);

		float paddleWidth = unitX;
		float paddleHeight = unitY * pong.PADDLE_LEN;

		g.setColor(Color.white);
		for (String player : pong.getPlayers()) {
			float paddleX = playX + unitX * pong.getPlayerPos(player).getL();
			float paddleY = playY + unitY * pong.getPlayerPos(player).getR();
			Rectangle paddle = new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);
			g.fill(paddle);
		}

		float ballRadius = unitX / 2;
		float ballX = playX + unitX * pong.getBallPos().getL();
		float ballY = playY + unitY * pong.getBallPos().getR();
		Circle ball = new Circle(ballX, ballY, ballRadius);
		g.fill(ball);
	}
}
