package game.ui.overlay;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.ui.components.WordGenerator;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

public class PopUpOverlay {

	protected Image background;
	protected WordGenerator wg;
	protected float width;
	protected float height;
	protected float x;
	protected float y;
	
	protected final float scale = 0.7f;

	public PopUpOverlay() throws SlickException {
		width = Vals.SCREEN_WIDTH * scale;
		height = Vals.SCREEN_HEIGHT * scale;
		x = (Vals.SCREEN_WIDTH - width)/2;
		y = (Vals.SCREEN_HEIGHT - height)/2;
		
		wg = new WordGenerator();
		
		//TODO make background image
		background = new Image(ImageLocations.OVERLAY_BACKGROUND, false, Image.FILTER_NEAREST);
	}

	/**
	 * Renders the components of the popUp
	 */
	public void render(Graphics g) {
	}	
	
}
