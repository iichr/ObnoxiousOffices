package game.ui.overlay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class OptionsOverlay extends PopUpOverlay {

	public OptionsOverlay() throws SlickException {
		super();
	}

	@Override
	public void render(Graphics g) {
		// draw the background
		// TODO add background image
		// background.draw(x, y, width, height);

		// TODO position text nicely
		wg.draw(g, "CLOSE", x + width / 2, y + height / 2 - 200, false, scale / 2);
		wg.draw(g, "TOGGLE_SOUND", x + width / 2, y + height / 2, false, scale / 2);
		wg.draw(g, "TOGGLE_FULLSCREEN", x + width / 2, y + height / 2 + 200, false, scale / 2);
		
	}
}
