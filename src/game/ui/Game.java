package game.ui;
import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import game.ui.interfaces.Vals;
import game.ui.states.CharacterSelect;
import game.ui.states.Menu;
import game.ui.states.Options;
import game.ui.states.Pause;
import game.ui.states.Play;
import game.ui.states.Rules;

public class Game extends StateBasedGame{
	
	public Game(String gamename) {
		super(gamename);
		this.addState(new Menu(Vals.MENU_STATE));
		this.addState(new Play(Vals.PLAY_STATE));
		this.addState(new Options(Vals.OPTIONS_STATE));
		this.addState(new Rules(Vals.RULES_STATE));
		this.addState(new CharacterSelect(Vals.CHARACTER_SELECT_STATE));
		this.addState(new Pause(Vals.PAUSE_STATE));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException{
		this.getState(Vals.MENU_STATE).init(gc, this);
		this.getState(Vals.PLAY_STATE).init(gc, this);
		this.getState(Vals.OPTIONS_STATE).init(gc,this);
		this.getState(Vals.RULES_STATE).init(gc, this);
		this.getState(Vals.CHARACTER_SELECT_STATE).init(gc, this);
		this.getState(Vals.PAUSE_STATE).init(gc, this);
		this.enterState(Vals.MENU_STATE);

	}
	
	public static void main(String[] args) {

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
		try{
			agc = new AppGameContainer(new Game(Vals.GAME_NAME));
			agc.setDisplayMode(Vals.SCREEN_WIDTH,Vals.SCREEN_HEIGHT,false);
			
			agc.setFullscreen(true);
			agc.start();
		}catch(SlickException e){
			e.printStackTrace();
			
		}
		
	}

}
