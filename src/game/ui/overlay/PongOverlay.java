package game.ui.overlay;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

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

		Rectangle playArea = new Rectangle(x + (width - playWidth), y + (height - playHeight), playWidth, playHeight);
		g.draw(playArea);
		
		float paddelWidth = playWidth / ((float) pong.BOUND_X);
		float paddelHeight = playHeight / ((float) pong.BOUND_Y / (float) pong.PADDLE_LEN);
		
		for(String s: pong.getPlayers()){
			//TODO add paddel locations
		}
	}
}
