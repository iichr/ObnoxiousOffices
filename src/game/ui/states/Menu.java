package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.MusicLocations;
import game.ui.interfaces.Vals;

public class Menu extends BasicGameState implements MusicListener{

	private MenuButton playButton, optionsButton, rulesButton, exitButton;
	private String mouseCoords = "No input yet!";
	private Music music;

	public Menu(int state) {

	}

	@Override
	public int getID() {
		return Vals.MENU_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		
		Image play = new Image(ImageLocations.PLAY);
		Image playR = new Image(ImageLocations.PLAY_ROLLOVER);
		playButton = new MenuButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H - 150, Vals.BUTTON_WIDTH,
				Vals.BUTTON_HEIGHT, play, playR);

		Image options = new Image(ImageLocations.OPTIONS);
		Image optionsR = new Image(ImageLocations.OPTIONS_ROLLOVER);
		optionsButton = new MenuButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H - 50, Vals.BUTTON_WIDTH,
				Vals.BUTTON_HEIGHT, options, optionsR);

		Image rules = new Image(ImageLocations.RULES);
		Image rulesR = new Image(ImageLocations.RULES_ROLLOVER);
		rulesButton = new MenuButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H + 50, Vals.BUTTON_WIDTH,
				Vals.BUTTON_HEIGHT, rules, rulesR);

		Image exit = new Image(ImageLocations.EXIT);
		Image exitR = new Image(ImageLocations.EXIT_ROLLOVER);
		exitButton = new MenuButton(Vals.BUTTON_ALIGN_CENTRE_W, Vals.BUTTON_ALIGN_CENTRE_H + 150, Vals.BUTTON_WIDTH,
				Vals.BUTTON_HEIGHT, exit, exitR);
//		music = new Music (MusicLocations.MENU_MUSIC);
//		music.addListener(this);
//		music.setVolume(0.5f); 

	}
	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException{
	      //Start the music loop when you first enter the state, will not end until you use music.stop() or .pause() somewhere, even if you change states.
//	      music.loop();
	   }   
	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {

	}

	/*
	 * The main board of the menu screen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);

		// draw buttons
		playButton.render(g);
		optionsButton.render(g);
		rulesButton.render(g);
		exitButton.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		float mouseX = Mouse.getX();
		float mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		// set button properties
		playButton.onClick(gc, game, mouseX, mouseY, Vals.CHARACTER_SELECT_STATE);
		optionsButton.onClick(gc, game, mouseX, mouseY, Vals.OPTIONS_STATE);
		rulesButton.onClick(gc, game, mouseX, mouseY, Vals.RULES_STATE);
		exitButton.onClick(gc, game, mouseX, mouseY, Vals.EXIT);
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