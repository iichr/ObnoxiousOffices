package game.ui.overlay;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import game.core.player.Player;
import game.ui.components.WordGenerator;
import game.ui.interfaces.Vals;

public class GameOverOverlay extends PopUpOverlay {

	private List<Player> players;

	/**
	 * Constructor: Sets up overlay
	 * 
	 * @param players
	 *            The list of players
	 * @throws SlickException
	 */
	public GameOverOverlay(List<Player> players, WordGenerator wg) throws SlickException {
		super(wg);
		this.players = players;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		// draw the background
		background.draw(x, y, width, height);
		Input input=gc.getInput();

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
		if(input.isKeyPressed(Keyboard.getEventKey())){
			sbg.enterState(Vals.MENU_STATE);
		}

	}
}
