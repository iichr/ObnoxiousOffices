package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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

	public Rules(int state) {

	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);
		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

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

		// add back button
		backButton.render(g);
		g.drawString(gameTitle, 200, 100);
		drawRules(g, rules, 30, 140);
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
	 * Draw a long string, entering a new line every time the \n character is encountered.
	 * @param g The graphics
	 * @param s The string to be displayed
	 * @param x The x coordinate of the string
	 * @param y The y coordinate of the string.
	 */
	public void drawRules(Graphics g, String s, int x, int y) {
		for (String line : s.split("\n"))
			g.drawString(line, Vals.SCREEN_WIDTH / 2 - 500, y += 60);
	}

}
