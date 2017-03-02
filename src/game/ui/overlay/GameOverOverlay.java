package game.ui.overlay;

import java.util.List;
import java.util.Set;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.core.player.Player;

public class GameOverOverlay extends PopUpOverlay {

	private List<Player> players;

	public GameOverOverlay(List<Player> players) throws SlickException {
		super();
		this.players = players;
	}

	@Override
	public void render(Graphics g) {
		// draw the background
		background.draw(x, y, width, height);

		wg.drawCenter(g, "GAME_OVER", x + width / 2, y + height / 2 - height / 3, false, scale);

		// TODO sort players by progress and display progress once more
		// characters available
		float playerNumber = 0;
		for (Player p : players) {
			wg.drawCenter(g, p.name, x + width / 4, y + height / 2 + height / 8 * playerNumber, false, scale / 4);
			wg.drawCenter(g, "PROGRESS", x + 3 * width / 4, y + height / 2 + height / 8 * playerNumber, false,
					scale / 4);
			playerNumber++;
		}

		wg.drawCenter(g, "EXIT", x + width / 2, y + height / 2 + height / 3, false, scale / 2);
	}
}
