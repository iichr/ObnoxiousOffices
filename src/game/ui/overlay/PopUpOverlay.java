package game.ui.overlay;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.ui.components.WordGenerator;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

/**
 * Superclass for overlays
 */
public class PopUpOverlay {

	protected Image background;
	protected WordGenerator wg;
	protected float width;
	protected float height;
	protected float x;
	protected float y;
	protected final float scale = 0.7f;

	/**
	 * Constructor: Sets up overlay and loads background image
	 * 
	 * @throws SlickException
	 */
	public PopUpOverlay(WordGenerator wg) throws SlickException {
		width = Vals.SCREEN_WIDTH * scale;
		height = Vals.SCREEN_HEIGHT * scale;
		x = (Vals.SCREEN_WIDTH - width) / 2;
		y = (Vals.SCREEN_HEIGHT - height) / 2;
		this.wg = wg;

		background = new Image(ImageLocations.OVERLAY_BACKGROUND, false, Image.FILTER_NEAREST);

	}

	/**
	 * Renders the components of the popUp
	 * 
	 * @param g
	 *            The graphics object
	 */
	public void render(Graphics g) {

	}

	/**
	 * Takes a screenshot of the screen
	 * 
	 * @param name
	 *            The name to save the image as
	 */
	public void createPNG(String name) {
		java.awt.Rectangle screenRect = new java.awt.Rectangle(new Dimension(Vals.screenRes));

		try {
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "png", new File(name + ".png"));
		} catch (IOException | AWTException e) {
			e.printStackTrace();
		}
	}

}
