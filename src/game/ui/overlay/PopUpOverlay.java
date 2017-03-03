package game.ui.overlay;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL45;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.EffectUtil;
import org.newdawn.slick.geom.Rectangle;

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
	/** The number of kernels to apply */
	public static final int NUM_KERNELS = 16;
	/** The blur kernels applied across the effect */
	
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
