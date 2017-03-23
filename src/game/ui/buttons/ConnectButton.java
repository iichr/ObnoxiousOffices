package game.ui.buttons;

import java.util.regex.Pattern;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import game.core.event.ConnectionAttemptEvent;
import game.core.event.Events;
import game.ui.states.Connect;

/**
 * Used to create a new labelled button for the menu. Label is centred to the
 * middle of the button.
 */
public class ConnectButton extends Button {
	private boolean active = true;

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
	 */
	public void update(GameContainer gc, StateBasedGame game, float mouseX, float mouseY, String serverAddress,
			String name, Connect cs) {
		Input input = gc.getInput();
		if (active) {
			if (inRange(mouseX, mouseY)) {
				button = select;
				if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
					if (Pattern.matches("[a-zA-Z0-9_]+[a-zA-Z0-9_ ]*", name) && name.length() >= 1) {
						if(Pattern.matches("Volker_[0-9]+", name)){
							cs.setInvalidName(true);
						}else {
							cs.setInvalidName(false);
							Events.trigger(new ConnectionAttemptEvent(name, serverAddress));
						}
					} else {
						cs.setInvalidName(true);
					}
				}
			} else {
				button = unselect;
			}
		}
	}

	public void setActive(boolean toSet) {
		active = toSet;
	}
}
