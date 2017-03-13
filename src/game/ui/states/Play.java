package game.ui.states;

import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.core.chat.Chat;
import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.event.minigame.MiniGameStartedEvent;
import game.core.event.player.PlayerInputEvent;

import game.core.input.InputTypeCharacter;
import game.core.input.InputTypeInteraction;
import game.core.input.InputTypeMovement;
import game.core.input.InteractionType;
import game.core.input.MovementType;
import game.core.minigame.MiniGame;
import game.core.minigame.MiniGameHangman;
import game.core.minigame.MiniGamePong;
import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.player.action.PlayerActionSleep;
import game.core.util.Coordinates;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import game.core.world.tile.type.TileTypeComputer;
import game.core.world.tile.type.TileTypeDoor;
import game.ui.PlayerInfo;
import game.ui.PlayerOverview;
import game.ui.components.ChatBox;
import game.ui.components.Effect;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;
import game.ui.overlay.GameOverOverlay;
import game.ui.overlay.HangmanOverlay;
import game.ui.overlay.OptionsOverlay;
import game.ui.overlay.PongOverlay;
import game.ui.player.ActionSelector;
import game.ui.player.PlayerAnimation;

public class Play extends BasicGameState {
	// private String mouseCoords = "No input yet!";

	// world information
	protected World world;
	private HashMap<Player, PlayerAnimation> playerMap;
	protected String localPlayerName;

	// tile information
	private float tileWidth;
	private float tileHeight;
	private HashMap<TileType, HashMap<Direction, Image[]>> tileMap;

	// status container
	private PlayerOverview playerOverview;

	// actionSelector
	private ActionSelector actionSelector;

	// effect container
	protected Effect effectOverview;

	// player info
	private PlayerInfo playerinfo;

	// overlays
	private OptionsOverlay optionsOverlay;
	private GameOverOverlay gameOverOverlay;
	private HangmanOverlay hangmanOverlay;
	private PongOverlay pongOverlay;

	boolean showOverview = false;

	// boolean flags
	protected boolean options;
	protected boolean gameOver;
	protected boolean exit;
	private boolean choosingHack;
	private boolean playingPong;
	private boolean playingHangman;

	Music bgmusic;
	private ChatBox cb;

	public Play(int state) {
	}

	@Override
	public int getID() {
		return Vals.PLAY_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		playerMap = new HashMap<Player, PlayerAnimation>();

		// Font
		Vals.FONT_PLAY.addAsciiGlyphs();
		Vals.FONT_PLAY.getEffects().add(new ColorEffect());
		Vals.FONT_PLAY.loadGlyphs();

		actionSelector = new ActionSelector();

		Events.on(GameFinishedEvent.class, this::gameFinished);
		Events.on(MiniGameStartedEvent.class, this::startMinigame);
		Events.on(MiniGameEndedEvent.class, this::closeMinigame);

		// KEEP COMMENTED until we've all added the required libraries.
		// Initialise the background music
		// bgmusic = new Music("res/music/toocheerful.ogg");
		cb = new ChatBox(gc, new Chat());
	}

	/**
	 * Sets up the play state which should be called at the start of each game
	 *
	 */
	public void playSetup() {
		this.world = World.world;
		this.localPlayerName = Player.localPlayerName;

		// set boolean flags
		options = false;
		gameOver = false;
		exit = false;
		choosingHack = false;
		playingPong = false;
		playingHangman = false;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		// UNCOMMENT until everybody add the required libraries.
		// start the background music in a loop
		// bgmusic.loop();

		// setup tile sizes
		tileWidth = (float) Vals.SCREEN_WIDTH / world.xSize;
		tileHeight = 2 * ((float) Vals.SCREEN_HEIGHT / (world.ySize + 2));

		// add player animations
		animatePlayers(world.getPlayers());

		// get the tileMap
		SpriteLocations sp = new SpriteLocations();
		tileMap = sp.getTileMap();

		// set up player info
		playerinfo = new PlayerInfo(world, localPlayerName, tileWidth, tileHeight);

		// Effect container
		effectOverview = new Effect(tileWidth, tileHeight);

		// player overview
		playerOverview = new PlayerOverview(localPlayerName, 0, 0);

		// popUps
		optionsOverlay = new OptionsOverlay();
		gameOverOverlay = new GameOverOverlay(world.getPlayers());
		hangmanOverlay = new HangmanOverlay();
		pongOverlay = new PongOverlay();
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
	private void animatePlayers(List<Player> players) throws SlickException {
		for (Player p : players) {
			PlayerAnimation animation = new PlayerAnimation(p.getHair(), p.getFacing());
			playerMap.put(p, animation);
		}
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// KEEP COMMENTED until everybody has added the required libraries.
		// used to stop the music from playing
		// bgmusic.stop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(Vals.FONT_PLAY);
		boolean[][] visible = findVisibles();
		
		// renders world
		drawWorld(visible);
		cb.render(gc, g);
		
		// add effects overview container
		effectOverview.render();

		// shows selectors
		if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
			if (world.getPlayer(localPlayerName).status.getActions().size() == 0) {
				actionSelector.updateSelector(world, localPlayerName, choosingHack, tileWidth, tileHeight, g);
			}
		}

		// show ui info to player
		playerinfo.render(g, visible);
		if (gameOver) {
			gameOverOverlay.render(g);
		} else if (options) {
			optionsOverlay.render(g);
		} else if (playingHangman) {
			hangmanOverlay.render(g);
		} else if (playingPong) {
			pongOverlay.render(g);
		} else if (showOverview) {
			playerOverview.render(g);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		Player localPlayer = world.getPlayer(localPlayerName);

		cb.update(gc, localPlayerName);

		effectOverview.updateEffects(localPlayer);

		playerOverview.updateContainer(world.getPlayers());

		if (exit) {
			game.enterState(Vals.MENU_STATE);
		}

		input.clearKeyPressedRecord();
	}

	private void drawWorld(boolean [][] visible) throws SlickException {
		// draw the wall sprite
		Image wall = new Image(SpriteLocations.TILE_WALL, false, Image.FILTER_NEAREST);
		wall.draw(0, 0, Vals.SCREEN_WIDTH, tileHeight);
		
		// check every position in the world to render what is needed at that
		// location
		for (int y = 0; y < world.ySize; y++) {
			for (int x = 0; x < world.xSize; x++) {
				float tileX = x * tileWidth;
				float tileY = (y + 1) * (tileHeight / 2);

				// find out what tile is in this location
				Tile found = world.getTile(x, y, 0);

				// check to draw computer marker
				drawComputerMarker(tileX, tileY, found);

				// draw the tile at this location
				drawTile(tileX, tileY, found, visible[x][y]);

				// draw any players at this location
				if (visible[x][y]) {
					drawPlayers(x, y, tileX, tileY);
				}
			}
		}
	}

	private void drawComputerMarker(float tileX, float tileY, Tile found) throws SlickException {
		TileType type = found.type;
		if (type.equals(TileType.COMPUTER) && showOverview) {
			String ownerName = TileTypeComputer.getOwningPlayer((MetaTile) found);
			if (ownerName.equals(localPlayerName)) {
				Image identifier = new Image(ImageLocations.PLAYER_IDENTIFIER, false, Image.FILTER_NEAREST);
				identifier.draw(tileX + tileWidth / 6, tileY + tileHeight / 8, 2 * tileWidth / 3, tileHeight / 8);
			}
		}
	}

	private void drawTile(float tileX, float tileY, Tile tile, boolean visible) {
		Direction facing = tile.facing;
		TileType type = tile.type;

		// draw the tile
		int mtID = tile.multitileID;
		if (mtID == -1) {
			mtID++;
		}

		HashMap<Direction, Image[]> directionMap = tileMap.get(type);
		Image[] images = directionMap.get(facing);

		if (visible) {
			if (type.equals(TileType.WALL) || type.equals(TileType.WALL_CORNER) || type.equals(TileType.DOOR) ) {
				images[mtID].draw(tileX, tileY - tileHeight / 2, tileWidth, 3 * tileHeight / 2);
			} else {
				images[mtID].draw(tileX, tileY, tileWidth, tileHeight);
			}
		} else {
			if (type.equals(TileType.WALL) || type.equals(TileType.WALL_CORNER) || type.equals(TileType.DOOR)) {
				images[mtID].draw(tileX, tileY - tileHeight / 2, tileWidth, 3 * tileHeight / 2, Color.darkGray);
			} else {
				images[mtID].draw(tileX, tileY, tileWidth, tileHeight, Color.darkGray);
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
	private void drawPlayers(int x, int y, float tileX, float tileY) {
		// get players
		List<Player> players = world.getPlayers();

		// render the players
		for (Player player : players) {
			Location playerLocation = player.getLocation();
			if (playerLocation.coords.x == x && playerLocation.coords.y == y) {
				changeAnimation(player);
				if (player.status.hasAction(PlayerActionSleep.class)) {
					// draw sleeping players sideways
					// check if next tile is in bounds
					Location right = new Location(new Coordinates(x - 1, y, 0), world);
					if (right.checkBounds()) {
						playerMap.get(player).drawPlayer((x - 1) * tileWidth, (y + 2) * (tileHeight / 2), tileWidth * 2,
								tileHeight / 2);
					} else {
						// otherwise draw to the left
						playerMap.get(player).drawPlayer(tileX, (y + 2) * (tileHeight / 2), tileWidth * 2,
								tileHeight / 2);
					}
				} else {
					playerMap.get(player).drawPlayer(tileX, tileY, tileWidth, tileHeight);
					Tile tile = playerLocation.getTile();
					if (tile.type.equals(TileType.CHAIR) && tile.facing.equals(Direction.NORTH)) {
						tileMap.get(tile.type).get(tile.facing)[0].draw(tileX, tileY, tileWidth, tileHeight);
					}
				}
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
	private void changeAnimation(Player player) {
		Direction playerFacing = player.getFacing();
		if (player.status.hasAction(PlayerActionSleep.class)) {
			playerMap.get(player).sleeping(playerFacing);
		} else if (player.status.hasState(PlayerState.sitting)) {
			playerMap.get(player).seated(playerFacing);
		} else {
			playerMap.get(player).turn(player.getFacing());
		}
	}

	private boolean[][] findVisibles() {
		boolean[][] visible = new boolean[world.xSize][world.ySize];

		// set values to false initially
		for (int x = 0; x < world.xSize; x++) {
			for (int y = 0; y < world.ySize; y++) {
				visible[x][y] = false;
			}
		}

		// find any which should be true
		getVisibleArray(visible, world.getPlayer(localPlayerName).getLocation());

		return visible;
	}

	/**
	 * Checks if the tile is visible in the current room
	 * 
	 * @param visible
	 *            The visible array
	 * @param current
	 *            The current location being looked at
	 * @return The visible array
	 */
	private boolean[][] getVisibleArray(boolean[][] visible, Location current) {
		int x = current.coords.x;
		int y = current.coords.y;
		visible[x][y] = true;

		// find surrounding locations
		Location north = current.add(-1, 0, 0);
		Location east = current.add(0, 1, 0);
		Location south = current.add(1, 0, 0);
		Location west = current.add(0, -1, 0);
		Location ne = current.add(-1, 1, 0);
		Location nw = current.add(-1, -1, 0);
		Location se = current.add(1, 1, 0);
		Location sw = current.add(1, -1, 0);

		// check if we should continue checking them
		checkContinue(visible, north);
		checkContinue(visible, east);
		checkContinue(visible, south);
		checkContinue(visible, west);
		checkContinue(visible, ne);
		checkContinue(visible, nw);
		checkContinue(visible, se);
		checkContinue(visible, sw);

		return visible;
	}

	/**
	 * @param visible
	 *            The visible array
	 * @param toCheck
	 *            The location being checked
	 * @return The visible array
	 */
	private boolean[][] checkContinue(boolean[][] visible, Location toCheck) {
		int x = toCheck.coords.x;
		int y = toCheck.coords.y;
		if (x >= 0 && x < visible.length && y >= 0 && y < visible[0].length) {
			if (visible[x][y] != true) {
				if (toCheck.checkBounds()) {
					TileType found = toCheck.getTile().type;
					if (!found.equals(TileType.WALL) && !found.equals(TileType.WALL_CORNER) && !found.equals(TileType.DOOR)) {
						visible = getVisibleArray(visible, toCheck);
					} else {
						// we can see the wall, but not past it
						visible[x][y] = true;
					}
				}
			}
		}
		return visible;
	}

	private void gameFinished(GameFinishedEvent e) {
		gameOver = true;
	}

	private void startMinigame(MiniGameStartedEvent e) {
		if(e.game.equals(MiniGameHangman.class)){
			playingHangman = true;
		}else if(e.game.equals(MiniGamePong.class)){
			playingPong = true;
		}
	}

	private void closeMinigame(MiniGameEndedEvent e) {
			playingHangman = false;
			playingPong = false;
	}

	@Override
	public void keyPressed(int key, char c) {
		if (!gameOver) {
			if (playingPong) {
				pongControls(key);
			} else if (playingHangman) {
				hangmanControls(key, c);
			} else {
				coreControls(key);
			}
		} else {
			exit = true;
		}
	}

	/**
	 * Hangman - manage input and display
	 * 
	 * @param key
	 * @param c
	 *            The input char
	 */
	private void hangmanControls(int key, char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			// System.out.println("Entered char = " + c);
			// also serves to update the display!
			Events.trigger(new PlayerInputEvent(new InputTypeCharacter(c), localPlayerName));
		}
	}

	/**
	 * Use controls for the pong minigame
	 * 
	 * @param c
	 *            The character entered
	 */
	private void pongControls(int key) {
		switch (key) {
		case Input.KEY_W:
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_UP), localPlayerName));
			break;
		case Input.KEY_S:
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_DOWN), localPlayerName));
		}
	}

	/**
	 * Use controls for normal game
	 * 
	 * @param key
	 *            The key pressed
	 */
	private void coreControls(int key) {
		switch (key) {
		case Input.KEY_ESCAPE:
			options = !options;
			break;
		case Input.KEY_TAB:
			showOverview = true;
			break;
		case Input.KEY_W:
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_UP), localPlayerName));
			choosingHack = false;
			actionSelector.setAction(0);
			break;
		case Input.KEY_S:
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_DOWN), localPlayerName));
			choosingHack = false;
			actionSelector.setAction(0);
			break;
		case Input.KEY_D:
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_RIGHT), localPlayerName));
			choosingHack = false;
			actionSelector.setAction(0);
			break;
		case Input.KEY_A:
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_LEFT), localPlayerName));
			choosingHack = false;
			actionSelector.setAction(0);
			break;
		case Input.KEY_E:
			if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
				manageSelector();
			} else {
				Events.trigger(new PlayerInputEvent(new InputTypeInteraction(InteractionType.OTHER), localPlayerName));
			}
			break;
		case Input.KEY_UP:
			if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
				actionSelector.changeSelection(1);
			}
			break;
		case Input.KEY_DOWN:
			if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
				actionSelector.changeSelection(-1);
			}
			break;
		case Input.KEY_LEFT:
			if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
				choosingHack = false;
			}
			break;
		// TEMPORARY FOR TESTING HANGMAN - PRESS 9
		case Input.KEY_9:
			MiniGame.localMiniGame = new MiniGameHangman("chris");
			playingHangman = true;
			// System.out.println("ENTERED HANGMAN");
			break;
			// TEMPORARY FOR TESTING PONG - PRESS 8
		case Input.KEY_8:
			MiniGame.localMiniGame = new MiniGamePong("tim", localPlayerName);
			playingPong = true;
			break;
		case Input.KEY_LALT:
			cb.toggleFocus();
		}
	}

	private void manageSelector() {
		switch (actionSelector.getSelected()) {
		case "WORK":
			Events.trigger(new PlayerInputEvent(new InputTypeInteraction(InteractionType.WORK), localPlayerName));
			break;
		case "HACK":
			choosingHack = true;
			actionSelector.setAction(0);
			break;
		case "NONE":
			// do nothing
			break;
		default:
			Events.trigger(new PlayerInputEvent(new InputTypeInteraction(InteractionType.HACK), localPlayerName));
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
