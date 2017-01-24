package game.ui.buttons;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

public class Button extends Rectangle {

	private static final long serialVersionUID = -3539104161526355883L;
	protected Animation select, unselect, button;

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
	 * 
	 * @param g
	 *            The graphics context to render to
	 */
	public void render(Graphics g) {
		button.draw(this.x, this.y, this.width, this.height);
	}
}
