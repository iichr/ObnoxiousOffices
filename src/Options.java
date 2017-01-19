import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.*;

/**
 * The Options submenu.
 * @author iichr
 *
 */
public class Options extends BasicGameState {	
	private Image speakerOn, speakerOff;
	private Animation turnOn, turnOff, soundStatus;
	private int[] duration = {200,200};
	private Music music;
	private Sound sound;
	private int mouseX, mouseY;
	private String mouseCoords;
	
	private MenuButton backButton;
	
	public Options(int state) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		//sound toggle animation
		speakerOff = new Image("./res/speakerOff.png");
		speakerOn = new Image("./res/speakerOn.png");
		Image[] speakerTurnOff = {speakerOff, speakerOn};
		Image[] speakerTurnOn = {speakerOn, speakerOff};
		
		turnOff = new Animation(speakerTurnOff, duration, false);
		turnOn = new Animation(speakerTurnOn, duration, false);
		
		// set initial state to ON;
		soundStatus = turnOn;
		
		// TODO add music and sound	 
		Image back = new Image("./res/back.png");
		Image backR = new Image("./res/backR.png");
		
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);
		
		soundStatus.draw(295,150);

		//add back button
		backButton.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
		
		backButton.onClick(gc, game, mouseX, mouseY, Vals.MENU_STATE);	
	}

	@Override
	public int getID() {
		return Vals.OPTIONS_STATE;
	}
	
	public void mousePressed(int button, int x, int y) {
		mouseX = x;
		mouseY = y;
		if(button == 0) {
			// png size 128
			if((x>=295 && x<=423) && (y>=150 && y<=278)) {
				if(soundStatus == turnOff) {
				soundStatus = turnOn;
				}
				else {
					soundStatus = turnOff;
				}
			}
		}
	}
}
