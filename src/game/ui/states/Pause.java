package game.ui.states;
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
public class Pause extends BasicGameState {
	// Resume game
	private int resume = Input.KEY_ESCAPE;
	// Back to menu
	private int backToMenu = Input.KEY_M;
	// Quit
	private int quit = Input.KEY_Q;
	public Pause(int state) {
	}
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("Resume (ESC)", Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H - 100);
		g.drawString("Main Menu (M)", Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H - 50);
		g.drawString("Quit (Q)", Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		// Manage which state each button routes to
		if (input.isKeyPressed(resume)) {
			game.enterState(Vals.PLAY_STATE);
		} else if (input.isKeyDown(backToMenu)) {
			game.enterState(Vals.MENU_STATE);
		} else if (input.isKeyDown(quit)) {
			gc.exit();
		}
	}
	@Override
	public int getID() {
		return Vals.PAUSE_STATE;
	}
}