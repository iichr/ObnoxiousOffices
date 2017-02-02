package game.ui.states;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.TileType;
import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;
import game.ui.player.PlayerAnimation;

public class Play extends BasicGameState {
	private String mouseCoords = "No input yet!";
	private int[] duration = { 200, 200 };
	boolean pause = false;
	private MenuButton backButton;
	private HashMap<TileType, Image[]> imageMap;
	private HashMap<Player, PlayerAnimation> playerMap;
	private World world;

	public Play(int state) {
	}

	@Override
	public int getID() {
		return Vals.PLAY_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		playerMap = new HashMap<Player, PlayerAnimation>();

		Image back = new Image(ImageLocations.BACK);
		Image backR = new Image(ImageLocations.BACK_ROLLOVER);

		backButton = new MenuButton(10.0f, 10.0f, 40, 40, back, backR);

		imageMap = SpriteLocations.createMap();
		createWorld();
		addPlayers();
		animatePlayers(world.getPlayers());
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

	// temporary method until classes integrated
	private void addPlayers() {
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			int x = r.nextInt(world.xSize);
			int y = r.nextInt(world.ySize -1);
			Location l = new Location(x, y, world);
			Direction d = Direction.NORTH;
			Player p = new Player("" + i, d, l);
			world.addPlayer(p);
		}
	}
	
	//map players to player animations
	private void animatePlayers(Set<Player> players) throws SlickException{
		for(Player p: players){
			Image n = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_NORTH, false, Image.FILTER_NEAREST);
			Image s = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			Image e = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_EAST, false, Image.FILTER_NEAREST);
			Image w = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_WEST, false, Image.FILTER_NEAREST);
			
			PlayerAnimation animation = new PlayerAnimation(n, s, e, w, p.getDirection());
			playerMap.put(p, animation);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		drawWorld();
		drawPlayers();

		// debugging
		g.drawString(mouseCoords, 10, 50);

		// pausing the game
		if (pause) {
			g.drawString("Resume (R) ", Vals.SCREEN_WIDTH - Vals.SCREEN_HEIGHT / 10, Vals.SCREEN_HEIGHT / 2 - 20);
		}

		// add back button
		backButton.render();
	}

	public void drawWorld() throws SlickException {
		// find tile width and height
		int tileWidth = Vals.SCREEN_WIDTH / world.xSize;
		int tileHeight = Vals.SCREEN_HEIGHT / world.ySize;

		// render each tile
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

	private void drawPlayers() throws SlickException {
		int tileHeight = Vals.SCREEN_HEIGHT / world.ySize;
		int tileWidth = Vals.SCREEN_WIDTH / world.xSize;
		int playerHeight = 2 * tileHeight;
		int playerWidth = tileWidth;

		Set<Player> players = world.getPlayers();
		for (Player player : players) {
			int playerX = player.location.x * tileWidth;
			int playerY = player.location.y * tileHeight;
			playerMap.get(player).drawPlayer(playerX, playerY, playerWidth, playerHeight);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		float mouseX = Mouse.getX();
		float mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
		
		Player p1 = null;
		for(Player p: world.getPlayers()){
			if (p.name.equals("0")){
				p1 = p;
			}
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			//for testing, move player one
			p1.move(Direction.NORTH);
			playerMap.get(p1).turnNorth();
			
			//actually send info to game logic
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			//for testing, move player one
			p1.move(Direction.SOUTH);
			playerMap.get(p1).turnSouth();
			
			//actually send info to game logic
		} else if (input.isKeyPressed(Input.KEY_LEFT)) {
			//for testing, move player one
			p1.move(Direction.WEST);
			playerMap.get(p1).turnWest();
			
			//actually send info to game logic
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			//for testing move player one
			p1.move(Direction.EAST);
			playerMap.get(p1).turnEast();
			
			//actually send info to game logic
		} else {
			//do nothing
		}

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);
	}
}
