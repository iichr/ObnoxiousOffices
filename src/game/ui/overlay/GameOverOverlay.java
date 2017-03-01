package game.ui.overlay;

import java.util.Set;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.core.player.Player;

public class GameOverOverlay extends PopUpOverlay {
	
	private Set<Player> players;

	public GameOverOverlay(Set<Player> players) throws SlickException {
		super();
		this.players = players;
	}

	@Override
	public void render(Graphics g) {
		// draw the background
		// TODO add background image
		// background.draw(x, y, width, height);

		// TODO position text nicely
		wg.draw(g, "GAME_OVER", x + width / 2 - 300, y + height / 2 - 200, false, scale);
		
		//TODO sort players by progress and display progress once more characters available
		float playerNumber = 0;
		for(Player p: players){
			wg.draw(g, p.name, x + width / 2 - 300, y + height / 2 + 50* playerNumber, false, scale/4);
			wg.draw(g, "PROGRESS", x + width / 2, y + height / 2 + 50* playerNumber, false, scale/4);
			playerNumber++;
		}
		
		wg.draw(g, "EXIT", x + width / 2 - 300, y + height / 2 + 400, false, scale);
	}
}
