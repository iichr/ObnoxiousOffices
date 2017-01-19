import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * The Options submenu.
 * @author iichr
 *
 */
public class Options extends BasicGameState {
	
	//animation for back button
	private Image back, backR;
	private Animation rollOn, rollOff, backButton;
	
	private Image speakerOn, speakerOff;
	private Animation turnOn, turnOff, soundStatus;
	private int[] duration = {200,200};
	private Music music;
	private Sound sound;
	private int mouseX, mouseY;
	private String mouseCoords;
	
	public Options(int state) {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		//back button animation
		back = new Image("./res/back.png");
		backR = new Image("./res/backR.png");
		Image[] rollO = {backR, back};
		Image[] rollF = {back, backR};
		
		rollOn = new Animation(rollO, duration, false);
		rollOff = new Animation(rollF, duration, false);
		
		//set initial state to off
		backButton = rollOff;
		
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
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 100, 100);
		
		soundStatus.draw(295,150);
		
		//draw back button
		backButton.draw(10, 10);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		int mouseX = Mouse.getX();
		int mouseY = container.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
		
		//back size 40
		if((mouseX >= 10 && mouseX <= 50) && (mouseY >= 10 && mouseY<= 50)) {
			backButton = rollOn;
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				game.enterState(Vals.MENU_STATE);
			}
		} else {
			backButton = rollOff;
		}
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
