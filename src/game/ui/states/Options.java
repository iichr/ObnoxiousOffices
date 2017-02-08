package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

/**
 * The Options submenu.
 * 
 * @author iichr
 *
 */
public class Options extends BasicGameState {
	private Image speakerOn, speakerOff;
	private Animation turnOn, turnOff, soundStatus;
	private int[] duration = { 200, 200 };
	private Music music;
	private Sound sound;
	private int mouseX, mouseY;
	private String mouseCoords;

	private MenuButton backButton;

	public Options(int state) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		
		// sound toggle animation
		speakerOff = new Image(ImageLocations.SPEAKER_OFF);
		speakerOn = new Image(ImageLocations.SPEAKER_ON);

		Image[] speakerTurnOff = { speakerOff, speakerOn };
		Image[] speakerTurnOn = { speakerOn, speakerOff };

		turnOff = new Animation(speakerTurnOff, duration, false);
		turnOn = new Animation(speakerTurnOn, duration, false);

		// set initial state to ON;
		soundStatus = turnOn;

		// TODO add music and sound
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);

		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);
		
		soundStatus.draw(295, 150);

		// add back button
		backButton.render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
	}

	@Override
	public int getID() {
		return Vals.OPTIONS_STATE;
	}

	public void mousePressed(int button, int x, int y) {
		mouseX = x;
		mouseY = y;
		if (button == 0) {
			// png size 128
			if ((x >= 295 && x <= 423) && (y >= 150 && y <= 278)) {
				if (soundStatus == turnOff) {
					soundStatus = turnOn;
					//music.resume();
				} else {
					soundStatus = turnOff;
					//music.stop();
				}
			}
		}
	}
}
