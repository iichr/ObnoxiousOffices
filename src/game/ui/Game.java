package game.ui;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.core.world.World;
import game.ui.components.MusicBox;
import game.ui.components.WordGenerator;
import game.ui.interfaces.MusicLocations;
import game.ui.interfaces.Vals;
import game.ui.states.Connect;
import game.ui.states.Intro;
import game.ui.states.KeyOptions;
import game.ui.states.Menu;
import game.ui.states.Options;
import game.ui.states.Play;
import game.ui.states.Rules;

public class Game extends StateBasedGame {
	private Intro introState;
	private Play playState;
	private Menu menuState;
	private Options optionsState;
	private Rules rulesState;
	private Connect connectState;
	private KeyOptions keyOptionsState;

	/**
	 * Constructor: sets up states and event listeners
	 * 
	 * @param gamename
	 *            The name of the game
	 */
	public Game(String gamename) {
		super(gamename);

		introState = new Intro();
		this.addState(introState);
		menuState = new Menu();
		this.addState(menuState);
		playState = new Play();
		this.addState(playState);
		optionsState = new Options();
		this.addState(optionsState);
		rulesState = new Rules();
		this.addState(rulesState);
		connectState = new Connect();
		this.addState(connectState);
		keyOptionsState = new KeyOptions();
		this.addState(keyOptionsState);

		Events.on(GameStartedEvent.class, this::onGameStart);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		// initialises states automatically

		WordGenerator wg = new WordGenerator();
		// initialise music and sound
		gc.setMusicVolume(0.5f);
		MusicBox musicBox = new MusicBox(gc);
		Music music = new Music(MusicLocations.MENU_MUSIC);
		

		menuState.setDependencies(music, musicBox);
		playState.setDependencies(wg);
		optionsState.setDependencies(wg, musicBox);
		connectState.setDepenedencies(wg, music);
		keyOptionsState.setDependencies(wg);

	}

	/**
	 * On game started event, set up play state and enter it.
	 * 
	 * @param event
	 */
	public void onGameStart(GameStartedEvent event) {
		World.world = event.world;
		playState.playSetup();
		this.enterState(Vals.PLAY_STATE);
	}

	/**
	 * Initialises the window and UI environment
	 */
	public static void init() {

		File JGLLib = null;

		switch (LWJGLUtil.getPlatform()) {
		case LWJGLUtil.PLATFORM_WINDOWS: {
			JGLLib = new File("natives/windows/");
		}
			break;

		case LWJGLUtil.PLATFORM_LINUX: {
			JGLLib = new File("natives/linux/");
		}
			break;

		case LWJGLUtil.PLATFORM_MACOSX: {
			JGLLib = new File("natives/macosx/");
		}
			break;
		}

		System.setProperty("org.lwjgl.librarypath", JGLLib.getAbsolutePath());

		AppGameContainer agc;
		try {
			agc = new AppGameContainer(new Game(Vals.GAME_NAME));
			agc.setDisplayMode(Vals.SCREEN_WIDTH, Vals.SCREEN_HEIGHT, false);

			agc.setUpdateOnlyWhenVisible(true);
			agc.setMinimumLogicUpdateInterval(10);

			agc.setShowFPS(false);
			agc.setFullscreen(false);
			agc.start();

		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Main method used for testing UI elements on their own
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		init();
	}

}
