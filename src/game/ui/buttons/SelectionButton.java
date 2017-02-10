package game.ui.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

public class SelectionButton extends Button {
	
	public SelectionButton(float x, float y, float width, float height, Image normal, Image alternate) {
		super(x, y, width, height, normal, alternate);
	}

	private static final long serialVersionUID = -685450501085834835L;
	
	/**
	 * Update method for the button - maintain a state on button click
	 * 
	 * @param gc
	 *            The game container
	 * @param game
	 *            The game
	 * @param mouseX
	 *            The x coord of the mouse cursor
	 * @param mouseY
	 *            The y coord of the mouse cursor
	 */
	public void update(GameContainer gc, StateBasedGame game, float mouseX, float mouseY) {
		Input input = gc.getInput();
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (inRange(mouseX, mouseY)) {
				button = select;
				//update core game with selected character
			}else{
				button = unselect;
				//update game that character unselected
			}
		}
	}

}
