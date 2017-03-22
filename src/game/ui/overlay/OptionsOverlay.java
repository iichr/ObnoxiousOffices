package game.ui.overlay;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import game.ui.components.MusicBox;
import game.ui.components.WordGenerator;
import game.ui.interfaces.Vals;
import game.util.Pair;

/**
 * Overlay to show options to user in game
 */
public class OptionsOverlay extends PopUpOverlay {
	private MusicBox mb;

	/**
	 * Constructor: Sets up overlay
	 * 
	 * @throws SlickException
	 */
	public OptionsOverlay(WordGenerator wg) throws SlickException {
		super(wg);

	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		mb = new MusicBox(gc);
		// draw the background
		background.draw(x, y, width, height);
		Input input = gc.getInput();
		float mousex = input.getMouseX();
		float mousey = input.getMouseY();
		float currentSVolume = gc.getSoundVolume();
		float currentMVolume = gc.getMusicVolume();

		// debugging
		Pair<Float, Float> wh = wg.getWH("Sound", 0.2f);
		wg.draw(g, "Sound", Vals.BUTTON_ALIGN_CENTRE_W - wh.getL(), Vals.BUTTON_ALIGN_CENTRE_H - wh.getR(), true, 0.2f);
		// < symbol
		Pair<Float, Float> wh2 = wg.getWH("<", 0.2f);
		wg.draw(g, "<", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH, Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR(), true,
				0.2f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH + wh2.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR() && mousey <= Vals.BUTTON_ALIGN_CENTRE_H) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				mb.changeSVolumeL(gc);
			}
		}

		// Volume in %
		wg.draw(g, (int) (currentSVolume * 100) + "%", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 1.5f,
				Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR(), true, 0.2f);
		// > symbol
		wg.draw(g, ">", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f, Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR(),
				true, 0.2f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f + wh2.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR() && mousey <= Vals.BUTTON_ALIGN_CENTRE_H) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				mb.changeSVolumeR(gc);
			}
		}
		// debugging
		Pair<Float, Float> wh4 = wg.getWH("Music", 0.2f);
		wg.draw(g, "Music", Vals.BUTTON_ALIGN_CENTRE_W - wh4.getL(), Vals.BUTTON_ALIGN_CENTRE_H, true, 0.2f);
		// < symbol
		wg.draw(g, "<", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH, Vals.BUTTON_ALIGN_CENTRE_H, true, 0.2f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH + wh2.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H && mousey <= Vals.BUTTON_ALIGN_CENTRE_H + wh2.getR()) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				mb.changeMVolumeL(gc);
			}
		}

		// Volume in %
		wg.draw(g, (int) (currentMVolume * 100) + "%", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 1.5f,
				Vals.BUTTON_ALIGN_CENTRE_H, true, 0.2f);
		// > symbol
		wg.draw(g, ">", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f, Vals.BUTTON_ALIGN_CENTRE_H, true, 0.2f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f + wh2.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H && mousey <= Vals.BUTTON_ALIGN_CENTRE_H + wh2.getR()) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				mb.changeMVolumeR(gc);
			}
		}

		Pair<Float, Float> wh3 = wg.getWH("Display Mode", 0.2f);
		wg.draw(g, "Display Mode", Vals.BUTTON_ALIGN_CENTRE_W - wh3.getL(), Vals.BUTTON_ALIGN_CENTRE_H + wh2.getR(),
				true, 0.2f);
		// < symbol
		wg.draw(g, "<", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH, Vals.BUTTON_ALIGN_CENTRE_H + wh2.getR(), true,
				0.2f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH + wh3.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H + wh2.getR()
				&& mousey <= Vals.BUTTON_ALIGN_CENTRE_H + 2 * wh2.getR()) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				gc.setFullscreen(!gc.isFullscreen());
			}
		}

		// display modes
		String toShow = "Window";
		if (gc.isFullscreen()) {
			toShow = "Full Screen";
		}
		Pair<Float, Float> wh5 = wg.getWH(toShow, 0.2f);
		wg.drawCenter(g, toShow, Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 1.8f,
				Vals.BUTTON_ALIGN_CENTRE_H + wh5.getR() + wh5.getR() / 10, true, 0.2f);
		// > symbol
		wg.draw(g, ">", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f, Vals.BUTTON_ALIGN_CENTRE_H + wh2.getR(),
				true, 0.2f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f + wh3.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H + wh2.getR()
				&& mousey <= Vals.BUTTON_ALIGN_CENTRE_H + 2 * wh2.getR()) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				gc.setFullscreen(!gc.isFullscreen());
			}
		}

	}
}
