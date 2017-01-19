import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 * The Options submenu.
 * @author iichr
 *
 */
public class Rules extends BasicGameState {
	private int mouseX, mouseY;
	private String mouseCoords;
	
	public Rules(int state) {
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 100, 100);
		
		g.drawString("Back",295 ,300);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		int mouseX = Mouse.getX();
		int mouseY = container.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
		
		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			// sound.play();
			if((mouseX >= 290 && mouseX <= 340) && (mouseY >= 290 && mouseY<=310)) {
				game.enterState(Vals.MENU_STATE);
			}
		}
	}

	@Override
	public int getID() {
		return Vals.RULES_STATE;
	}
	
	public void mousePressed(int button, int x, int y) {
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
	}

}
