package game.ui.overlay;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

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
		
		background = new Image(ImageLocations.OVERLAY_BACKGROUND, false, Image.FILTER_NEAREST);
		
	}

	/**
	 * Renders the components of the popUp
	 */
	public void render(Graphics g) {
		
	}	
	
	public void createPNG(String name){
		java.awt.Rectangle screenRect = new java.awt.Rectangle(new Dimension(Vals.screenRes));
		
		try {
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "png", new File(name+".png"));
		} catch (IOException | AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
