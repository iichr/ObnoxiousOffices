package game.ui.components;

import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.util.Pair;

public class WordGenerator {
	Graphics g;
	float height;
	float width;
	SpriteSheet ss;
	HashMap<Character, Image> wg = new HashMap<>();
	HashMap<Character, Image> wgB = new HashMap<>();
	int a = (int) 'a';
	char[][] map = new char[][] { 	{ 'a', 'b', 'c', 'd', 'e' }, 
									{ 'f', 'g', 'h', 'i', 'j' }, 
									{ 'k', 'l', 'm', 'n', 'o' },
									{ 'p', 'q', 'r', 's', 't' }, 
									{ 'u', 'v', 'w', 'x', 'y' }, 
									{ 'z', '%', '_', '<', '>' },
									{ '0', '1', '2', '3', '4' }, 
									{ '5', '6', '7', '8', '9' } };

	/**
	 * Constructor: loads all of the images needed by the word generator
	 * 
	 * @throws SlickException
	 */
	public WordGenerator() throws SlickException {
		ss = new SpriteSheet(new Image("res/alphabets/normal.png"), 150, 150, 0, 0);
		this.load(wg);
		this.load(wgB);
		height = wg.get('%').getHeight();
		width = wg.get('%').getWidth();

	}

	private void load(HashMap<Character, Image> hm) throws SlickException {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 5; x++) {
				if (map[y][x] - 32 >= 'A' && map[y][x] - 32 <= 'Z') {
					hm.put((char) (((int) map[y][x]) - 32), ss.getSprite(x, y));
				}
				hm.put(map[y][x], ss.getSprite(x, y));
			}
		}
		hm.put((char) 32, new Image("res/alphabets/Space.png"));

	}

	/**
	 * @param Graphics
	 *            g - the graphics object
	 * @param text
	 *            - the text to generate
	 * @param f
	 *            - x coordinate
	 * @param h
	 *            - y coordinate
	 * @param bold
	 *            - Set true if you want text in bold, else false
	 * @param scale
	 *            - scaling factor min 0 max 1
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
	 * Get the Width and Height of a generated Word as a Pair
	 * 
	 * @return Pair(Width, Height) Gets the width and height of a generated word
	 * 
	 * 
	 * @param text
	 *            The text to get the width and height of
	 * @param scale
	 *            The scale to be used for the image
	 * @return A pair <Width, Height>
	 */

	public Pair<Float, Float> getWH(String text, float scale) {
		float totalWidth = text.length() * (width * scale);
		return new Pair<>(totalWidth, height);

	}

	/**
	 * @param Graphics
	 *            g - the graphics object
	 * @param text
	 *            - the text to generate
	 * @param f
	 *            - x coordinate
	 * @param h
	 *            - y coordinate
	 * @param bold
	 *            - Set true if you want text in bold, else false
	 * @param scale
	 *            - scaling factor min 0 max 1
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

	public Pair<Float, Float> getXY(String text, float f, float h, float scale) {
		float totalX = 0;
		float totalY = 0;
		for (int length = 0; length < text.length(); length++) {
			Image img = this.get(text.charAt(length), false);
			totalX += img.getScaledCopy(scale).getWidth();
			if (img.getScaledCopy(scale).getHeight() > totalY) {
				totalY = img.getScaledCopy(scale).getHeight();
			}
		}
		return new Pair<Float, Float>(f - totalX / 2, h - totalY / 2);
	}

	/**
	 * Get the image for a particular character
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
