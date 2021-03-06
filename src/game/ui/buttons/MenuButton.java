package game.ui.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.interfaces.Vals;

/**
 * Used to create a new labelled button for the menu. Label is centred to the
 * middle of the button.
 */
public class MenuButton extends Button {

	/**
	 * Constructor: sets up variables needed for the button
	 * 
	 * @param x
	 *            The x position of the top left of the button
	 * @param y
	 *            The y position of the top left of the button
	 * @param width
	 *            The width of the button
	 * @param height
	 *            The height of the button
	 * @param normal
	 *            The normal image for the button
	 * @param alternate
	 *            The roll-over image for the button
	 */
	public MenuButton(float x, float y, float width, float height, Image normal, Image alternate) {
		super(x, y, width, height, normal, alternate);
	}

	private static final long serialVersionUID = -6073162297979548251L;

	/**
	 * Update method for the button - enter a new state on button click.
	 * 
	 * @param gc
	 *            The game container
	 * @param game
	 *            The game
	 * @param mouseX
	 *            The x coord of the mouse cursor
	 * @param mouseY
	 *            The y coord of the mouse cursor
	 * @param stateID
	 *            The new state to enter.
	 */
	public void update(GameContainer gc, StateBasedGame game, float mouseX, float mouseY, int stateID) {
		Input input = gc.getInput();

		if (inRange(mouseX, mouseY)) {
			button = select;
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (stateID == Vals.EXIT) {
					gc.exit();
				} else {
					game.enterState(stateID);
				}
			}
		} else {
			button = unselect;
		}
	}

	/**
	 * Update method for the button - enter a new state on button select. Only
	 * use this update method for keyboard controlled menu buttons
	 *
	 * @param gc
	 *            The game container
	 * @param game
	 *            The game
	 * @param selected
	 *            Whether the button is selected or not
	 * @param stateID
	 *            The new state to enter
	 */
	public void update(GameContainer gc, StateBasedGame game, boolean selected, int stateID) {
		Input input = gc.getInput();

		if (selected) {
			button = select;
			if (input.isKeyPressed(Input.KEY_ENTER)) {
				if (stateID == Vals.EXIT) {
					gc.exit();
				} else {
					game.enterState(stateID);
				}
			}
		} else {
			button = unselect;
		}
	}

}
