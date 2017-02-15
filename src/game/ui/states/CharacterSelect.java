package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.core.event.ConnectionAttemptEvent;
import game.core.event.ConnectionFailedEvent;
import game.core.event.Events;
import game.core.event.PlayerCreatedEvent;
import game.ui.buttons.ConnectButton;
import game.ui.buttons.MenuButton;
import game.ui.buttons.SelectionButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

public class CharacterSelect extends BasicGameState {
	private MenuButton backButton;
	private ConnectButton connectButton;
	private SelectionButton circleButton;

	private TextField serverAddress, playerName;
	private String serverStr = "Enter Server Address:";
	private String playerStr = "Enter Player Name:";
	private String connectingString = "Attempting to connect to server";
	private String waitingString = "Waiting for more players";
	private String connectFailString = "Connection failed: please try again";

	private boolean toPlay = false;
	private boolean connecting = false;
	private boolean connected = false;
	private boolean connectFailed = false;
	private PlayTest playTest;

	public CharacterSelect(int state, PlayTest test) {
		this.playTest = test;
		Events.on(ConnectionAttemptEvent.class, this::showConnecting);
		Events.on(PlayerCreatedEvent.class, this::connected);
		Events.on(ConnectionFailedEvent.class, this::connectFail);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

		Image conn = new Image(ImageLocations.CONNECT);
		Image connR = new Image(ImageLocations.CONNECT_ROLLOVER);
		connectButton = new ConnectButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H + 150,
				Vals.BUTTON_WIDTH, Vals.BUTTON_HEIGHT, conn, connR);

		Image circleUnselected = new Image(ImageLocations.CIRCLE_UNSELECTED, false, Image.FILTER_NEAREST);
		Image circleSelected = new Image(ImageLocations.CIRCLE_SELECTED, false, Image.FILTER_NEAREST);
		circleButton = new SelectionButton(Vals.BUTTON_ALIGN_CENTRE_W - 200, Vals.BUTTON_ALIGN_CENTRE_H - 200, 50, 50,
				circleUnselected, circleSelected);

		// Server address text field.
		Vals.FONT_MAIN.addAsciiGlyphs();
		// necessary to load an effect otherwise an exception is thrown!!!
		Vals.FONT_MAIN.getEffects().add(new ColorEffect());
		Vals.FONT_MAIN.loadGlyphs();

		serverAddress = new TextField(gc, Vals.FONT_MAIN, Vals.TFIELD_ALIGN_CENTRE_W, 200, Vals.TFIELD_WIDTH,
				Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						serverAddress.setFocus(true);
					}
				});
		serverAddress.setBackgroundColor(Color.white);
		serverAddress.setTextColor(Color.black);

		// Player name text field.
		playerName = new TextField(gc, Vals.FONT_MAIN, Vals.TFIELD_ALIGN_CENTRE_W, 300, Vals.TFIELD_WIDTH,
				Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						playerName.setFocus(true);
					}
				});
		playerName.setBackgroundColor(Color.white);
		playerName.setTextColor(Color.black);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.setFont(Vals.FONT_MAIN);
		g.setColor(Color.white);
		// g.drawString(ipAddress.getText(), 700, 100);

		// add necessary buttons
		backButton.render();
		circleButton.render();
		
		connectStatus(g);

		// Text fields
		serverAddress.render(gc, g);
		g.drawString(serverStr, serverAddress.getX() - Vals.FONT_MAIN.getWidth(serverStr) - 10, 200);
		playerName.render(gc, g);
		g.drawString(playerStr, serverAddress.getX() - Vals.FONT_MAIN.getWidth(playerStr) - 10, 300);
	}
	
	private void connectStatus(Graphics g){
		if(!connecting){
			connectButton.render();
			if(connectFailed){
				g.drawString(connectFailString,
						connectButton.getCenterX() - Vals.FONT_MAIN.getWidth(connectFailString) / 2,
						connectButton.getY() + 100);
			}
		}else if (connecting){
			g.drawString(connectingString,
					connectButton.getCenterX() - Vals.FONT_MAIN.getWidth(connectingString) / 2,
					connectButton.getY() + 100);
		}else{
			g.drawString(waitingString, connectButton.getCenterX() - Vals.FONT_MAIN.getWidth(waitingString) / 2,
					connectButton.getY() + 100);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		String addressValue = serverAddress.getText();
		String nameValue = playerName.getText();
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
		connectButton.update(gc, game, mouseX, mouseY, addressValue, nameValue);
		circleButton.update(gc, game, mouseX, mouseY);

		if (toPlay) {
			playTest.testSetup();
			game.enterState(Vals.PLAY_TEST_STATE);
			toPlay = false;
		}
	}

	private void showConnecting(ConnectionAttemptEvent e) {
		connecting = true;
		connectFailed = false;
		connected = false;
	}

	private void connected(PlayerCreatedEvent e) {
		connecting = false;
		connected = true;
		connectFailed = false;
	}

	private void connectFail(ConnectionFailedEvent e) {
		connectFailed = true;
		connected = false;
		connecting = false;
	}

	@Override
	public int getID() {
		return Vals.CHARACTER_SELECT_STATE;
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ESCAPE:
			toPlay = true;
		}
	}
}
