package game.ui.states;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

public class CharacterSelect extends BasicGameState {
	private MenuButton connectButton, backButton;
	private String mouseCoords = "No input yet!";

	public CharacterSelect(int state) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);
		
		Image conn = new Image(ImageLocations.CONNECT);
		Image connR = new Image(ImageLocations.CONNECT_ROLLOVER);
		connectButton = new MenuButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H + 150, Vals.BUTTON_WIDTH,
				Vals.BUTTON_HEIGHT, conn, connR);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);

		// add back button
		backButton.render(g);
		connectButton.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
		connectButton.update(gc, game, mouseX, mouseY, Vals.PLAY_STATE);
	}

	@Override
	public int getID() {
		return Vals.CHARACTER_SELECT_STATE;
	}

}
