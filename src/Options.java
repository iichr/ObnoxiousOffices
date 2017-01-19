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
	
	private MenuButton testButton;
	private UnicodeFont font;
	
	public Options(int state) {
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		//back button animation
		
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
		testButton = new MenuButton(295.0f, 350.0f, 200, 50, "Test");
		Font awtFont = new Font("Arial", Font.BOLD, 30);
		font = new UnicodeFont(awtFont);
		font.getEffects().add(new ColorEffect(java.awt.Color.white));
		font.addAsciiGlyphs();
		font.loadGlyphs();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 100, 100);
		
		g.setFont(font);
		
		soundStatus.draw(295,150);
		
		int strWidth = font.getWidth(testButton.getString());
		int strHeight = font.getHeight(testButton.getString());
		testButton.render(g,strWidth,strHeight);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		int mouseX = Mouse.getX();
		int mouseY = container.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
		
		testButton.onClick(input, game, mouseX, mouseY, Vals.MENU_STATE);
		
		// TODO FIX testButton currently invalidates Back string link to menu state.
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			// sound.play();
			if((mouseX >= 290 && mouseX <= 340) && (mouseY >= 290 && mouseY<=310)) {
				game.enterState(Vals.MENU_STATE);
			}
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
