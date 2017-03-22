package game.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.core.chat.Chat;
import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.event.minigame.MiniGameStartedEvent;
import game.core.minigame.MiniGame;
import game.core.minigame.MiniGameHangman;
import game.core.minigame.MiniGamePong;
import game.core.player.Player;
import game.core.player.state.PlayerState;
import game.core.world.World;
import game.ui.components.ChatBox;
import game.ui.components.Controls;
import game.ui.components.Effect;
import game.ui.components.Renderer;
import game.ui.components.WordGenerator;
import game.ui.interfaces.Vals;
import game.ui.overlay.GameOverOverlay;
import game.ui.overlay.HangmanOverlay;
import game.ui.overlay.OptionsOverlay;
import game.ui.overlay.PongOverlay;
import game.ui.player.ActionSelector;
import game.ui.player.PlayerInfo;
import game.ui.player.PlayerOverview;

/**
 * The state where the game is played in.
 */
public class Play extends BasicGameState {

	// world info
	protected World world;
	protected String localPlayerName;

	// time info
	private final int rateMilliseconds = 250;
	private final int rateMillisecondsPong = 50;
	private long lastMove = 0;

	// helper objects
	protected Controls controls;
	protected Renderer renderer;
	protected WordGenerator wg;

	// tile info
	private float tileWidth;
	private float tileHeight;

	// status container
	private PlayerOverview playerOverview;

	// actionSelector
	private ActionSelector actionSelector;

	// effect container
	protected Effect effectOverview;

	// player info
	private PlayerInfo playerinfo;

	// movement info
	protected int heldKey;

	// overlays
	private OptionsOverlay optionsOverlay;
	private GameOverOverlay gameOverOverlay;
	private HangmanOverlay hangmanOverlay;
	private PongOverlay pongOverlay;

	// boolean flags
	private boolean canMove;
	protected boolean options;
	protected boolean gameOver;
	protected boolean exit;
	private boolean playingPong;
	private boolean playingHangman;
	private boolean showOverview;

	// chatbox
	private ChatBox chatBox;

	// the background music
	private Music bgmusic;

	@Override
	public int getID() {
		return Vals.PLAY_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Load the Font
		Vals.FONT_PLAY.addAsciiGlyphs();
		Vals.FONT_PLAY.getEffects().add(new ColorEffect());
		Vals.FONT_PLAY.loadGlyphs();

		// listen for events
		Events.on(GameFinishedEvent.class, this::gameFinished);
		Events.on(MiniGameStartedEvent.class, this::startMinigame);
		Events.on(MiniGameEndedEvent.class, this::closeMinigame);

		// Initialise the background music
		bgmusic = new Music("res/music/main.ogg");

		chatBox = new ChatBox(gc, new Chat());
	}

	/**
	 * Sets up the play state: Should be called at the start of each game
	 */
	public void playSetup() {
		this.world = World.world;
		this.localPlayerName = Player.localPlayerName;

		controls = new Controls();

		// set boolean flags
		canMove = true;
		options = false;
		gameOver = false;
		exit = false;
		playingPong = false;
		playingHangman = false;
		showOverview = false;

		heldKey = -1;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// setup tile sizes
		tileWidth = (float) Vals.SCREEN_WIDTH / world.xSize;
		tileHeight = 2 * ((float) Vals.SCREEN_HEIGHT / (world.ySize + 2));

		wg = new WordGenerator();

		// set up renderer
		renderer = new Renderer(world, localPlayerName, tileWidth, tileHeight, showOverview);

		// set up player info
		playerinfo = new PlayerInfo(localPlayerName, tileWidth, tileHeight, wg);

		// Effect container
		effectOverview = new Effect(tileWidth, tileHeight);

		// player overview
		playerOverview = new PlayerOverview(localPlayerName, 0, 0);

		actionSelector = new ActionSelector(wg);

		// popUps
		optionsOverlay = new OptionsOverlay(wg);
		gameOverOverlay = new GameOverOverlay(world.getPlayers(), wg);
		hangmanOverlay = new HangmanOverlay(wg);
		pongOverlay = new PongOverlay(wg);

		// Play the background music in a loop
		bgmusic.loop();
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// stop the background music when a plater leaves the state
		bgmusic.stop();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		Player localPlayer = world.getPlayer(localPlayerName);
		// update the chatbox
		chatBox.update(gc, localPlayerName);
		// update the effects overview
		effectOverview.updateEffects(localPlayer);
		// update the player Overview container
		playerOverview.updateContainer();

		if (exit) {
			// enter the main menu state on exit
			game.enterState(Vals.MENU_STATE);
		}

		// disable player movement whilst in minigames
		long time = System.currentTimeMillis();
		if (playingPong) {
			if (time - lastMove >= rateMillisecondsPong) {
				canMove = true;
			}
		} else {
			if (time - lastMove >= rateMilliseconds) {
				canMove = true;
			}
		}

		if (canMove) {
			if (heldKey >= 0) {
				controls.manageMovement(actionSelector, localPlayerName, heldKey);
				canMove = false;
				lastMove = time;
			}
		}

		input.clearKeyPressedRecord();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(Vals.FONT_PLAY);
		boolean[][] visible = renderer.findVisibles();
		// renders world
		renderer.drawWorld(visible);
		chatBox.render(gc, g);

		// add effects overview container
		effectOverview.render();

		// shows selectors
		if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
			if (world.getPlayer(localPlayerName).status.getActions().size() == 0) {
				actionSelector.updateSelector(world, localPlayerName, tileWidth, tileHeight, g);
			}
		}

		// show ui info to player
		playerinfo.render(g, visible);
		if (gameOver) {
			gameOverOverlay.render(gc, sbg, g);
		} else if (options) {
			optionsOverlay.render(gc, g);
		} else if (playingHangman) {
			hangmanOverlay.render(g);
		} else if (playingPong) {
			pongOverlay.render(g);
		} else if (showOverview) {
			playerOverview.render(g);
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if (!gameOver) {
			if (playingPong) {
				// use the pong controls if in pong
				heldKey = controls.pongMoveStart(heldKey, key);
				chatBox = controls.toggleChat(chatBox, key);
			} else if (playingHangman) {
				// use hangman controls in hangman
				controls.hangman(localPlayerName, c);
				chatBox = controls.toggleChat(chatBox, key);
			} else {
				coreControls(key);
			}
		} else {
			exit = true;
		}
	}

	/**
	 * Manages controls for the core game
	 * 
	 * @param key
	 *            The id of the key pressed
	 */
	private void coreControls(int key) {
		heldKey = controls.coreMoveStart(heldKey, key);
		if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
			actionSelector = controls.selectorInput(actionSelector, localPlayerName, key);
		} else {
			controls.interaction(localPlayerName, key);
		}
		showOverview = controls.openOverview(showOverview, key);
		chatBox = controls.toggleChat(chatBox, key);
		options = controls.toggleOptions(options, key);
	}

	@Override
	public void keyReleased(int key, char c) {
		showOverview = controls.closeOverview(showOverview, key);
		heldKey = controls.coreMoveFinish(heldKey, key);
		heldKey = controls.pongMoveFinish(heldKey, key);
	}

	/**
	 * Sets game finished to be true;
	 * 
	 * @param e
	 *            A GameFinishedEvent
	 */
	private void gameFinished(GameFinishedEvent e) {
		gameOver = true;
		playingPong = false;
		playingHangman = false;
		showOverview = false;
	}

	/**
	 * Starts a minigame depending on the event passed
	 * 
	 * @param e
	 *            A MiniGameStartedEvent
	 */
	private void startMinigame(MiniGameStartedEvent e) {
		if (e.isLocal()) {
			Class<? extends MiniGame> cls = e.game.getClass();
			if (cls == MiniGameHangman.class)
				playingHangman = true;
			else if (cls == MiniGamePong.class)
				playingPong = true;
		}
	}

	/**
	 * Closes the current minigame
	 * 
	 * @param e
	 *            A MiniGameEndedEvent
	 */
	private void closeMinigame(MiniGameEndedEvent e) {
		if (e.isLocal()) {
			playingHangman = false;
			playingPong = false;
		}
	}
}
