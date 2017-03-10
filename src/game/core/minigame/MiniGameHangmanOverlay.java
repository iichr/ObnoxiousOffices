package game.core.minigame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.ui.overlay.PopUpOverlay;

/**
 * A hangman mini-game popup. Game Logic abstracted away.
 * 
 * @author iichr
 */

public class MiniGameHangmanOverlay extends PopUpOverlay {

	public MiniGameHangmanOverlay() throws SlickException {
	}

	@Override
	public void render(Graphics g) {
		MiniGameHangman hangman = (MiniGameHangman) MiniGame.localMiniGame;
		// temporarily use the default background
		background.draw(x, y, width, height);

		wg.drawCenter(g, hangman.getDisplayWord(), x + width / 2, y + height / 2 - height / 6, true, scale / 3);
		wg.drawCenter(g, "GUESSED    " + hangman.getEntered(), x + width / 2, y + height / 2, true, scale / 3);
		wg.drawCenter(g, hangman.getAttemptsLeft() + " attempts left", x + width / 2, y + height / 2 + height / 6, true,
				scale / 4);
	}

}
