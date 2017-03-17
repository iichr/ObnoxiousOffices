package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

//import com.sun.imageio.plugins.png.PNGImageReader;

import game.ui.buttons.MenuButton;
import game.ui.components.MusicBox;
import game.ui.components.WordGenerator;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;
import game.util.Pair;

/**
 * The Options submenu.
 * 
 * @author iichr
 *
 */
public class Options extends BasicGameState {
	private Music music;
	private Sound sound;
	private WordGenerator wg;
	private float currentSVolume, currentMVolume;
	private MusicBox mb;
	private MenuButton backButton, nextPageButton;

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		wg = new WordGenerator();

		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);
		nextPageButton = new MenuButton(52.0f, 134.0f, 60, 60, wg.get('>', true), wg.get('>', true));
		//nextPageButton = new MenuButton(52.0f, 134.0f, 40, 40, back, back);
		
		gc.setSoundVolume(1.0f);
		
		mb=new MusicBox(gc);

	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		Input input = gc.getInput();
		float mousex = input.getMouseX();
		float mousey = input.getMouseY();
		g.setColor(Color.white);
		currentSVolume = gc.getSoundVolume();
		currentMVolume = gc.getMusicVolume();

		// debugging
		Pair<Float, Float> wh = wg.getWH("Sound", 0.3f);
		wg.draw(g, "Sound", Vals.BUTTON_ALIGN_CENTRE_W - wh.getL(), Vals.BUTTON_ALIGN_CENTRE_H - wh.getR(), false,
				0.3f);
		// < symbol
		Pair<Float, Float> wh2 = wg.getWH("<", 0.3f);
		wg.draw(g, "<", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH, Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR(), false,
				0.3f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH + wh2.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR() && mousey <= Vals.BUTTON_ALIGN_CENTRE_H) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				mb.changeSVolumeL(gc);
			}
		}

		// Volume in %
		wg.draw(g, (int) (currentSVolume * 100) + "%", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 1.5f,
				Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR(), false, 0.3f);
		// > symbol
		wg.draw(g, ">", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f, Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR(),
				false, 0.3f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f + wh2.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H - wh2.getR() && mousey <= Vals.BUTTON_ALIGN_CENTRE_H) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				mb.changeSVolumeR(gc);
			}
		}
		// debugging
				Pair<Float, Float> wh4 = wg.getWH("Music", 0.3f);
				wg.draw(g, "Music", Vals.BUTTON_ALIGN_CENTRE_W - wh.getL(), Vals.BUTTON_ALIGN_CENTRE_H , false,
						0.3f);
				// < symbol
				wg.draw(g, "<", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH, Vals.BUTTON_ALIGN_CENTRE_H , false,
						0.3f);
				if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH
						&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH + wh2.getL()
						&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H && mousey <= Vals.BUTTON_ALIGN_CENTRE_H + wh2.getR()) {
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						mb.changeMVolumeL(gc);
					}
				}

				// Volume in %
				wg.draw(g, (int) (currentMVolume * 100) + "%", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 1.5f,
						Vals.BUTTON_ALIGN_CENTRE_H, false, 0.3f);
				// > symbol
				wg.draw(g, ">", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f, Vals.BUTTON_ALIGN_CENTRE_H,
						false, 0.3f);
				if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f
						&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 2.5f + wh2.getL()
						&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H && mousey <= Vals.BUTTON_ALIGN_CENTRE_H+wh2.getR()) {
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						mb.changeMVolumeR(gc);
					}
				}
		

		Pair<Float, Float> wh3 = wg.getWH("Display Mode", 0.3f);
		wg.draw(g, "Display Mode", Vals.BUTTON_ALIGN_CENTRE_W - wh3.getL(), Vals.BUTTON_ALIGN_CENTRE_H+wh2.getR(), false, 0.3f);
		// < symbol
		wg.draw(g, "<", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH / 2, Vals.BUTTON_ALIGN_CENTRE_H+wh2.getR(), false, 0.3f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH / 2
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH / 2 + wh3.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H+wh2.getR() && mousey <= Vals.BUTTON_ALIGN_CENTRE_H + 2*wh2.getR()) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				gc.setFullscreen(!gc.isFullscreen());
			}
		}

		// display modes
		wg.draw(g, gc.isFullscreen() ? "Full Screen" : "Window", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH,
				Vals.BUTTON_ALIGN_CENTRE_H+wh2.getR(), false, 0.3f);
		// > symbol
		wg.draw(g, ">", Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 3f, Vals.BUTTON_ALIGN_CENTRE_H+wh2.getR(), false, 0.3f);
		if (mousex >= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 3f
				&& mousex <= Vals.BUTTON_ALIGN_CENTRE_W + Vals.BUTTON_WIDTH * 3f + wh3.getL()
				&& mousey >= Vals.BUTTON_ALIGN_CENTRE_H+wh2.getR() && mousey <= Vals.BUTTON_ALIGN_CENTRE_H + 2*wh2.getR()) {
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				gc.setFullscreen(!gc.isFullscreen());
			}
		}
		// add back button
		backButton.render();
		nextPageButton.render();

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
		nextPageButton.update(gc,game,mouseX,mouseY, Vals.OPTIONS_STATE_PAGE2);
	}

	@Override
	public int getID() {
		return Vals.OPTIONS_STATE;
	}

}
