package game.ui.overlay;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.core.minigame.MiniGame;
import game.core.minigame.MiniGameHangman;
import game.ui.components.WordGenerator;

/**
 * Overlay displayed for hangman mingame
 */
public class HangmanOverlay extends PopUpOverlay {

	/**
	 * Constructor: Sets up overlay
	 * 
	 * @throws SlickException
	 */
	public HangmanOverlay(WordGenerator wg) throws SlickException {
		super(wg);
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
