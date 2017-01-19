import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState {
	private String mouseCoords = "No input yet!";
	float moveX = 300;
	float moveY = 150;
	Animation circle, staying, moving;
	int[] duration = { 200, 200 };
	boolean quit = false;
	private MenuButton backButton;

	public Play(int state) {

	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {

	}

	@Override
	public int getID() {
		return Vals.PLAY_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Image[] stay = { new Image("./res/circle.png"), new Image("./res/circle.png") };
		Image[] move = { new Image("./res/circle2.png"), new Image("./res/circle2.png") };

		staying = new Animation(stay, duration, false);
		moving = new Animation(move, duration, false);
		circle = staying;

		Image back = new Image("./res/back.png");
		Image backR = new Image("./res/backR.png");

		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);

		// example
		circle.draw(moveX, moveY);
		g.drawString("Circle at:(" + moveX + "," + moveY + ")", 350, 50);

		// add back button
		backButton.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		if (input.isKeyDown(Input.KEY_UP)) {
			circle = moving;
			moveY -= 0.5;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			circle = moving;
			moveY += 0.5;
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			circle = moving;
			moveX -= 0.5;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			circle = moving;
			moveX += 0.5;
		} else if (input.isKeyDown(Input.KEY_ESCAPE)) {
			quit = true;
		} else {
			circle = staying;
		}

		backButton.onClick(gc, game, mouseX, mouseY, Vals.MENU_STATE);
	}

}
