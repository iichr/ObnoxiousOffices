package game.ui.overlay;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Circle;

import game.core.minigame.MiniGame;
import game.core.minigame.MiniGamePong;
import game.core.player.Player;

public class PongOverlay extends PopUpOverlay {

	private List<Player> players;

	public PongOverlay(List<Player> players) throws SlickException {
		super();
		this.players = players;
	}

	@Override
	public void render(Graphics g) {
		MiniGamePong pong = (MiniGamePong) MiniGame.localMiniGame;

		// draw the background
		background.draw(x, y, width, height);

		float playWidth = 3 * width / 4;
		float playHeight = 3 * height / 4;
		float playX = x + (width - playWidth);
		float playY = y + (height - playHeight);

		Rectangle playArea = new Rectangle(playX, playY, playWidth, playHeight);
		g.setColor(Color.black);
		g.draw(playArea);
		
		float paddleWidth = playWidth / ((float) pong.BOUND_X);
		float paddleHeight = playHeight / ((float) pong.BOUND_Y / (float) pong.PADDLE_LEN);
		
		g.setColor(Color.white);
		for(String player: pong.getPlayers()){
			float paddleX = playX + playWidth/(float)pong.getPlayerPos(player).getL();
			float paddleY = playY + playHeight/(float)pong.getPlayerPos(player).getR();
			Rectangle paddle = new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight);
			g.draw(paddle);
		}
		
		float ballDiameter = playWidth / ((float) pong.BOUND_X);
		float ballX = playX + (playWidth/(float)pong.getBallPos().getL()) - ballDiameter/2;
		float ballY = playY + (playHeight/(float)pong.getBallPos().getR()) - ballDiameter/2;
		Circle ball = new Circle(ballX, ballY, ballDiameter);
		g.draw(ball);
	}
}
