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
import game.core.player.PlayerState;
import game.core.world.World;
import game.ui.components.ChatBox;
import game.ui.components.Controls;
import game.ui.components.Effect;
import game.ui.components.Renderer;
import game.ui.components.WordGenerator;
import game.ui.interfaces.Vals;
import game.ui.overlay.GameOverOverlay;
import game.ui.overlay.HangmanOverlay;
import game.ui.overlay.HelpOverlay;
import game.ui.overlay.OptionsOverlay;
import game.ui.overlay.PongOverlay;
import game.ui.player.ActionSelector;
import game.ui.player.PlayerInfo;
import game.ui.player.PlayerOverview;

public class Play extends BasicGameState {
	// private String mouseCoords = "No input yet!";

	// world info
	protected World world;
	protected String localPlayerName;

	// time info
	private final int rateMilliseconds = 250;
	private final int rateMillisecondsPong = 50;
	private long lastMove = 0;

	// helper objects
	protected Controls ctrs;
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
	private HelpOverlay helpOverlay;

	// boolean flags
	private boolean canMove;
	protected boolean options;
	protected boolean gameOver;
	protected boolean exit;
	private boolean playingPong;
	private boolean playingHangman;
	private boolean showOverview;

	Music bgmusic;
	private ChatBox cb;

	@Override
	public int getID() {
		return Vals.PLAY_STATE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Font
		Vals.FONT_PLAY.addAsciiGlyphs();
		Vals.FONT_PLAY.getEffects().add(new ColorEffect());
		Vals.FONT_PLAY.loadGlyphs();

		// listen for events
		Events.on(GameFinishedEvent.class, this::gameFinished);
		Events.on(MiniGameStartedEvent.class, this::startMinigame);
		Events.on(MiniGameEndedEvent.class, this::closeMinigame);

		// KEEP COMMENTED until we've all added the required libraries.
		// Initialise the background music
		bgmusic = new Music("res/music/main.ogg");

		cb = new ChatBox(gc, new Chat());
	}

	/**
	 * Sets up the play state: Should be called at the start of each game
	 */
	public void playSetup() {
		this.world = World.world;
		this.localPlayerName = Player.localPlayerName;

		ctrs = new Controls();

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
		// UNCOMMENT until everybody add the required libraries.
		// start the background music in a loop
		// bgmusic.loop();

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
		helpOverlay = new HelpOverlay(wg);
		bgmusic.loop();
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// KEEP COMMENTED until everybody has added the required libraries.
		// used to stop the music from playing
		// bgmusic.stop();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		Player localPlayer = world.getPlayer(localPlayerName);

		cb.update(gc, localPlayerName);

		effectOverview.updateEffects(localPlayer);

		playerOverview.updateContainer();

		if (exit) {
			game.enterState(Vals.MENU_STATE);
		}

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
				ctrs.manageMovement(actionSelector, localPlayerName, heldKey);
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
		cb.render(gc, g);

		// add effects overview container
		effectOverview.render();

		// shows selectors
		if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
			if (world.getPlayer(localPlayerName).status.getActions().size() == 0) {
				actionSelector.updateSelector(world, localPlayerName, tileWidth, tileHeight, g);
			}
		}

		// show ui info to player
		int choice = helpOverlay.getHelp();
		playerinfo.render(g, visible);
		if (gameOver) {
			gameOverOverlay.render(g);
		} else if (options) {
			if (choice == 0) {
				helpOverlay.render(gc, g);
			} else if (options && choice == 1) {
				// optionsOverlay.render(gc, g);
			} else if (options && choice == 2) {
				optionsOverlay.render(gc, g);
			}

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
				heldKey = ctrs.pongMoveStart(heldKey, key);
			} else if (playingHangman) {
				ctrs.hangman(localPlayerName, c);
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
		heldKey = ctrs.coreMoveStart(heldKey, key);
		if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
			actionSelector = ctrs.selectorInput(actionSelector, localPlayerName, key);
		} else {
			ctrs.interaction(localPlayerName, key);
		}
		showOverview = ctrs.openOverview(showOverview, key);
		cb = ctrs.toggleChat(cb, key);
		options = ctrs.toggleOptions(options, key);

		// TEMPORARY MINIGAME TESTING
		switch (key) {
		// TEMPORARY FOR TESTING HANGMAN - PRESS 9
		case Input.KEY_9:
			MiniGame.localMiniGame = new MiniGameHangman("chris");
			playingHangman = true;
			// System.out.println("ENTERED HANGMAN");
			break;
		// TEMPORARY FOR TESTING PONG - PRESS 8
		case Input.KEY_8:
			MiniGame.localMiniGame = new MiniGamePong("tim", localPlayerName);
			playingPong = true;
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		showOverview = ctrs.closeOverview(showOverview, key);
		heldKey = ctrs.coreMoveFinish(heldKey, key);
		heldKey = ctrs.pongMoveFinish(heldKey, key);
	}

	/**
	 * Sets game finished to be true;
	 * 
	 * @param e
	 *            A GameFinishedEvent
	 */
	private void gameFinished(GameFinishedEvent e) {
		gameOver = true;
	}

	/**
	 * Starts a minigame depending on the event passed
	 * 
	 * @param e
	 *            A MiniGameStartedEvent
	 */
	private void startMinigame(MiniGameStartedEvent e) {
		if (e.game.isLocal()) {
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
		playingHangman = false;
		playingPong = false;
	}
}
