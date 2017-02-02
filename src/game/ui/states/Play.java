package game.ui.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.StatusContainer;
import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;

public class Play extends BasicGameState {
	private String mouseCoords = "No input yet!";
	float moveX = 300;
	float moveY = 150;
	Animation circle, staying, moving;
	int[] duration = { 200, 200 };
	private MenuButton backButton;

	// status container
	private StatusContainer playerOverview;
	private Image _avatar;
	boolean showOverview = false;

	public Play(int state) {
	}

	@Override
	public int getID() {
		return Vals.PLAY_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Image[] stay = { new Image(SpriteLocations.TEST_CIRCLE_GREEN), new Image(SpriteLocations.TEST_CIRCLE_GREEN) };
		Image[] move = { new Image(SpriteLocations.TEST_CIRCLE_PINK), new Image(SpriteLocations.TEST_CIRCLE_PINK) };

		staying = new Animation(stay, duration, false);
		moving = new Animation(move, duration, false);
		circle = staying;

		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);

		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

		// Status container
		_avatar = new Image(ImageLocations.TEMP_AVATAR, false, Image.FILTER_NEAREST);
		playerOverview = new StatusContainer(10, 100, 300, 500, _avatar, _avatar, _avatar, _avatar);

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

		// add player status container
		playerOverview.render(g, showOverview);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		float mouseX = Mouse.getX();
		float mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		// Handle pause and movement
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			input.clearKeyPressedRecord();
			game.enterState(Vals.PAUSE_STATE);
		} else if (input.isKeyDown(Input.KEY_TAB)) {
			showOverview = true;
		} else if (input.isKeyDown(Input.KEY_UP)) {
			circle = moving;
			moveY -= delta * .1f;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			circle = moving;
			moveY += delta * .1f;
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			circle = moving;
			moveX -= delta * .1f;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			circle = moving;
			moveX += delta * .1f;
		} else {
			circle = staying;
			showOverview = false;
		}

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
	}

}
