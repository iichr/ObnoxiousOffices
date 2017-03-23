package game.ui.components;

import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.util.Pair;

/**
 * Generates an image using an in house font from a given string
 */
public class WordGenerator {

	private Graphics g;

	private float height;
	private float width;

	/** The HashMap for Normal Characters */
	private HashMap<Character, Image> wg = new HashMap<>();

	/** The HashMap for Bolded Characters */
	private HashMap<Character, Image> wgB = new HashMap<>();

	/** The map. */
	char[][] map = new char[][] { { 'a', 'b', 'c', 'd', 'e' }, { 'f', 'g', 'h', 'i', 'j' }, { 'k', 'l', 'm', 'n', 'o' },
			{ 'p', 'q', 'r', 's', 't' }, { 'u', 'v', 'w', 'x', 'y' }, { 'z', '%', '_', '<', '>' },
			{ '0', '1', '2', '3', '4' }, { '5', '6', '7', '8', '9' } };

	/**
	 * Constructor: loads all of the images needed by the word generator.
	 *
	 * @throws SlickException
	 */
	public WordGenerator() throws SlickException {
		SpriteSheet ss = new SpriteSheet(new Image("res/alphabets/normal.png"), 149, 149, 1, 0);
		SpriteSheet ssB = new SpriteSheet(new Image("res/alphabets/bold.png"), 147, 147, 3, 0);
		this.load(wg, ss);
		this.load(wgB, ssB);
		height = wg.get('%').getHeight();
		width = wg.get('%').getWidth();

	}

	/**
	 * Load sprite sheets to hashmaps
	 *
	 * @param hm
	 *            The hash map from characters to images
	 * @param spriteSheet
	 *            The character sprite sheet
	 * @throws SlickException
	 */
	private void load(HashMap<Character, Image> hm, SpriteSheet spriteSheet) throws SlickException {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 5; x++) {
				if (map[y][x] - 32 >= 'A' && map[y][x] - 32 <= 'Z') {
					hm.put((char) (((int) map[y][x]) - 32), spriteSheet.getSprite(x, y));
				}
				hm.put(map[y][x], spriteSheet.getSprite(x, y));
			}
		}
		hm.put((char) 32, new Image("res/alphabets/Space.png"));
		hm.put(':', new Image("res/alphabets/colon.png"));

	}

	/**
	 * Draw a word at x and y coordinates
	 *
	 * @param g
	 *            The graphics object
	 * @param text
	 *            The text to generate
	 * @param f
	 *            The x coordinate of the top left hand corner
	 * @param h
	 *            The y coordinate of the top left hand corner
	 * @param bold
	 *            Set true if you want text in bold, else false
	 * @param scale
	 *            Scaling factor min 0 max 1
	 */
	public void draw(Graphics g, String text, float f, float h, boolean bold, float scale) {
		this.g = g;
		for (int length = 0; length < text.length(); length++) {
			Image img = this.get(text.charAt(length), bold);
			try {
				g.drawImage(img.getScaledCopy(scale), f, h);
			} catch (NullPointerException e) {
				System.err.println("Symbol " + text.charAt(length) + " doesn't exist in the HashMap");
			}
			f += img.getWidth() * scale;
		}
	}

	/**
	 * Get the Width and Height of a generated Word as a Pair.
	 *
	 * @param text
	 *            The text to get the width and height of
	 * @param scale
	 *            The scale to be used for the image
	 * @return Pair(Width, Height) Gets the width and height of a generated word
	 *         A pair <Width, Height>
	 */

	public Pair<Float, Float> getWH(String text, float scale) {
		float totalWidth = text.length() * (width * scale);
		return new Pair<>(totalWidth, height);

	}

	/**
	 * Draw a word centred around x and y coordinates
	 *
	 * @param g
	 *            The graphics object
	 * @param text
	 *            The text to generate
	 * @param f
	 *            The x coordinate to draw at
	 * @param h
	 *            The y coordinate to draw at
	 * @param bold
	 *            Set true if you want text in bold, else false
	 * @param scale
	 *            Scaling factor min 0 max 1
	 */
	public void drawCenter(Graphics g, String text, float f, float h, boolean bold, float scale) {
		this.g = g;
		float totalX = 0;
		float totalY = 0;
		for (int length = 0; length < text.length(); length++) {
			Image img = this.get(text.charAt(length), bold);
			totalX += img.getScaledCopy(scale).getWidth();
			if (img.getScaledCopy(scale).getHeight() > totalY) {
				totalY = img.getScaledCopy(scale).getHeight();
			}
		}

		for (int length = 0; length < text.length(); length++) {
			Image img = this.get(text.charAt(length), bold);
			try {
				g.drawImage(img.getScaledCopy(scale), f - totalX / 2, h - totalY / 2);
			} catch (NullPointerException e) {
				System.err.println("The Player name contains invalid symbol that doesn't exist in the HashMap");
			}
			f += img.getWidth() * scale;
		}
	}

	/**
	 * Gets the XY coordinates.
	 *
	 * @param text
	 *            The text
	 * @param x
	 *            The x coordinate of the top left hand corner
	 * @param y
	 *            The y coordinate of the top left hand corner
	 * @param scale
	 *            The scale
	 * @return the xy
	 */
	public Pair<Float, Float> getXY(String text, float x, float y, float scale) {
		float totalX = 0;
		float totalY = 0;
		for (int length = 0; length < text.length(); length++) {
			Image img = this.get(text.charAt(length), false);
			totalX += img.getScaledCopy(scale).getWidth();
			if (img.getScaledCopy(scale).getHeight() > totalY) {
				totalY = img.getScaledCopy(scale).getHeight();
			}
		}
		return new Pair<Float, Float>(x - totalX / 2, y - totalY / 2);
	}

	/**
	 * Get the image for a particular character.
	 *
	 * @param c
	 *            The character to get the image of
	 * @param bold
	 *            Whether the character is bold or not
	 * @return The image for the given character
	 */

	public Image get(char c, boolean bold) {
		if (bold) {
			return wgB.get(c);
		}
		return wg.get(c);
	}

}
