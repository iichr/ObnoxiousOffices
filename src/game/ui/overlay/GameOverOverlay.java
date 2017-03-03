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

		wg.drawCenter(g, "GAME OVER", x + width / 2, y + height / 2 - height / 3, false, 2*scale/3);

		// TODO sort players by progress and display progress once more
		// characters available
		float playerNumber = 0;
		for (Player p : players) {
			wg.drawCenter(g, p.name + "   " + (int) p.getProgress() + "%", x + width / 2, y + height / 3 + (height / 9) * playerNumber, false, scale / 4);
			playerNumber++;
		}

		wg.drawCenter(g, "PRESS ANY KEY TO EXIT", x + width / 2, y + height / 2 + height / 3, false, scale / 3);
	}
}
