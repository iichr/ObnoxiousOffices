package game.ui.states;


import game.ui.EffectContainer;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


import game.ui.PlayerContainer;

import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.TileType;
import game.ui.StatusContainer;

import game.ui.buttons.MenuButton;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;
import game.ui.player.PlayerAnimation;

import java.time.Instant;

public class Play extends BasicGameState {
	private String mouseCoords = "No input yet!";
	private MenuButton backButton;
	private HashMap<TileType, HashMap<Direction, Image[]>> tileMap;
	private HashMap<Player, PlayerAnimation> playerMap;
	private World world;

	// status container
	private PlayerContainer playerOverview;

	// effect container
	private EffectContainer effectOverview;
	private Image _avatar, coffee;
	boolean showOverview = false;

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

		// PlayerContainer container
		_avatar = new Image(ImageLocations.TEMP_AVATAR, false, Image.FILTER_NEAREST);
		playerOverview = new PlayerContainer(10, 100, 300, 500, _avatar, _avatar, _avatar, _avatar);

	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// Effectcontainer
		coffee = new Image("res/sprites/tiles/coffee.png", false, Image.FILTER_NEAREST);
		effectOverview.setCurrent(Instant.now());
		effectOverview = new EffectContainer(coffee, 10);

		SpriteLocations sp = new SpriteLocations();
		tileMap = sp.getTileMap();

		// testing methods
		createWorld();
		addPlayers();
		animatePlayers(world.getPlayers());
	}

	// temporary method until classes integrated
	private void createWorld() {
		Path p = Paths.get("data/office2.level");
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
			int y = r.nextInt(world.ySize - 1);
			Location l = new Location(x, y, world);
			Direction d = Direction.SOUTH;
			Player p = new Player("" + i, d, l);
			world.addPlayer(p);
		}
	}

	// map players to player animations, testing different sprites, not final
	private void animatePlayers(Set<Player> players) throws SlickException {
		for (Player p : players) {
			Image n = null;
			Image s = null;
			Image e = null;
			Image w = null;

			switch (p.name) {
			case "0":
				n = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_NORTH, false, Image.FILTER_NEAREST);
				s = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_SOUTH, false, Image.FILTER_NEAREST);
				e = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_EAST, false, Image.FILTER_NEAREST);
				w = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_WEST, false, Image.FILTER_NEAREST);
				break;
			case "1":
				n = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
				s = new Image(SpriteLocations.PLAYER_DARK_STANDING_SOUTH, false, Image.FILTER_NEAREST);
				e = new Image(SpriteLocations.PLAYER_DARK_STANDING_EAST, false, Image.FILTER_NEAREST);
				w = new Image(SpriteLocations.PLAYER_DARK_STANDING_WEST, false, Image.FILTER_NEAREST);
				break;
			case "2":
				n = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
				s = new Image(SpriteLocations.PLAYER_BROWN_STANDING_SOUTH, false, Image.FILTER_NEAREST);
				e = new Image(SpriteLocations.PLAYER_BROWN_STANDING_EAST, false, Image.FILTER_NEAREST);
				w = new Image(SpriteLocations.PLAYER_BROWN_STANDING_WEST, false, Image.FILTER_NEAREST);
				break;
			case "3":
				n = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
				s = new Image(SpriteLocations.PLAYER_PINK_STANDING_SOUTH, false, Image.FILTER_NEAREST);
				e = new Image(SpriteLocations.PLAYER_PINK_STANDING_EAST, false, Image.FILTER_NEAREST);
				w = new Image(SpriteLocations.PLAYER_PINK_STANDING_WEST, false, Image.FILTER_NEAREST);
				break;
			}

			PlayerAnimation animation = new PlayerAnimation(n, s, e, w, p.getFacing());
			playerMap.put(p, animation);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		drawWorld();
		// drawPlayers();

		// debugging
		g.drawString(mouseCoords, 0, 0);

		// add player status container
		playerOverview.render(g, showOverview);
		
		// add effects overview container
		effectOverview.render(g);

	}

	public void drawWorld() throws SlickException {
		// find tile width and height
		float tileWidth = (float) Vals.SCREEN_WIDTH / world.xSize;
		float tileHeight = 2 * (float) Vals.SCREEN_HEIGHT / (world.ySize + 2);

		Image wall = new Image(SpriteLocations.TILE_WALL, false, Image.FILTER_NEAREST);
		wall.draw(0, 0, Vals.SCREEN_WIDTH, tileHeight);

		// get players
		Set<Player> players = world.getPlayers();

		// check every position in the world to render what is needed at that
		// location
		for (int y = 0; y < world.ySize; y++) {
			for (int x = 0; x < world.xSize; x++) {
				float tileX = x * tileWidth;
				float tileY = (y - 1 + 2) * (tileHeight / 2);

				// find out what to render at this location
				Direction facing = world.getTile(x, y, 0).facing;
				TileType type = world.getTile(x, y, 0).type;
				HashMap<Direction, Image[]> directionMap = tileMap.get(type);
				Image[] images = directionMap.get(facing);
				
				images[0].draw(tileX, tileY, tileWidth, tileHeight);
				
				// render the players
				for (Player player : players) {
					Location playerLocation = player.getLocation();
					if (playerLocation.x == x && playerLocation.y == y) {
						playerMap.get(player).drawPlayer(tileX, tileY, tileWidth, tileHeight);
					}
				}
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		float mouseX = Mouse.getX();
		float mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;

		Player p1 = null;
		for (Player p : world.getPlayers()) {
			if (p.name.equals("0")) {
				p1 = p;
			}
		}

		effectOverview.update();
		// Handle pause and movement
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			input.clearKeyPressedRecord();
			game.enterState(Vals.PAUSE_STATE);

		} else if (input.isKeyDown(Input.KEY_TAB)) {
			showOverview = true;

		} else if (input.isKeyPressed(Input.KEY_UP)) {
			// for testing, move player one
			p1.move(Direction.NORTH);
			playerMap.get(p1).turnNorth();

			// actually send info to game logic
		} else if (input.isKeyPressed(Input.KEY_DOWN)) {
			// for testing, move player one
			p1.move(Direction.SOUTH);
			playerMap.get(p1).turnSouth();

			// actually send info to game logic
		} else if (input.isKeyPressed(Input.KEY_LEFT)) {
			// for testing, move player one
			p1.move(Direction.WEST);
			playerMap.get(p1).turnWest();

			// actually send info to game logic
		} else if (input.isKeyPressed(Input.KEY_RIGHT)) {
			// for testing move player one
			p1.move(Direction.EAST);
			playerMap.get(p1).turnEast();

			// actually send info to game logic
		} else {
			showOverview = false;
		}

		backButton.update(gc, game, mouseX, mouseY, Vals.MENU_STATE);

	}

}
