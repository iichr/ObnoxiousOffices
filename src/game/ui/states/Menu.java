package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.components.MusicBox;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

/**
 * The state containing the Main game menu to be rendered.
 */
public class Menu extends BasicGameState {

	// the menu buttons
	private MenuButton playButton;
	private MenuButton optionsButton;
	private MenuButton rulesButton;
	private MenuButton exitButton;

	// The music and sound
	private Music music;
	private MusicBox musicBox;

	// The background image the menu is set against
	private Image background;
	private Image logo;

	// An array of buttons that follows the order of menu
	private MenuButton buttons[];
	private int values[] = new int[] { Vals.CHARACTER_SELECT_STATE, Vals.OPTIONS_STATE, Vals.RULES_STATE, Vals.EXIT };

	private int CURRENT = 0;

	@Override
	public int getID() {
		return Vals.MENU_STATE;
	}
	
	public void setDependencies(Music music, MusicBox musicBox) {
		this.music = music;
		this.musicBox = musicBox;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		// initialise all menu buttons' positions and sizes
		background = new Image(ImageLocations.BACKGROUND, false, Image.FILTER_NEAREST);
		logo = new Image(ImageLocations.LOGO, false, Image.FILTER_NEAREST);
		Image play = new Image(ImageLocations.PLAY).getScaledCopy(0.8f);
		Image playR = new Image(ImageLocations.PLAY_ROLLOVER).getScaledCopy(0.8f);
		float padding = Vals.SCREEN_HEIGHT / 15;

		playButton = new MenuButton(Vals.SCREEN_WIDTH / 2 - (play.getWidth() / 2), Vals.SCREEN_HEIGHT / 2 - padding,
				play.getWidth(), play.getHeight(), play, playR);

		Image options = new Image(ImageLocations.OPTIONS).getScaledCopy(0.8f);
		Image optionsR = new Image(ImageLocations.OPTIONS_ROLLOVER).getScaledCopy(0.8f);
		optionsButton = new MenuButton(Vals.SCREEN_WIDTH / 2 - (options.getWidth() / 2),
				Vals.SCREEN_HEIGHT / 2 + padding, options.getWidth(), options.getHeight(), options, optionsR);

		Image rules = new Image(ImageLocations.RULES).getScaledCopy(0.8f);
		Image rulesR = new Image(ImageLocations.RULES_ROLLOVER).getScaledCopy(0.8f);

		rulesButton = new MenuButton(Vals.SCREEN_WIDTH / 2 - rules.getWidth() / 2, Vals.SCREEN_HEIGHT / 2 + 3 * padding,
				rules.getWidth(), rules.getHeight(), rules, rulesR);

		Image exit = new Image(ImageLocations.EXIT).getScaledCopy(0.8f);
		Image exitR = new Image(ImageLocations.EXIT_ROLLOVER).getScaledCopy(0.8f);
		exitButton = new MenuButton(Vals.SCREEN_WIDTH / 2 - exit.getWidth() / 2, Vals.SCREEN_HEIGHT / 2 + 5 * padding,
				exit.getWidth(), exit.getHeight(), exit, exitR);

		// add all buttons to an array
		buttons = new MenuButton[] { playButton, optionsButton, rulesButton, exitButton };

	}

	@Override
	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException {
		// manage music when the game is entered.
		if (music.playing()) {
			music.resume();
		} else {
			music.loop();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// draw the background
		background.draw(0, 0, Vals.SCREEN_WIDTH, Vals.SCREEN_HEIGHT, Color.darkGray);
		logo.draw(Vals.SCREEN_WIDTH / 4, Vals.SCREEN_HEIGHT / 8, Vals.SCREEN_WIDTH / 2, Vals.SCREEN_HEIGHT / 4);

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

		// set button properties
		playButton.update(gc, game, mouseX, mouseY, Vals.CHARACTER_SELECT_STATE);
		optionsButton.update(gc, game, mouseX, mouseY, Vals.OPTIONS_STATE);
		rulesButton.update(gc, game, mouseX, mouseY, Vals.RULES_STATE);
		exitButton.update(gc, game, mouseX, mouseY, Vals.EXIT);

		for (int i = 0; i < buttons.length; i++) {
			buttons[i].update(gc, game, i == CURRENT, values[CURRENT]);
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		// handle movement through the menu with the UP and DOWN keys
		switch (key) {
		case Input.KEY_DOWN:
			musicBox.playPressed();
			if (CURRENT == (buttons.length - 1)) {
				CURRENT = 0;
			} else {
				CURRENT += 1;
			}
			break;
		case Input.KEY_UP:
			musicBox.playPressed();
			if (CURRENT == 0) {
				CURRENT = (buttons.length - 1);
			} else {
				CURRENT -= 1;
			}
			break;
		}
	}
}