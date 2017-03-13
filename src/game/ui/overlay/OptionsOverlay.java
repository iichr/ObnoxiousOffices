package game.ui.overlay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class OptionsOverlay extends PopUpOverlay {

	/**
	 * Constructor: Sets up overlay
	 * @throws SlickException
	 */
	public OptionsOverlay() throws SlickException {
		super();
	}

	@Override
	public void render(Graphics g) {
		// draw the background
		 background.draw(x, y, width, height);

		wg.drawCenter(g, "CLOSE", x + width / 2, y + height / 2 - height/6, true, scale / 3);
		wg.drawCenter(g, "TOGGLE SOUND", x + width / 2, y + height / 2, true, scale / 3);
		wg.drawCenter(g, "TOGGLE FULLSCREEN", x + width / 2, y + height / 2 + height/6, true, scale / 3);
		
	}
}
