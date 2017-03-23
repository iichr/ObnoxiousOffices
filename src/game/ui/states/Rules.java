package game.ui.states;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;

/**
 * The rules page accessible from the main menu. Provides the user with a guide
 * on how to play.
 */
public class Rules extends BasicGameState {
	private MenuButton backButton;
	private String mouseCoords;
	private String gameTitle;
	private String rules;
	SpriteSheet allSprites;

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

		// Set up fonts
		ArrayList<UnicodeFont> fontList = new ArrayList<UnicodeFont>();
		fontList.add(Vals.FONT_RULES);
		fontList.add(Vals.FONT_HEADING1);

		for (UnicodeFont font : fontList) {
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();
		}

		Image sprites = new Image(SpriteLocations.RULES_SPRITES, false, Image.FILTER_NEAREST);
		allSprites = new SpriteSheet(sprites, 64, 128);

		// character encoding: \u0027 for apostrophe
		gameTitle = "DevWars";
		rules = "Your goal is to complete your programming project before the other players!\n"
				+ "Move around with the WASD keys, interact with objects by using the up and down arrow keys.\n"
				+ "Hold the TAB key whilst in game to see your and others' progress and fatigue levels.\n"
				+ "You have the following interactions available to meet your goal:"
				+ "\n\n"
				+ "Work - you can work only on your own computer, which helps you up your progress.\n"
				+ "You will be presented with a Hangman minigame where you have to guess the word \n" 
				+ "displayed to get progress."
				+ "\n\n"
				+ "Hack - you can hack another player from your own computer as well, by selecting Hack and that\n" 
				+ "player\u0027s name. You\u0027ll be presented with a game of Pong, where you control one of the paddles\n" 
				+ "and your objective is not to miss a ball."
				+ "\n\n"
				+ "Coffee Break - reduces your fatigue and increases your productivity. Be careful, it may have negative consequences."
				+ "\n\n"
				+ "Nap -  a risk-free way fo reducing your fatigue."
				+ "\n\n"
				+ "To view all controls available go to the Options menu and press More> at the bottom.";
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(Color.white);
		// debugging
		g.drawString(mouseCoords, 10, 50);

		// title
		g.setFont(Vals.FONT_HEADING1);
		g.drawString(gameTitle, (Vals.SCREEN_WIDTH - Vals.FONT_MAIN.getWidth(gameTitle)) / 2, 30);

		g.setFont(Vals.FONT_RULES);

		// add back button
		backButton.render();

		// do -50 of the first image rendered to account for xline height.
		drawRules(g, rules, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
	}

	@Override
	public int getID() {
		return Vals.RULES_STATE;
	}

	/**
	 * Draw a long string, entering a new line every time the \n character is
	 * encountered. Each line will be left-aligned according to the
	 * RULES_SECT_RIGHT_W variable.
	 * 
	 * @param g
	 *            The graphics
	 * @param s
	 *            The string to be displayed
	 * @param y
	 *            The y coordinate of the string.
	 */
	public void drawRules(Graphics g, String s, int y) {
		for (String line : s.split("\n"))
			// acts as error handling
			if (Vals.FONT_RULES.getWidth(line) > Vals.RULES_SECT_RIGHT_W) {
				g.drawString("CONSIDER SHORTENING THIS LINE: " + line.substring(0, 20),
						Vals.SCREEN_WIDTH - Vals.RULES_SECT_RIGHT_W, y += Vals.FONT_RULES.getLineHeight() * 2);
			} else {
				g.drawString(line, Vals.SCREEN_WIDTH - Vals.RULES_SECT_RIGHT_W,
						y += Vals.FONT_RULES.getLineHeight() * 1.7);
			}
	}

}
