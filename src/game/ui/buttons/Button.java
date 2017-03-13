package game.ui.buttons;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Button extends Rectangle {

	private static final long serialVersionUID = -3539104161526355883L;
	protected Animation select, unselect, button;

	/**
	 * Constructor: sets up variables needed for the button and creates the animation for the button
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
	 *            The default image for the button
	 * @param alternate
	 *            The alternate image for the button
	 */
	public Button(float x, float y, float width, float height, Image normal, Image alternate) {
		super(x, y, width, height);
		createAnimation(normal, alternate);
	}

	/**
	 * Creates the animation for the roll-over button
	 * 
	 * @param normal
	 *            The image for the button
	 * @param rollover
	 *            The image for the button when rolled over
	 */
	public void createAnimation(Image normal, Image alternate) {
		int[] duration = { 200, 200 };

		Image[] on = { alternate, normal };
		Image[] off = { normal, alternate };

		select = new Animation(on, duration, false);
		unselect = new Animation(off, duration, false);

		// set initial state to off
		button = unselect;
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
	 * Renders the button to the graphics context using its coords.
	 */
	public void render() {
		button.draw(this.x, this.y, this.width, this.height);
	}
}
