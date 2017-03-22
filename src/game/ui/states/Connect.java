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
import game.core.event.GameFullEvent;
import game.core.event.player.PlayerCreatedEvent;
import game.ui.buttons.ConnectButton;
import game.ui.buttons.MenuButton;
import game.ui.components.WordGenerator;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

/**
 * The state containing the connect screen where a player is prompted to input a
 * player name and server address and connect to a game
 */
public class Connect extends BasicGameState {

	// the menu and connect buttons
	private MenuButton backButton;
	private ConnectButton connectButton;

	// the spiral of death
	private Image waiting;

	// Input fields on the screen
	private String serverStr = "Enter Server Address:";
	private String playerStr = "Enter Player Name:";
	private TextField serverAddress;
	private TextField playerName;

	// information in response to events
	private String waitingString = "Connected: Waiting for ";
	private String connectFailString = "Connection failed: please check the ip and try again";
	private String gameFullSring = "Game is already full, try a different server";
	private String invalidNameString = "Invalid name: Must be alphanumeric or underscore";

	private boolean toPlay = false;

	// Connection status
	private boolean connected = false;
	private boolean connectFailed = false;

	// invalid name check
	private boolean invalidName = false;

	// have enough people connected to the game
	private boolean gameFull = false;

	private PlayTest playTest;
	private int playerLeft = 0;
	private WordGenerator wg;

	/**
	 * [USE ONLY IN TESTING] Creates the connect state and starts the necessary
	 * event listeners.
	 *
	 * @param test
	 *            The play test state
	 * @param wg
	 */
	public Connect(PlayTest test) {
		this.playTest = test;
		Events.on(PlayerCreatedEvent.class, this::connected);
		Events.on(ConnectionFailedEvent.class, this::connectFail);
		Events.on(GameFullEvent.class, this::gameFull);
	}
	
	public void setWG(WordGenerator wg) {
		this.wg = wg;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		// all button images
		Image back = new Image(ImageLocations.BACK, false);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

		Image conn = new Image(ImageLocations.CONNECT);
		Image connR = new Image(ImageLocations.CONNECT_ROLLOVER);
		connectButton = new ConnectButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H + 150,
				Vals.BUTTON_WIDTH, Vals.BUTTON_HEIGHT, conn, connR);

		// the waiting spiral of death
		waiting = new Image(ImageLocations.WAITING, false, Image.FILTER_NEAREST);
		// adds the text fields
		addTextFields(gc);
	}

	/**
	 * Creates the text fields to be shown on the screen.
	 *
	 * @param gc
	 *            The game container
	 * @throws SlickException
	 */
	@SuppressWarnings("unchecked")
	private void addTextFields(GameContainer gc) throws SlickException {
		// load the font
		Vals.FONT_MAIN.addAsciiGlyphs();
		Vals.FONT_MAIN.getEffects().add(new ColorEffect());
		Vals.FONT_MAIN.loadGlyphs();

		// Server address text field
		serverAddress = new TextField(gc, Vals.FONT_MAIN,
				(int) (Vals.TFIELD_ALIGN_CENTRE_W + wg.getWH(serverStr, 0.15f).getL() / 2), Vals.SCREEN_HEIGHT / 3,
				Vals.TFIELD_WIDTH, Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						serverAddress.setFocus(true);
					}
				});

		serverAddress.setBackgroundColor(Color.white);
		serverAddress.setTextColor(Color.black);

		// Player name text field
		playerName = new TextField(gc, Vals.FONT_MAIN,
				(int) (Vals.TFIELD_ALIGN_CENTRE_W + wg.getWH(playerStr, 0.15f).getL() / 2), Vals.SCREEN_HEIGHT / 4,
				Vals.TFIELD_WIDTH, Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						playerName.setFocus(true);
					}
				});
		playerName.setBackgroundColor(Color.white);
		playerName.setTextColor(Color.black);
		playerName.setMaxLength(15);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		toPlay = false;
		connected = false;
		connectFailed = false;
		invalidName = false;
		gameFull = false;
		serverAddress.setText("");
		playerName.setText("");

	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.setFont(Vals.FONT_MAIN);
		g.setColor(Color.white);

		// add the back button
		backButton.render();
		// show connection status
		connectStatus(g);

		// Render the text fields
		serverAddress.render(gc, g);
		wg.draw(g, serverStr, serverAddress.getX() - wg.getWH(serverStr, 0.15f).getL(), Vals.SCREEN_HEIGHT / 3, false,
				0.15f);
		playerName.render(gc, g);
		wg.draw(g, playerStr, playerName.getX() - wg.getWH(playerStr, 0.15f).getL(), Vals.SCREEN_HEIGHT / 4, false,
				0.15f);
	}

	/**
	 * Renders the elements on the screen depending on the connection status of
	 * the client.
	 *
	 * @param g
	 *            The graphics object g
	 */
	private void connectStatus(Graphics g) {
		if (connected) {
			// make button inactive
			connectButton.setActive(false);

			// draw waiting spiral of death
			float waitingDiam = Vals.SCREEN_WIDTH / 20;
			waiting.setCenterOfRotation(waitingDiam / 2, waitingDiam / 2);
			waiting.rotate((float) -0.05);
			waiting.draw(Vals.SCREEN_WIDTH / 2 - waitingDiam / 2, connectButton.getY() - waitingDiam / 2, waitingDiam,
					waitingDiam);

			// display text
			wg.drawCenter(g, waitingString + (playerLeft == 0 ? " more players" : (playerLeft + " more players")),
					connectButton.getCenterX(), connectButton.getY() + waitingDiam / 2 + Vals.BUTTON_HEIGHT / 2, false,
					0.15f);
		} else {
			// still not connected
			connectButton.setActive(true);
			connectButton.render();
			if (invalidName) {
				wg.drawCenter(g, invalidNameString, connectButton.getCenterX(),
						connectButton.getY() + 3 * Vals.BUTTON_HEIGHT / 2, false, 0.15f);
			} else if (connectFailed) {
				wg.drawCenter(g, connectFailString, connectButton.getCenterX(),
						connectButton.getY() + 3 * Vals.BUTTON_HEIGHT / 2, false, 0.15f);
			} else if (gameFull) {
				wg.drawCenter(g, gameFullSring, connectButton.getCenterX(),
						connectButton.getY() + 3 * Vals.BUTTON_HEIGHT / 2, false, 0.15f);
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
		if (input.isKeyPressed(Input.KEY_F11)) {
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
	 * Sets connected to be true and connectFailed is false when a player
	 * successfully connects.
	 *
	 * @param e
	 *            A PlayerCreatedEvent
	 */
	private void connected(PlayerCreatedEvent e) {
		connected = true;
		connectFailed = false;
		playerLeft = e.playersLeft;
	}

	/**
	 * Sets connectFailed to be true and connected is false when player fails to
	 * connect.
	 *
	 * @param e
	 *            The connection failed event
	 */
	private void connectFail(ConnectionFailedEvent e) {
		connectFailed = true;
		connected = false;
	}

	/**
	 * Is the game full?
	 *
	 * @param e
	 *            the game full event
	 */
	private void gameFull(GameFullEvent e) {
		gameFull = true;
	}

	/**
	 * Sets the invalid name.
	 *
	 * @param toSet
	 *            the new invalid name
	 */
	public void setInvalidName(boolean toSet) {
		invalidName = toSet;
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
