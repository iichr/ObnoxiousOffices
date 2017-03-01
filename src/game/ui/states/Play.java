package game.ui.states;

import java.util.HashMap;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.core.Input.InputType;
import game.core.event.Events;
import game.core.event.player.PlayerInputEvent;
import game.core.player.Player;
import game.core.player.action.PlayerAction;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.type.TileType;
import game.ui.EffectContainer;
import game.ui.PlayerContainer;
import game.ui.PlayerInfo;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;
import game.ui.player.ActionSelector;
import game.ui.player.PlayerAnimation;

public class Play extends BasicGameState {
	// private String mouseCoords = "No input yet!";

	// world information
	protected World world;
	private HashMap<Player, PlayerAnimation> playerMap;
	protected String localPlayerName;

	private HashMap<Player, Player> previousPlayer;

	// tile information
	private float tileWidth;
	private float tileHeight;
	private HashMap<TileType, HashMap<Direction, Image[]>> tileMap;

	// status container
	private PlayerContainer playerOverview;

	// actionSelector
	private ActionSelector actionSelector;

	// effect container
	protected EffectContainer effectOverview;

	// player info
	private PlayerInfo playerinfo;

	private Image coffee;
	boolean showOverview = false;

	// options toggles
	protected boolean paused = false;

	Music bgmusic;

	public Play(int state) {
	}

	@Override
	public int getID() {
		return Vals.PLAY_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		playerMap = new HashMap<Player, PlayerAnimation>();
		previousPlayer = new HashMap<Player, Player>();

		// Font
		Vals.FONT_PLAY.addAsciiGlyphs();
		Vals.FONT_PLAY.getEffects().add(new ColorEffect());
		Vals.FONT_PLAY.loadGlyphs();

		// Font
		Vals.FONT_PLAY.addAsciiGlyphs();
		Vals.FONT_PLAY.getEffects().add(new ColorEffect());
		Vals.FONT_PLAY.loadGlyphs();

		actionSelector = new ActionSelector();

		// UNCOMMENT until everybody add the required libraries.
		// Initialise the background music
		// bgmusic = new Music("res/music/toocheerful.ogg");
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// UNCOMMENT until everybody add the required libraries.
		// start the background music in a loop
		// bgmusic.loop();

		// setup tile sizes
		tileWidth = (float) Vals.SCREEN_WIDTH / world.xSize;
		tileHeight = 2 * ((float) Vals.SCREEN_HEIGHT / (world.ySize + 2));

		// Effect container
		coffee = new Image("res/sprites/tiles/coffee.png", false, Image.FILTER_NEAREST);
		effectOverview = new EffectContainer(coffee, 10, Vals.SCREEN_WIDTH - 100,
				Vals.SCREEN_HEIGHT - Vals.SCREEN_HEIGHT / 5 * 4);

		// add player animations
		animatePlayers(world.getPlayers());
		storePreviousLocations(world.getPlayers());

		// get the tileMap
		SpriteLocations sp = new SpriteLocations();
		tileMap = sp.getTileMap();

		// set up player info
		playerinfo = new PlayerInfo(world, localPlayerName, tileWidth, tileHeight);
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// UNCOMMENT until everybody add the required libraries.
		// used to stop the music from playing
		// bgmusic.stop();
	}

	/**
	 * Sets up the play state which should be called at the start of each game
	 * 
	 * @param world
	 *            The game world
	 */
	public void playSetup() {
		this.world = World.world;
		this.localPlayerName = Player.localPlayerName;
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
			PlayerAnimation animation = new PlayerAnimation(p.getHair(), p.getFacing());
			playerMap.put(p, animation);
		}
	}

	private void storePreviousLocations(Set<Player> players) throws SlickException {
		for (Player p : players) {
			Player pOld = new Player(p.name, p.getFacing(), p.getLocation());
			previousPlayer.put(p, pOld);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(Vals.FONT_PLAY);
		playerOverview = new PlayerContainer(world, localPlayerName, 0, 0);

		// renders world
		drawWorld();

		// add player status container if invoked
		if (showOverview) {
			playerOverview.render(g);
		}

		// add effects overview container
		effectOverview.render(g);

		// shows selectors
		// TODO only show when seated
		actionSelector.updateSelector(world, localPlayerName, tileWidth, tileHeight);

		// show ui info to player
		playerinfo.render(g);
	}

	public void drawWorld() throws SlickException {
		Image wall = new Image(SpriteLocations.TILE_WALL, false, Image.FILTER_NEAREST);
		wall.draw(0, 0, Vals.SCREEN_WIDTH, tileHeight);

		// check every position in the world to render what is needed at that
		// location
		for (int y = 0; y < world.ySize; y++) {
			for (int x = 0; x < world.xSize; x++) {
				float tileX = x * tileWidth;
				float tileY = (y + 1) * (tileHeight / 2);

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

				drawPlayers(x, y, tileX, tileY);
			}
		}
	}

	/**
	 * Renders the players in the world
	 * 
	 * @param x
	 *            the x location being checked
	 * @param y
	 *            the y location being checked
	 * @param tileX
	 *            the x location of the tiles on screen
	 * @param tileY
	 *            the y location of the tiles on screen
	 */
	public void drawPlayers(int x, int y, float tileX, float tileY) {
		// get players
		Set<Player> players = world.getPlayers();

		// render the players
		for (Player player : players) {
			Location playerLocation = player.getLocation();
			if (playerLocation.coords.x == x && playerLocation.coords.y == y) {
				checkPreviousLocation(player);
				playerMap.get(player).drawPlayer(tileX, tileY, tileWidth, tileHeight);
			}
		}
	}

	/**
	 * Animates the players turning by checking their previous location and
	 * adjusting appropriately
	 * 
	 * @param player
	 *            the player to check
	 */
	public void checkPreviousLocation(Player player) {
		Location playerLocation = player.getLocation();
		Direction playerFacing = player.getFacing();
		if (previousPlayer.get(player).getFacing() != player.getFacing()) {
			//TODO add seated check
			playerMap.get(player).turn(player.getFacing());
			previousPlayer.get(player).setLocation(playerLocation);
			previousPlayer.get(player).setFacing(playerFacing);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();

		if (paused) {
			game.enterState(Vals.PAUSE_STATE);
			paused = false;
		}

		input.clearKeyPressedRecord();
	}

	@Override
	public void mouseWheelMoved(int newValue) {
		// TODO only when seated
		actionSelector.changeSelection(newValue);
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
			Events.trigger(new PlayerInputEvent(InputType.MOVE_UP, localPlayerName));
			break;
		case Input.KEY_DOWN:
			Events.trigger(new PlayerInputEvent(InputType.MOVE_DOWN, localPlayerName));
			break;
		case Input.KEY_RIGHT:
			Events.trigger(new PlayerInputEvent(InputType.MOVE_RIGHT, localPlayerName));
			break;
		case Input.KEY_LEFT:
			Events.trigger(new PlayerInputEvent(InputType.MOVE_LEFT, localPlayerName));
			break;
		case Input.KEY_E:
			Events.trigger(new PlayerInputEvent(InputType.INTERACT, localPlayerName));
			// TODO add way to send work/hack input events
			// this section will be changing with new inputType system
			break;
		case Input.KEY_B:
			effectOverview.activate();
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
}
