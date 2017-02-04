package game.ui.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.interfaces.Vals;

/**
 * A class invoked when the user is in game in order to pause it and
 * subsequently provide them with options.
 * 
 * @author iichr
 *
 */
public class Pause {



	public Pause(int state) {
	}



	public void render(Graphics g) throws SlickException {
		g.drawString("Resume (ESC)", Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H - 100);
		g.drawString("Main Menu (M)", Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H - 50);
		g.drawString("Quit (Q)", Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H);
	}


	public void clear(Graphics g) throws SlickException {
		g.clear();

	}




}
