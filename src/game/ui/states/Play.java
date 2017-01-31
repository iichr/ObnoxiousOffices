package game.ui.states;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
import game.core.world.tile.Tile;
import game.core.world.tile.TileType;
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
	boolean pause = false;
	private MenuButton backButton;

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
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);

		// example
		circle.draw(moveX, moveY);
		g.drawString("Circle at:(" + moveX + "," + moveY + ")", 350, 50);
		// pausing the game
		if (pause) {
			g.drawString("Resume (R) ", Vals.SCREEN_WIDTH - Vals.SCREEN_HEIGHT / 10, Vals.SCREEN_HEIGHT / 2 - 20);
		}
		
		drawWorld(gc, sbg, g);
		
		// add back button
		backButton.render(g);
	}
	
	public void drawWorld(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Image floor = new Image(SpriteLocations.TILE_FLOOR1, false, Image.FILTER_NEAREST);
		Image desk = new Image(SpriteLocations.TILE_DESK, false, Image.FILTER_NEAREST);
		Image chair = new Image(SpriteLocations.TILE_CHAIR, false, Image.FILTER_NEAREST);
		Image pc = new Image(SpriteLocations.TILE_PC, false, Image.FILTER_NEAREST);
		Path p = Paths.get("data/office.level");
		try {
			TileType.init();
			World world = World.load(p, 4);
			System.out.println(floor.getWidth());
			int tileWidth = (Vals.SCREEN_WIDTH)/world.xSize;
			int tileHeight = (Vals.SCREEN_HEIGHT)/world.ySize;
			for(int y = 0; y < world.ySize; y++ ){
				for(int x = 0; x < world.xSize; x++){
					System.out.println(x);
					TileType type = world.getTile(x, y, 0).type;
					if(type.equals(TileType.getType('f'))){
						floor.draw(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
					}else if(type.equals(TileType.getType('d'))){
						desk.draw(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
					}else if(type.equals(TileType.getType('s'))){
						chair.draw(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
					}else if(type.equals(TileType.getType('c'))){
						pc.draw(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
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
