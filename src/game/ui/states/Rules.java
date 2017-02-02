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
		
		gameTitle = "Read below/Placeholder TITLE";
		rules =" Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n "
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
		backButton.render();
		g.drawString(gameTitle, 200, 50);
		drawRules(g,rules, 30, 100);
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
	
	public void drawRules(Graphics g, String s, int x, int y) {
		for(String line: s.split("\n"))
			g.drawString(line, x, y+=20);
	}

}
