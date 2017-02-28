package game.ui;

import java.util.HashMap;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.ui.interfaces.ImageLocations;


public class WordGenerator {
	Graphics g;
	Image normal;
	Image bold;
	Image ssN;
	Image ssB;
	HashMap<Character,Image> wg = new HashMap<>();
	HashMap<Character,Image> wgB = new HashMap<>();
	int a=(int)'a';

	public WordGenerator() throws SlickException{
		
		for(char c = 'A'; c <= 'Z';c++){
			wg.put(c,new Image("/res/alphabets/"+c+".png"));
		}		
	}
	/**
	 * @param Graphics g - the graphics object
	 * @param text - the text to generate
	 * @param x - x coordinate 
	 * @param y - y coordinate
	 * @param bold - Set true if you want text in bold, else false
	 * @param scale - scaling factor min 0 max 1
	 * */
	public void draw(Graphics g,String text,int x, int y,boolean bold,float scale){
		this.g=g;
		for(int length=0;length<text.length();length++){
			Image img = this.get(text.charAt(length), bold);
			g.drawImage(img.getScaledCopy(scale),x,y);
			x+=img.getWidth()*scale;
		}
	}

	public Image get(char c,boolean bold){
		 return wg.get(c);
	
	}

}
