package game.ui.states;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.core.world.World;
import game.core.world.tile.TileType;
import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;

public class Play extends BasicGameState {
	private String mouseCoords = "No input yet!";
	private float moveX = 300;
	private float moveY = 150;
	private Animation circle, staying, moving;
	private int[] duration = { 200, 200 };
	boolean pause = false;
	private MenuButton backButton;
	private HashMap<TileType, Image []> imageMap;
	private World world;

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
		
		imageMap = SpriteLocations.createMap();
		createWorld();
	}

	// temporary method until classes integrated
	private void createWorld() {
		TileType.init();
		Path p = Paths.get("data/office.level");
		try {
			world = World.load(p, 4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		drawWorld();
		drawPlayer();

		// debugging
		g.drawString(mouseCoords, 10, 50);

		// example
		circle.draw(moveX, moveY);
		g.drawString("Circle at:(" + moveX + "," + moveY + ")", 350, 50);
		// pausing the game
		if (pause) {
			g.drawString("Resume (R) ", Vals.SCREEN_WIDTH - Vals.SCREEN_HEIGHT / 10, Vals.SCREEN_HEIGHT / 2 - 20);
		}

		// add back button
		backButton.render(g);
	}

	public void drawWorld() throws SlickException {
		//find tile width and height
		int tileWidth = (Vals.SCREEN_WIDTH) / world.xSize;
		int tileHeight = (Vals.SCREEN_HEIGHT) / world.ySize;
		
		//render each tile
		for (int y = 0; y < world.ySize; y++) {
			for (int x = 0; x < world.xSize; x++) {
				TileType type = world.getTile(x, y, 0).type;
				Image[] images = imageMap.get(type);
				if (type.equals(TileType.FLOOR)) {
					images[(x + y) % 2].draw(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
				} else {
					images[0].draw(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
				}
			}
		}
	}

	private void drawPlayer() throws SlickException {
		//TODO make/find player sprites
		//TODO need a way to access the list of players
		//TODO draw players (taller than one tile?) - may want a height variable in tileType?
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		float mouseX = Mouse.getX();
		float mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
		if (input.isKeyDown(Input.KEY_UP)) {
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
		}

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
	}
}
