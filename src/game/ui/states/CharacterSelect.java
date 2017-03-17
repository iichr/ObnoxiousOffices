package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
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

import game.core.event.ConnectionFailedEvent;
import game.core.event.Events;
import game.core.event.player.PlayerCreatedEvent;
import game.ui.buttons.ConnectButton;
import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

public class CharacterSelect extends BasicGameState {
	private MenuButton backButton;
	private ConnectButton connectButton;
	private Image waiting;

	private TextField serverAddress, playerName;
	private String serverStr = "Enter Server Address:";
	private String playerStr = "Enter Player Name:";
	private String waitingString = "Connected: Waiting for ";	
	private String connectFailString = "Connection failed: please check the ip and try again";
	private String invalidNameString = "Invalid name, must be alphanumeric or underscore";

	private boolean toPlay = false;
	private boolean connected = false;
	private boolean connectFailed = false;
	private boolean invalidName = false;
	private PlayTest playTest;
	private int playerLeft = 0 ;

	/**
	 * Constructor: Creates the character select state and starts event
	 * listeners
	 * 
	 * @param test
	 */
	public CharacterSelect(PlayTest test) {
		this.playTest = test;
		Events.on(PlayerCreatedEvent.class, this::connected);
		Events.on(ConnectionFailedEvent.class, this::connectFail);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		Image back = new Image(ImageLocations.BACK, false);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

		Image conn = new Image(ImageLocations.CONNECT);
		Image connR = new Image(ImageLocations.CONNECT_ROLLOVER);
		connectButton = new ConnectButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H + 150,
				Vals.BUTTON_WIDTH, Vals.BUTTON_HEIGHT, conn, connR);

		waiting = new Image(ImageLocations.WAITING, false, Image.FILTER_NEAREST);

		// adds the text fields
		addTextFields(gc);
	}

	/**
	 * Creates the text fields to be shown on the screen
	 * 
	 * @param gc
	 *            The game container
	 * @throws SlickException
	 */
	private void addTextFields(GameContainer gc) throws SlickException {
		// Server address text field.
		Vals.FONT_MAIN.addAsciiGlyphs();
		// necessary to load an effect otherwise an exception is thrown!!!
		Vals.FONT_MAIN.getEffects().add(new ColorEffect());
		Vals.FONT_MAIN.loadGlyphs();

		serverAddress = new TextField(gc, Vals.FONT_MAIN, Vals.TFIELD_ALIGN_CENTRE_W, Vals.SCREEN_HEIGHT / 3,
				Vals.TFIELD_WIDTH, Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						serverAddress.setFocus(true);
					}
				});

		serverAddress.setBackgroundColor(Color.white);
		serverAddress.setTextColor(Color.black);
		
		

		// Player name text field.
		playerName = new TextField(gc, Vals.FONT_MAIN, Vals.TFIELD_ALIGN_CENTRE_W, Vals.SCREEN_HEIGHT / 4,
				Vals.TFIELD_WIDTH, Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						playerName.setFocus(true);
					}
				});
		playerName.setBackgroundColor(Color.white);
		playerName.setTextColor(Color.black);
		playerName.setMaxLength(30);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.setFont(Vals.FONT_MAIN);
		g.setColor(Color.white);

		// add necessary buttons
		backButton.render();

		// show connection status
		connectStatus(g);

		// Text fields
		serverAddress.render(gc, g);
		g.drawString(serverStr, serverAddress.getX() - Vals.FONT_MAIN.getWidth(serverStr) - 10, Vals.SCREEN_HEIGHT / 3);
		playerName.render(gc, g);
		g.drawString(playerStr, serverAddress.getX() - Vals.FONT_MAIN.getWidth(playerStr) - 10, Vals.SCREEN_HEIGHT / 4);
	}

	/**
	 * Renders the elements on the screen depending on the connection status of
	 * the client
	 * 
	 * @param g
	 *            The graphics object g
	 */
	private void connectStatus(Graphics g) {
		if (connected) {
			// make button inactive
			connectButton.setActive(false);

			// draw waiting spinner
			float waitingDiam = Vals.SCREEN_WIDTH / 20;
			waiting.setCenterOfRotation(waitingDiam / 2, waitingDiam / 2);
			waiting.rotate((float) -0.05);
			waiting.draw(Vals.SCREEN_WIDTH / 2 - waitingDiam / 2, connectButton.getY() - waitingDiam / 2, waitingDiam,
					waitingDiam);

			// display text
			g.drawString(waitingString+(playerLeft==0?"more players.":(playerLeft+" more players.")), connectButton.getCenterX() - Vals.FONT_MAIN.getWidth(waitingString) / 2,
					connectButton.getY() + waitingDiam / 2 + Vals.BUTTON_HEIGHT / 2);
		} else {
			connectButton.setActive(true);
			connectButton.render();
			if (invalidName) {
				g.drawString(invalidNameString,
						connectButton.getCenterX() - Vals.FONT_MAIN.getWidth(invalidNameString) / 2,
						connectButton.getY() + 3 * Vals.BUTTON_HEIGHT / 2);
			} else if (connectFailed) {
				g.drawString(connectFailString,
						connectButton.getCenterX() - Vals.FONT_MAIN.getWidth(connectFailString) / 2,
						connectButton.getY() + 3 * Vals.BUTTON_HEIGHT / 2);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		AppGameContainer container = (AppGameContainer) gc;
		Input input = gc.getInput();
		String addressValue = serverAddress.getText();
		String nameValue = playerName.getText();
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		if(input.isKeyPressed(Input.KEY_F11)){			
			container.setDisplayMode(800, 600, false);
		}
		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
		connectButton.update(gc, game, mouseX, mouseY, addressValue, nameValue, this);

		if (toPlay) {
			playTest.testSetup();
			game.enterState(Vals.PLAY_TEST_STATE);
			toPlay = false;
		}
	}

	/**
	 * Sets connected to be true and ensures connectFailed is false;
	 * 
	 * @param e
	 *            A PlayerCreatedEvent
	 */
	private void connected(PlayerCreatedEvent e) {
		connected = true;
		connectFailed = false;
		playerLeft =e.playersLeft;
	}

	/**
	 * Sets connectFailed to be true and ensures connected is false;
	 */
	private void connectFail(ConnectionFailedEvent e) {
		connectFailed = true;
		connected = false;
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

	public void setInvalidName(boolean toSet) {
		invalidName = toSet;
	}
}
