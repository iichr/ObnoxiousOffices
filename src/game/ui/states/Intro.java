// GameState that shows logo.

package game.ui.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.interfaces.MusicLocations;
import game.ui.interfaces.Vals;

public class Intro extends BasicGameState {

	private Image logo;
	private int alpha;
	private int ticks;
	private boolean skip;

	private final int FADE_IN = 100;
	private final int LENGTH = 500;
	private final int FADE_OUT = 200;

	private final float logoWidth = 3*Vals.SCREEN_WIDTH / 5;
	private final float logoHeight = Vals.SCREEN_HEIGHT / 4;
	private Sound intro;

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		ticks = 0;
		skip = false;

		logo = new Image("/res/logo.png", false, Image.FILTER_NEAREST);
	}
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.white);
		g.fillRect(0, 0, Vals.SCREEN_WIDTH, Vals.SCREEN_HEIGHT);
		logo.draw(Vals.SCREEN_WIDTH / 2 - logoWidth / 2, Vals.SCREEN_HEIGHT / 2 - logoHeight / 2, logoWidth,
				logoHeight);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, Vals.SCREEN_WIDTH, Vals.SCREEN_HEIGHT);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		ticks++;
		if (ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if (alpha < 0)
				alpha = 0;
		}
		if (ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if (alpha > 255)
				alpha = 255;
		}
		if (skip || (ticks > FADE_IN + LENGTH + FADE_OUT)) {
			game.enterState(Vals.MENU_STATE);
			gc.getInput().clearKeyPressedRecord();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_ENTER) {
			skip = true;
		}
	}

	@Override
	public int getID() {
		return Vals.INTRO_STATE;
	}

}