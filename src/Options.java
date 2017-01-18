import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
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
	
	public Options(int state) {
		
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
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
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			// sound.play();
		}
		
		// debugging
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
	}

	@Override
	public int getID() {
		return 2;
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
