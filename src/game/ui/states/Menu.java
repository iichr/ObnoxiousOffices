package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.components.MusicBox;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.MusicLocations;
import game.ui.interfaces.Vals;

public class Menu extends BasicGameState implements MusicListener {

	private MenuButton playButton, optionsButton, rulesButton, exitButton;
	private String mouseCoords = "No input yet!";
	private Music music;
	private Image bg;
	//An array of buttons that follows the order of menu
    private MenuButton buttons[];
    private int values[]= new int[]{
    		Vals.CHARACTER_SELECT_STATE,
    		Vals.OPTIONS_STATE,
    		Vals.RULES_STATE,
    		Vals.EXIT
    };
    private int CURRENT = 0;
    private long lastInput = System.currentTimeMillis();
	private MusicBox mb;

	@Override
	public int getID() {
		return Vals.MENU_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		bg = new Image(ImageLocations.BG, false, Image.FILTER_NEAREST);
		Image play = new Image(ImageLocations.PLAY).getScaledCopy(0.8f);
		Image playR = new Image(ImageLocations.PLAY_ROLLOVER).getScaledCopy(0.8f);
		float padding=Vals.SCREEN_HEIGHT/15;

		playButton = new MenuButton(Vals.SCREEN_WIDTH/2-(play.getWidth()/2), Vals.SCREEN_HEIGHT/2- padding,
				play.getWidth(), play.getHeight(), play, playR);

		Image options = new Image(ImageLocations.OPTIONS).getScaledCopy(0.8f);
		Image optionsR = new Image(ImageLocations.OPTIONS_ROLLOVER).getScaledCopy(0.8f);
		optionsButton = new MenuButton(Vals.SCREEN_WIDTH/2 - (options.getWidth()/2), Vals.SCREEN_HEIGHT/2  + padding  ,options.getWidth(),
				options.getHeight(), options, optionsR);

		Image rules = new Image(ImageLocations.RULES).getScaledCopy(0.8f);
		Image rulesR = new Image(ImageLocations.RULES_ROLLOVER).getScaledCopy(0.8f);

		rulesButton = new MenuButton(Vals.SCREEN_WIDTH/2 - rules.getWidth()/2, Vals.SCREEN_HEIGHT/2 +3* padding, rules.getWidth(),
				rules.getHeight(), rules, rulesR);

		Image exit = new Image(ImageLocations.EXIT).getScaledCopy(0.8f);
		Image exitR = new Image(ImageLocations.EXIT_ROLLOVER).getScaledCopy(0.8f);
		exitButton = new MenuButton(Vals.SCREEN_WIDTH/2-exit.getWidth()/2, Vals.SCREEN_HEIGHT/2 + 5*padding, exit.getWidth(),
				exit.getHeight(), exit, exitR);

		mb=new MusicBox(gc);
		 music = new Music (MusicLocations.MENU_MUSIC);
		// music.addListener(this);
		// music.setVolume(0.5f);
		buttons= new MenuButton[]{ playButton,
	    		optionsButton,
	    		rulesButton,
	    		exitButton};

	}

	@Override
	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException {
		// Start the music loop when you first enter the state, will not end
		// until you use music.stop() or .pause() somewhere, even if you change
		// states.
		if(music.playing()){
			music.resume();
		}else{
		 music.loop();
		}
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {
		music.pause();
	}

	/*
	 * The main board of the menu screen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);

		//put the background on

        bg.draw(0,0,Vals.SCREEN_WIDTH,Vals.SCREEN_HEIGHT);
        // draw buttons
        
		playButton.render();
		optionsButton.render();
		rulesButton.render();
		exitButton.render();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		float mouseX = Mouse.getX();
		float mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		// set button properties
		playButton.update(gc, game, mouseX, mouseY, Vals.CHARACTER_SELECT_STATE);
		optionsButton.update(gc, game, mouseX, mouseY, Vals.OPTIONS_STATE);
		rulesButton.update(gc, game, mouseX, mouseY, Vals.RULES_STATE);
		exitButton.update(gc, game, mouseX, mouseY, Vals.EXIT);
	
		for(int i=0 ; i< buttons.length ;i++){
            buttons[i].update(gc, game, i==CURRENT, values[CURRENT]);
        }

		// Add a boolean function to button.update
	}
	
	@Override
	public void keyPressed(int key, char c) {
		switch (key){
		case Input.KEY_DOWN:
			mb.playPressed();
			if (CURRENT == (buttons.length-1)) {
                CURRENT = 0;
            } else {
                CURRENT += 1;
            }
			break;
		case Input.KEY_UP:
			mb.playPressed();
			if (CURRENT == 0) {
                CURRENT = (buttons.length-1);
            } else {
                CURRENT -= 1;
            }
			break;
		}
	}

	@Override
	public void musicEnded(Music arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void musicSwapped(Music arg0, Music arg1) {
		// TODO Auto-generated method stub

	}
}