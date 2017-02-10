package game.ui.states;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
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
import game.ui.EffectContainer;
import game.ui.PlayerContainer;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;
import game.ui.player.PlayerAnimation;

public class Play extends BasicGameState {
	private String mouseCoords = "No input yet!";

	// world information
	private World world;
	private HashMap<Player, PlayerAnimation> playerMap;

	// tile information
	private float tileWidth;
	private float tileHeight;
	private HashMap<TileType, HashMap<Direction, Image[]>> tileMap;

	// status container
	private PlayerContainer playerOverview;

	// effect container
	private EffectContainer effectOverview;
	private Image _avatar, coffee;
	boolean showOverview = false;

	// TODO WIP 10/02
	private float objX = 0;
	private float objY = 0;
	private boolean computer = false;
	private boolean coffeemach = false;
	private boolean sofa = false;

	private boolean paused = false;

	private boolean moveNorth = false;
	private boolean moveSouth = false;
	private boolean moveEast = false;
	private boolean moveWest = false;

	public Play(int state) {
	}

	@Override
	public int getID() {
		return Vals.PLAY_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		playerMap = new HashMap<Player, PlayerAnimation>();

		// PlayerContainer container
		_avatar = new Image(ImageLocations.TEMP_AVATAR, false, Image.FILTER_NEAREST);
		playerOverview = new PlayerContainer(10, 100, 300, 500, _avatar, _avatar, _avatar, _avatar);

		testSetup();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// Effectcontainer
		coffee = new Image("res/sprites/tiles/coffee.png", false, Image.FILTER_NEAREST);
		effectOverview = new EffectContainer(coffee, 10);
		// setup tile sizes
		tileWidth = (float) Vals.SCREEN_WIDTH / world.xSize;
		tileHeight = 2 * (float) Vals.SCREEN_HEIGHT / (world.ySize + 2);

		// add player animations
		animatePlayers(world.getPlayers());

		// get the tileMap
		SpriteLocations sp = new SpriteLocations();
		tileMap = sp.getTileMap();
	}

	/**
	 * Sets up the play state which should be called at the start of each game
	 * 
	 * @param world
	 *            The game world
	 */
	public void playSetup(World world) {
		this.world = world;
	}

	/**
	 * map players to player animations, testing different sprites
	 * implementation not finalised
	 * 
	 * @param players
	 *            the set of Players in the world
	 * 
	 * @throws SlickException
	 */
	private void animatePlayers(Set<Player> players) throws SlickException {
		for (Player p : players) {
			int colour = 0;
			colour = Integer.parseInt(p.name);

			PlayerAnimation animation = new PlayerAnimation(colour, p.getFacing());
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

		// TODO WIP 10/02
		// interaction with in-game objects on click, display string if
		// successful.
		if (computer)
			Vals.FONT_MAIN.drawString(objX, objY, "WORK/HACK", Color.red);
		if (coffeemach)
			Vals.FONT_MAIN.drawString(objX, objY, "DRINK COFFEE", Color.red);
		if (sofa)
			Vals.FONT_MAIN.drawString(objX, objY, "SLEEP ON SOFA", Color.red);
	}

	public void drawWorld() throws SlickException {
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
				int mtID = world.getTile(x, y, 0).multitileID;
				if (mtID == -1) {
					mtID++;
				}
				HashMap<Direction, Image[]> directionMap = tileMap.get(type);
				Image[] images = directionMap.get(facing);
				images[mtID].draw(tileX, tileY, tileWidth, tileHeight);

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

		Player p1 = null;
		for (Player p : world.getPlayers()) {
			if (p.name.equals("0")) {
				p1 = p;
			}
		}

		if (moveNorth) {
			p1.move(Direction.NORTH);
			playerMap.get(p1).turnNorth();
			moveNorth = false;

			// actually send info to game logic
		} else if (moveSouth) {
			// for testing, move player one
			p1.move(Direction.SOUTH);
			playerMap.get(p1).turnSouth();
			moveSouth = false;

			// actually send info to game logic
		} else if (moveWest) {
			// for testing, move player one
			p1.move(Direction.WEST);
			playerMap.get(p1).turnWest();
			moveWest = false;

			// actually send info to game logic
		} else if (moveEast) {
			// for testing move player one
			p1.move(Direction.EAST);
			playerMap.get(p1).turnEast();
			moveEast = false;

			// actually send info to game logic
		}

		if (input.isKeyPressed(Input.KEY_B)) {
			effectOverview.activate();
		}

		if (paused) {
			game.enterState(Vals.PAUSE_STATE);
			paused = false;
		}

		input.clearKeyPressedRecord();
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			// create event at location in world

			int worldX = (int) (x / tileWidth);
			int worldY = (int) (y / (tileHeight / 2) - 2);
			System.out.println(worldX);
			System.out.println(worldY);
			TileType type = world.getTile(worldX, worldY, 0).type;
			System.out.println(type);
			objX = x;
			objY = y;

			if (type == TileType.COFFEE_MACHINE) {
				System.out.println("hello");
				coffeemach = true;
				computer = false;
				sofa = false;
			} else if (type == TileType.COMPUTER) {
				computer = true;
				sofa = false;
				coffeemach = false;
			} else if (type == TileType.SOFA) {
				sofa = true;
				coffeemach = false;
				computer = false;
			} else {
				// decor without user interaction
				sofa = false;
				coffeemach = false;
				computer = false;
			}
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_ESCAPE:
			paused = true;
			break;
		case Input.KEY_TAB:
			showOverview = true;
			break;
		case Input.KEY_UP:
			moveNorth = true;
			break;
		case Input.KEY_DOWN:
			moveSouth = true;
			break;
		case Input.KEY_RIGHT:
			moveEast = true;
			break;
		case Input.KEY_LEFT:
			moveWest = true;
			break;
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		switch (key) {
		case Input.KEY_TAB:
			showOverview = false;
		}
	}

	/**
	 * Generates a fake world and set of players to be used for testing
	 */
	private void testSetup() {
		// testing methods
		int noPlayers = 6;
		World w = createWorld(noPlayers);
		w = addPlayers(w, noPlayers);

		playSetup(w);
	}

	/**
	 * Testing method used to create a fake world
	 * 
	 * @param noPlayers
	 *            the number of player in the game
	 * @return The world
	 */
	private World createWorld(int noPlayers) {
		World w = null;
		Path p = Paths.get("data/office4Player.level");
		if (noPlayers == 6) {
			p = Paths.get("data/office6Player.level");
		}
		try {
			w = World.load(p, noPlayers);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return w;
	}

	/**
	 * Testing method used to create a fake set of players
	 * 
	 * @param w
	 *            The world
	 * @param noPlayers
	 *            The number of players to be made
	 * @return The world
	 */
	private World addPlayers(World w, int noPlayers) {
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			int x = r.nextInt(w.xSize);
			int y = r.nextInt(w.ySize - 1);
			Location l = new Location(x, y, w);
			Direction d = Direction.SOUTH;
			Player p = new Player("" + i, d, l);
			w.addPlayer(p);
		}
		return w;
	}

}
