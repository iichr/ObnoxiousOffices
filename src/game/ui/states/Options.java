package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
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

import com.sun.imageio.plugins.png.PNGImageReader;

import game.ui.buttons.MenuButton;
import game.ui.components.WordGenerator;
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
	private WordGenerator wg;
	private Image normal;
	private Image bold;

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
		g.setColor(Color.white);
		// debugging
		g.drawString(mouseCoords, 10, 50);
		
		soundStatus.draw(Vals.BUTTON_ALIGN_CENTRE_W,Vals.BUTTON_ALIGN_CENTRE_H-Vals.BUTTON_ALIGN_CENTRE_H/10);
		g.drawString("Screen Mode :" + (gc.isFullscreen()?"Full Screen":"Windowed"), Vals.BUTTON_ALIGN_CENTRE_W-Vals.BUTTON_ALIGN_CENTRE_W/10, Vals.BUTTON_ALIGN_CENTRE_H+2*Vals.BUTTON_ALIGN_CENTRE_H/10);
		// add back button
		backButton.render();

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input=gc.getInput();
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
		if(input.isKeyPressed(input.KEY_F9)){
			gc.setFullscreen(!gc.isFullscreen());
		}
		
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
			if ((x >= Vals.BUTTON_ALIGN_CENTRE_W && x <= Vals.BUTTON_ALIGN_CENTRE_W + speakerOff.getWidth()) && (y >= Vals.BUTTON_ALIGN_CENTRE_H-10 && y <= Vals.BUTTON_ALIGN_CENTRE_H-10+speakerOff.getHeight())) {
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
