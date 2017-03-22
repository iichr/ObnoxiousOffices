package game.ui.states;

import java.util.Map;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.components.Controls;
import game.ui.components.WordGenerator;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;
import game.util.Pair;

/**
 * The second page of the Options submenu. Used to list all keyboard controls.
 */
public class KeyOptions extends BasicGameState {
	private MenuButton backButton;
	// A word generator for the main game font
	private WordGenerator wg;
	// A list of all controls in the game
	private Controls keyboardControls;

	public void setWG(WordGenerator wg) {
		this.wg = wg;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		keyboardControls = new Controls();

		// set up back button
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		backButton.render();

		float y = Vals.OPTIONS_CONTR_Y;

		// loop through the hash map of controls
		for (Map.Entry<String, String> keyBinding : keyboardControls.allControls.entrySet()) {
			// get key value pair
			String key = keyBinding.getKey();
			String value = keyBinding.getValue();

			// generate word to be drawn
			Pair<Float, Float> actionDescr = wg.getWH(key, 0.2f);
			// render the key (action description) on the left hand side
			wg.draw(g, key, Vals.OPTIONS_CONTR_X - actionDescr.getL(), y, false, 0.2f);
			// render the value (the key binding) on the right hand side
			wg.draw(g, value, Vals.OPTIONS_CONTR_X + Vals.BUTTON_WIDTH, y, false, 0.2f);

			// add padding between each line of key-value pairs rendered
			y += 40.0f;
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();

		// return button linking to the previous page of Options
		backButton.update(gc, game, mouseX, mouseY, Vals.OPTIONS_STATE);
	}

	@Override
	public int getID() {
		return Vals.OPTIONS_STATE_PAGE2;
	}

}
