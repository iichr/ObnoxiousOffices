package game.ui.states;

import java.util.Map;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
 * 
 * @author iichr
 *
 */
public class Options2 extends BasicGameState {
	private MenuButton backButton;
	private WordGenerator wordGen;
	private Controls keyboardControls;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		wordGen = new WordGenerator();
		keyboardControls = new Controls();
		
		// set up back button
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		Input input = gc.getInput();
		
		backButton.render();
		
		int x=100, y=40;
		for(Map.Entry<String, String> keyBinding: keyboardControls.allControls.entrySet()) {
			
			String key = keyBinding.getKey();
			String value = keyBinding.getValue();
			
			g.drawString(key, x, y);
			g.drawString(value, x+400, y);
			y+=40;
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		
		// return button to the previous page of Options
		backButton.update(gc, game, mouseX, mouseY, Vals.OPTIONS_STATE);

	}

	@Override
	public int getID() {
		return Vals.OPTIONS_STATE_PAGE2;
	}

}
