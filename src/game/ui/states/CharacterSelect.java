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
 * The Class CharacterSelect.
 */
public class CharacterSelect extends BasicGameState {

	/** The back button. */
	private MenuButton backButton;

	/** The connect button. */
	private ConnectButton connectButton;

	/** The waiting. */
	private Image waiting;

	/** The player name. */
	private TextField serverAddress, playerName;

	/** The server str. */
	private String serverStr = "Enter Server Address:";

	/** The player str. */
	private String playerStr = "Enter Player Name:";

	/** The waiting string. */
	private String waitingString = "Connected: Waiting for ";

	/** The connect fail string. */
	private String connectFailString = "Connection failed: please check the ip and try again";

	/** The game full sring. */
	private String gameFullSring = "Game is already full, try a different server";

	/** The invalid name string. */
	private String invalidNameString = "Invalid name: Must be alphanumeric or underscore";

	/** The to play. */
	private boolean toPlay = false;

	/** The connected. */
	private boolean connected = false;

	/** The connect failed. */
	private boolean connectFailed = false;

	/** The invalid name. */
	private boolean invalidName = false;

	/** The game full. */
	private boolean gameFull = false;

	/** The play test. */
	private PlayTest playTest;

	/** The player left. */
	private int playerLeft = 0;

	/** The wg. */
	private WordGenerator wg;

	/**
	 * Constructor: Creates the character select state and starts event
	 * listeners.
	 *
	 * @param test
	 *            the test
	 */
	public CharacterSelect(PlayTest test) {
		this.playTest = test;
		Events.on(PlayerCreatedEvent.class, this::connected);
		Events.on(ConnectionFailedEvent.class, this::connectFail);
		Events.on(GameFullEvent.class, this::gameFull);
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
		wg = new WordGenerator();
		// adds the text fields
		addTextFields(gc);
	}

	/**
	 * Creates the text fields to be shown on the screen.
	 *
	 * @param gc
	 *            The game container
	 * @throws SlickException
	 *             the slick exception
	 */
	private void addTextFields(GameContainer gc) throws SlickException {
		// Server address text field.
		Vals.FONT_MAIN.addAsciiGlyphs();
		// necessary to load an effect otherwise an exception is thrown!!!
		Vals.FONT_MAIN.getEffects().add(new ColorEffect());
		Vals.FONT_MAIN.loadGlyphs();

		serverAddress = new TextField(gc, Vals.FONT_MAIN,
				(int) (Vals.TFIELD_ALIGN_CENTRE_W + wg.getWH(serverStr, 0.15f).getL() / 2), Vals.SCREEN_HEIGHT / 3,
				Vals.TFIELD_WIDTH, Vals.FONT_MAIN.getLineHeight(), new ComponentListener() {
					public void componentActivated(AbstractComponent src) {
						serverAddress.setFocus(true);
					}
				});

		serverAddress.setBackgroundColor(Color.white);
		serverAddress.setTextColor(Color.black);

		// Player name text field.
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
		// debugging
		g.setFont(Vals.FONT_MAIN);
		g.setColor(Color.white);

		// add necessary buttons
		backButton.render();

		// show connection status
		connectStatus(g);

		// Text fields
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

			// draw waiting spinner
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
	 * Sets connected to be true and connectFailed is false;.
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
	 * Sets connectFailed to be true and connected is false;.
	 *
	 * @param e
	 *            the e
	 */
	private void connectFail(ConnectionFailedEvent e) {
		connectFailed = true;
		connected = false;
	}

	/**
	 * Game full.
	 *
	 * @param e
	 *            the e
	 */
	private void gameFull(GameFullEvent e) {
		gameFull = true;
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

	/**
	 * Sets the invalid name.
	 *
	 * @param toSet
	 *            the new invalid name
	 */
	public void setInvalidName(boolean toSet) {
		invalidName = toSet;
	}
}
