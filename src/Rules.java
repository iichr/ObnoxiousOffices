import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Rules extends BasicGameState {
	private Image back, backR;
	private Animation rollOn, rollOff, backButton;
	private int[] duration = {200,200};
	private String mouseCoords;
	
	public Rules(int state) {
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		//back button animation
		back = new Image("./res/back.png");
		backR = new Image("./res/backR.png");
		Image[] rollO = {backR, back};
		Image[] rollF = {back, backR};
				
		rollOn = new Animation(rollO, duration, false);
		rollOff = new Animation(rollF, duration, false);
				
		//set initial state to off
		backButton = rollOff;
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 100, 100);
		
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
		return Vals.RULES_STATE;
	}
	
}
