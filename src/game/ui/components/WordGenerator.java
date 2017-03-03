package game.ui.components;

import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class WordGenerator {
	Graphics g;
	Image ssN;
	Image ssB;
	HashMap<Character, Image> wg = new HashMap<>();
	HashMap<Character, Image> wgB = new HashMap<>();
	int a = (int) 'a';

	public WordGenerator() throws SlickException {
		
		//TODO extra characters and bold versions
		for (char c = 'A'; c <= 'Z'; c++) {
			wg.put(c, new Image("/res/alphabets/normal/" + c + ".png"));
			wg.put((char) (c + 32), new Image("/res/alphabets/normal/" + c + ".png"));
		}
		for (char c = '0'; c <= '9'; c++) {
			wg.put(c, new Image("/res/alphabets/normal/" + c + ".png"));
		}
		for (char c = 'A'; c <= 'Z'; c++) {
			wgB.put(c, new Image("/res/alphabets/bold/" + c + ".png"));
			wgB.put((char) (c + 32), new Image("/res/alphabets/bold/" + c + ".png"));
		}
		for (char c = '0'; c <= '9'; c++) {
			wgB.put(c, new Image("/res/alphabets/bold/" + c + ".png"));
		}
		wg.put('_', new Image("/res/alphabets/normal/_.png"));
		wgB.put('_', new Image("/res/alphabets/bold/_.png"));
		wg.put((char)32, new Image ("/res/alphabets/Space.png"));
		wgB.put((char)32, new Image ("/res/alphabets/Space.png"));
		wg.put((char)37, new Image ("/res/alphabets/normal/percentage.png"));
		wgB.put((char)37, new Image ("/res/alphabets/bold/percentage.png"));
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
				System.err.println("The Player name contains invalid symbol that doesn't exist in the HashMap");
			}
			f += img.getWidth() * scale;
		}
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
			if(img.getScaledCopy(scale).getHeight() > totalY){
				totalY = img.getScaledCopy(scale).getHeight();
			}
		}
		
		for(int length = 0; length < text.length(); length++) {
			Image img = this.get(text.charAt(length), bold);
			try {
				g.drawImage(img.getScaledCopy(scale), f - totalX/2, h - totalY/2);
			} catch (NullPointerException e) {
				System.err.println("The Player name contains invalid symbol that doesn't exist in the HashMap");
			}
			f += img.getWidth() * scale;
		}
	}

	public Image get(char c, boolean bold) {
		if(bold){
		return wgB.get(c);
		}
		return wg.get(c);
	}

}
