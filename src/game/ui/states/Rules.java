package game.ui.states;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

/**
 * The rules page accessible from the main menu. Provides the user with a guide
 * on how to play.
 * 
 * @author iichr
 *
 */
public class Rules extends BasicGameState {
	private MenuButton backButton;
	private String mouseCoords;
	private String gameTitle;
	private String rules;

	// testing with some arbitrary sprites
	private final String chairLoc = "/res/sprites/tiles/chair.png";
	private final String deskLoc = "/res/sprites/tiles/desk.png";
	private Image chair, desk;

	public Rules(int state) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

		// Fonts - temp
		// only load those actually needed.
		ArrayList<UnicodeFont> fontList = new ArrayList<UnicodeFont>();
		fontList.add(Vals.FONT_MAIN);
		fontList.add(Vals.FONT_HEADING1);

		for (UnicodeFont font : fontList) {
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect());
			font.loadGlyphs();
		}

		// Object images
		chair = new Image(chairLoc).getScaledCopy(50, 50);
		desk = new Image(deskLoc).getScaledCopy(50, 50);

		gameTitle = "DevWars";
		rules = " Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n "
				+ "Proin mattis odio leo, quis fermentum augue eleifend non.\n "
				+ "Ut vel maximus dui. Vivamus pellentesque fringilla dolor, et volutpat leo varius quis.\n "
				+ "Aliquam molestie elit vitae arcu interdum ultrices. Maecenas sagittis vel tellus fermentum fringilla.\n "
				+ "Nam a aliquet neque. Donec vehicula est diam, et cursus tellus maximus et. Curabitur porttitor iaculis velit\n"
				+ " non vulputate. Donec pharetra arcu quis tortor congue finibus. Maecenas dignissim convallis faucibus. Quisque dapibus quam lectus, a consequat tortor\n"
				+ "fermentum eget. Suspendisse potenti. Nam metus eros, sodales eu tellus a, lobortis rutrum tortor. Ut malesuada sem ligula, at vehicula\n "
				+ "augue ultricies id. Suspendisse sed neque dui.\n"
				+ "Ut tempus dapibus imperdiet. Phasellus sit amet pulvinar elit,\n"
				+ "in consequat ipsum. Suspendisse sed tincidunt erat. In posuere ligula sit amet enim cursus,\n"
				+ "nec pretium quam dictum. Ut semper vulputate quam, non auctor lacus egestas in. Praesent sed odio pellentesque risus fermentum.\n";
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);

		// title
		g.setFont(Vals.FONT_HEADING1);
		g.drawString(gameTitle, (Vals.SCREEN_WIDTH - Vals.FONT_MAIN.getWidth(gameTitle)) / 2, 30);

		g.setFont(Vals.FONT_MAIN);

		g.drawImage(chair, Vals.RULES_SECT_LEFT_W / 2 - chair.getWidth() / 2, 150);
		g.drawImage(desk, Vals.RULES_SECT_LEFT_W / 2 - desk.getWidth() / 2, 300);

		// add back button
		backButton.render(g);

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
	 * @param x
	 *            The x coordinate of the string
	 * @param y
	 *            The y coordinate of the string.
	 */
	public void drawRules(Graphics g, String s, int y) {
		for (String line : s.split("\n"))
			if (Vals.FONT_MAIN.getWidth(line) > Vals.RULES_SECT_RIGHT_W) {
				g.drawString("ERROR: THIS LINE NEEDS SHORTENING", Vals.SCREEN_WIDTH - Vals.RULES_SECT_RIGHT_W,
						y += Vals.FONT_MAIN.getLineHeight() * 2);
			} else {
				g.drawString(line, Vals.SCREEN_WIDTH - Vals.RULES_SECT_RIGHT_W,
						y += Vals.FONT_MAIN.getLineHeight() * 2);
			}
	}

}
