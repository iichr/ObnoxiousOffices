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
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

public class Menu extends BasicGameState implements MusicListener {

	private MenuButton playButton, optionsButton, rulesButton, exitButton;
	private String mouseCoords = "No input yet!";
	private Music music;
	private Image bg;
	private static boolean isWPressed = false;
	private static boolean isSPressed = false;
	private static boolean isEnterPressed = false;

	public Menu(int state) {

	}

	@Override
	public int getID() {
		return Vals.MENU_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		bg = new Image(ImageLocations.BG, false, Image.FILTER_NEAREST);
		Image play = new Image(ImageLocations.PLAY);
		Image playR = new Image(ImageLocations.PLAY_ROLLOVER);

		playButton = new MenuButton(Vals.SCREEN_WIDTH/4 - (play.getWidth()/2), Vals.BUTTON_ALIGN_CENTRE_H - 50,
				play.getWidth(), play.getHeight(), play, playR);

		Image options = new Image(ImageLocations.OPTIONS);
		Image optionsR = new Image(ImageLocations.OPTIONS_ROLLOVER);
		optionsButton = new MenuButton(3*Vals.SCREEN_WIDTH/4 - (options.getWidth()/2), Vals.BUTTON_ALIGN_CENTRE_H - 50, options.getWidth(),
				options.getHeight(), options, optionsR);

		Image rules = new Image(ImageLocations.RULES);
		Image rulesR = new Image(ImageLocations.RULES_ROLLOVER);

		rulesButton = new MenuButton(Vals.BUTTON_ALIGN_CENTRE_W/2 - 240, Vals.BUTTON_ALIGN_CENTRE_H + 150, rules.getWidth(),
				rules.getHeight(), rules, rulesR);

		Image exit = new Image(ImageLocations.EXIT);
		Image exitR = new Image(ImageLocations.EXIT_ROLLOVER);
		exitButton = new MenuButton(3*Vals.BUTTON_ALIGN_CENTRE_W/2 - 140, Vals.BUTTON_ALIGN_CENTRE_H + 150, exit.getWidth(),
				exit.getHeight(), exit, exitR);


		// music = new Music (MusicLocations.MENU_MUSIC);
		// music.addListener(this);
		// music.setVolume(0.5f);

	}

	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException {
		// Start the music loop when you first enter the state, will not end
		// until you use music.stop() or .pause() somewhere, even if you change
		// states.
		// music.loop();
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
		Input input = gc.getInput();
		float mouseX = Mouse.getX();
		float mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
		isEnterPressed = input.isKeyPressed(Vals.ENTER);
		isWPressed = input.isKeyPressed(Vals.UP);
		isSPressed = input.isKeyPressed(Vals.DOWN);

		// set button properties
		playButton.update(gc, game, mouseX, mouseY, Vals.CHARACTER_SELECT_STATE);
		playButton.update(gc,game);
		optionsButton.update(gc, game, mouseX, mouseY, Vals.OPTIONS_STATE);
		optionsButton.update(gc,game);
		rulesButton.update(gc, game, mouseX, mouseY, Vals.RULES_STATE);
		rulesButton.update(gc,game);
		exitButton.update(gc, game, mouseX, mouseY, Vals.EXIT);
		exitButton.update(gc,game);

		// Add a boolean function to button.update
	}

	@Override
	public void musicEnded(Music arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void musicSwapped(Music arg0, Music arg1) {
		// TODO Auto-generated method stub

	}

	public static boolean isSPressed() {
		return isSPressed;
	}

	public static boolean isWPressed() {
		return isWPressed;
	}

	public static boolean isEnterPressed() {
		return isEnterPressed;
	}
}