package game.ui.overlay;

import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.core.player.Player;

public class GameOverOverlay extends PopUpOverlay {

	private List<Player> players;

	/**
	 * Constructor: Sets up overlay
	 * 
	 * @param players
	 *            The list of players
	 * @throws SlickException
	 */
	public GameOverOverlay(List<Player> players) throws SlickException {
		super();
		this.players = players;
	}

	@Override
	public void render(Graphics g) {
		// draw the background
		background.draw(x, y, width, height);

		wg.drawCenter(g, "GAME OVER", x + width / 2, y + height / 2 - height / 3, true, 2 * scale / 3);

		// TODO sort players by progress
		// characters available
		float playerNumber = 0;
		for (Player p : players) {
			wg.drawCenter(g, p.name + "   " + (int) p.getProgress() + "%", x + width / 2,
					y + height / 3 + (height / 9) * playerNumber, true, scale / 4);
			playerNumber++;
		}

		wg.drawCenter(g, "PRESS ANY KEY TO EXIT", x + width / 2, y + height / 2 + height / 3, true, scale / 3);

	}
}
