package game.ui.overlay;

import java.util.List;
import java.util.Set;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.core.player.Player;

public class hangmanOverlay extends PopUpOverlay {

	private List<Player> players;

	public hangmanOverlay(List<Player> players) throws SlickException {
		super();
		this.players = players;
	}

	@Override
	public void render(Graphics g) {
		// draw the background
		background.draw(x, y, width, height);
	}
}
