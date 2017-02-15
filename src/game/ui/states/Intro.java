// GameState that shows logo.

package game.ui.states;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.interfaces.Vals;

public class Intro extends BasicGameState {
	
	private Image logo;
	private int alpha;
	private int ticks;
	
	private final int FADE_IN = 100;
	private final int LENGTH = 900;
	private final int FADE_OUT = 100;
	
	public Intro(int id) {
		
	}
	public void update() {
		
	}
	
	public void draw(Graphics2D g) {
		
	}
	
	public void handleInput(GameContainer gc,StateBasedGame game) {
		Input input=gc.getInput();
		if(input.isKeyPressed(Vals.ENTER)) {
			game.enterState(Vals.MENU_STATE);
		}
	}

	@Override
	public void init(GameContainer gc ,StateBasedGame game) throws SlickException {
		ticks = 0;
		try {
			logo = (new Image("/res/logo.png")).getScaledCopy((float) 2.5);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void render(GameContainer gc ,StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, Vals.SCREEN_WIDTH, Vals.SCREEN_HEIGHT);
		g.drawImage(logo, Vals.SCREEN_WIDTH*1/18,Vals.SCREEN_HEIGHT*3/10);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, Vals.SCREEN_WIDTH, Vals.SCREEN_HEIGHT);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		handleInput(gc,game);
		ticks++;
		if(ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if(alpha < 0) alpha = 0;
		}
		if(ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if(alpha > 255) alpha = 255;
		}
		if(ticks > FADE_IN + LENGTH + FADE_OUT) {
			game.enterState(Vals.MENU_STATE);
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Vals.INTRO_STATE;
	}
	
}