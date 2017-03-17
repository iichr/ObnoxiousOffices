package game.ui.overlay;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import game.core.minigame.MiniGame;
import game.core.minigame.MiniGamePong;
import game.ui.components.WordGenerator;

public class PongOverlay extends PopUpOverlay {

	/**
	 * Constructor: Sets up overlay
	 * @throws SlickException
	 */
	public PongOverlay(WordGenerator wg) throws SlickException {
		super(wg);
	}

	@Override
	public void render(Graphics g) {
		MiniGamePong pong = (MiniGamePong) MiniGame.localMiniGame;

		// draw the background
		background.draw(x, y, width, height, Color.red);

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
		String p1 = pong.getPlayers().get(0);
		String p2 = pong.getPlayers().get(1);
		
		float paddle1X = playX + unitX * (pong.getPlayerPos(p1).getL() - 1);
		float paddle1Y = playY + unitY * pong.getPlayerPos(p1).getR();
		Rectangle paddle1 = new Rectangle(paddle1X, paddle1Y, paddleWidth, paddleHeight);
		g.fill(paddle1);

		float paddle2X = playX + unitX * pong.getPlayerPos(p2).getL();
		float paddle2Y = playY + unitY * pong.getPlayerPos(p2).getR();
		Rectangle paddle2 = new Rectangle(paddle2X, paddle2Y, paddleWidth, paddleHeight);
		g.fill(paddle2);

		float ballRadius = unitX / 2;
		float ballX = playX + unitX * pong.getBallPos().getL();
		float ballY = playY + unitY * pong.getBallPos().getR();
		Circle ball = new Circle(ballX, ballY, ballRadius);
		g.fill(ball);
	}
}
