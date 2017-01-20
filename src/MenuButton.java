import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Used to create a new labeled button for the menu. Label is centered to the
 * middle of the button.
 * 
 * @author iichr
 *
 */
public class MenuButton extends Rectangle {

	private static final long serialVersionUID = -4269697269366852233L;
	private Animation rollOn, rollOff, rolloverButton;

	/**
	 * Create a new image based menu button.
	 * 
	 * @param x
	 *            The x coord of the top left of the button
	 * @param y
	 *            The y coord of the top left of the button
	 * @param width
	 *            The button's width
	 * @param height
	 *            The button's height
	 * @param normal
	 *            The default button image, on no interaction
	 * @param rollover
	 *            The button image on mouse hover.
	 */
	public MenuButton(float x, float y, float width, float height, Image normal, Image rollover) {
		super(x, y, width, height);
		createAnimation(normal, rollover);
	}

	/**
	 * Creates the animation for the roll-over button
	 * 
	 * @param normal
	 *            The image for the button
	 * @param rollover
	 *            The image for the button when rolled over
	 */
	public void createAnimation(Image normal, Image rollover) {
		int[] duration = { 200, 200 };

		Image[] rollO = { rollover, normal };
		Image[] rollF = { normal, rollover };

		rollOn = new Animation(rollO, duration, false);
		rollOff = new Animation(rollF, duration, false);

		// set initial state to off
		rolloverButton = rollOff;
	}

	/**
	 * Renders the button to the graphics context using its coords.
	 * 
	 * @param g
	 *            The graphics context to render to
	 */
	public void render(Graphics g) {
		rolloverButton.draw(this.x, this.y);
	}

	/**
	 * Check if certain coords fall within the boundaries of the button.
	 * 
	 * @param x
	 *            The x coord to check
	 * @param y
	 *            The y coord to check
	 * @return Whether (x,y) is inside the button.
	 */
	public boolean inRange(float x, float y) {
		return this.contains(x, y);
	}

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
	public void onClick(GameContainer gc, StateBasedGame game, float mouseX, float mouseY, int stateID) {
		Input input = gc.getInput();

		if (inRange( mouseX, mouseY)) {
			rolloverButton = rollOn;
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (stateID == 4) {
					gc.exit();
				} else {
					game.enterState(stateID);
				}
			}
		} else {
			rolloverButton = rollOff;
		}
	}
}
