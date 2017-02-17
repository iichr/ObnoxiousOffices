package game.ui.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import game.core.event.ConnectionAttemptEvent;
import game.core.event.Events;

/**
 * Used to create a new labelled button for the menu. Label is centred to the
 * middle of the button.
 *
 */
public class ConnectButton extends Button {
	private boolean active = true;

	public ConnectButton(float x, float y, float width, float height, Image normal, Image alternate) {
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
	public void update(GameContainer gc, StateBasedGame game, float mouseX, float mouseY, String serverAddress,
			String name) {
		Input input = gc.getInput();
		if (active) {
			if (inRange(mouseX, mouseY)) {
				button = select;
				if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
					Events.trigger(new ConnectionAttemptEvent(serverAddress, name));
				}
			} else {
				button = unselect;
			}
		}
	}
	
	public void setActive(boolean toSet){
		active = toSet;
	}
}
