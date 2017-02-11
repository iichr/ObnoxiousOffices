package game.ui;

import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.ui.interfaces.Vals;
import game.ui.states.CharacterSelect;
import game.ui.states.Intro;
import game.ui.states.Menu;
import game.ui.states.Options;
import game.ui.states.Pause;
import game.ui.states.Play;
import game.ui.states.PlayTest;
import game.ui.states.Rules;

public class Game extends StateBasedGame {
	private Intro introState;
	private Play playState;
	private Menu menuState;
	private Options optionsState;
	private Rules rulesState;
	private CharacterSelect chSelectState;
	private Pause pauseState;
	private PlayTest playtestState;

	public Game(String gamename) {
		super(gamename);

//		EventDispatcher.subscribe2(this);
		introState= new Intro(Vals.INTRO_STATE);
		this.addState(introState);
		menuState = new Menu(Vals.MENU_STATE);
		this.addState(menuState);
		playState = new Play(Vals.PLAY_STATE);
		this.addState(playState);
		optionsState = new Options(Vals.OPTIONS_STATE);
		this.addState(optionsState);
		rulesState = new Rules(Vals.RULES_STATE);
		this.addState(rulesState);
		chSelectState = new CharacterSelect(Vals.CHARACTER_SELECT_STATE);
		this.addState(chSelectState);
		pauseState = new Pause(Vals.PAUSE_STATE);
		this.addState(pauseState);
		playtestState = new PlayTest(Vals.PLAY_TEST_STATE);
		this.addState(playtestState);
		
		Events.on(GameStartedEvent.class, this::onGameStart);
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		//initialises states automatically
	}
	
	public void onGameStart(GameStartedEvent event) {
		playState.playSetup(event.world);
		this.enterState(Vals.PLAY_STATE);
	}

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

			agc.setFullscreen(true);
			agc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		init();
	}

}
