import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{
	
	public Game(String gamename) {
		super(gamename);
		this.addState(new Menu(Vals.MENU_STATE));
		this.addState(new Play(Vals.PLAY_STATE));
		this.addState(new Options(Vals.OPTIONS_STATE));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException{
		this.getState(Vals.MENU_STATE).init(gc, this);
		this.getState(Vals.PLAY_STATE).init(gc, this);
		this.getState(Vals.OPTIONS_STATE).init(gc,this);
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
			agc.start();
		}catch(SlickException e){
			e.printStackTrace();
			
		}
		
	}

}
