import java.io.File;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{
	
	public static final String gamename = " Offices " ;
	public static final int menu = 0 ;
	public static final int play = 1; 
	
	public Game(String gamename) {
		super(gamename);
		this.addState(new Menu(menu));
		this.addState(new Play(play));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException{
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.enterState(menu);
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
			agc = new AppGameContainer(new Game(gamename));
			agc.setDisplayMode(600,350,false);
			agc.start();
		}catch(SlickException e){
			e.printStackTrace();
			
		}
		
	}

}
