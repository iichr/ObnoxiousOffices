package game.ui.states;
import java.awt.Font;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.buttons.SelectionButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;


public class CharacterSelect extends BasicGameState {
	private MenuButton connectButton, backButton;
	private SelectionButton circleButton;
	private String mouseCoords = "No input yet!";
	
	private TextField serverAddress;
	private UnicodeFont font1;
		
	public CharacterSelect(int state) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);
		
		Image conn = new Image(ImageLocations.CONNECT);
		Image connR = new Image(ImageLocations.CONNECT_ROLLOVER);
		connectButton = new MenuButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H + 150, Vals.BUTTON_WIDTH,
				Vals.BUTTON_HEIGHT, conn, connR);
		
		Image circleUnselected = new Image(ImageLocations.CIRCLE_UNSELECTED, false, Image.FILTER_NEAREST);
		Image circleSelected = new Image(ImageLocations.CIRCLE_SELECTED, false, Image.FILTER_NEAREST);
		circleButton = new SelectionButton(Vals.BUTTON_ALIGN_CENTRE_W - 200, Vals.BUTTON_ALIGN_CENTRE_H - 200, 50, 50, circleUnselected, circleSelected);
		
		// Server address text field.
		font1 = new UnicodeFont(new Font("Arial", Font.BOLD, 20));
		font1.addAsciiGlyphs();
		// necessary to load an effect otherwise an exception is thrown.
		font1.getEffects().add(new ColorEffect());
		font1.loadGlyphs();
		
		serverAddress = new TextField(gc,font1, (Vals.SCREEN_WIDTH - 300)/2, 200, 300, font1.getLineHeight(), new ComponentListener(){
			public void componentActivated(AbstractComponent src){	
				serverAddress.setFocus(true);
			}
		});
		serverAddress.setBackgroundColor(Color.white);
		serverAddress.setTextColor(Color.black);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);
		//g.drawString(ipAddress.getText(), 700, 100);

		// add back button
		backButton.render(g);
		circleButton.render(g);
		connectButton.render(g);
		
		serverAddress.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
		connectButton.update(gc, game, mouseX, mouseY, Vals.PLAY_STATE);
		circleButton.update(gc, game, mouseX, mouseY);
	}

	@Override
	public int getID() {
		return Vals.CHARACTER_SELECT_STATE;
	}

}
