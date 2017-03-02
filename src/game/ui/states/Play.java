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

import game.core.event.Events;
import game.core.event.GameFinishedEvent;
import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeInteraction;
import game.core.input.InputTypeMovement;
import game.core.input.InteractionType;
import game.core.input.MovementType;
import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.player.action.PlayerActionSleep;
import game.core.player.effect.PlayerEffectCoffeeBuzz;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import game.core.world.tile.type.TileTypeComputer;
import game.ui.PlayerOverview;
import game.ui.PlayerInfo;
import game.ui.components.Effect;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;
import game.ui.overlay.GameOverOverlay;
import game.ui.overlay.OptionsOverlay;
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

	boolean showOverview = false;

	// options toggles
	private boolean options = false;
	private boolean gameOver = false;
	private boolean exit = false;

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

		// Font
		Vals.FONT_PLAY.addAsciiGlyphs();
		Vals.FONT_PLAY.getEffects().add(new ColorEffect());
		Vals.FONT_PLAY.loadGlyphs();

		actionSelector = new ActionSelector();

		Events.on(GameFinishedEvent.class, this::gameFinished);

		// UNCOMMENT until everybody add the required libraries.
		// Initialise the background music
		// bgmusic = new Music("res/music/toocheerful.ogg");
	}
	
	/**
	 * Sets up the play state which should be called at the start of each game
	 *
	 */
	public void playSetup() {
		this.world = World.world;
		this.localPlayerName = Player.localPlayerName;
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
		
		//player overview
		playerOverview = new PlayerOverview(localPlayerName, 0, 0);

		// popUps
		optionsOverlay = new OptionsOverlay();
		gameOverOverlay = new GameOverOverlay(world.getPlayers());
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
		// UNCOMMENT until everybody add the required libraries.
		// used to stop the music from playing
		// bgmusic.stop();
	}	
	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		Player localPlayer = world.getPlayer(localPlayerName);

		effectOverview.updateEffects(localPlayer);
		
		playerOverview.updateContainer(world.getPlayers());
		if(localPlayer.status.hasAction(PlayerActionSleep.class)){
			playerOverview.toggleSleep(localPlayer, true);
		}
		
		if (exit) {
			game.enterState(Vals.MENU_STATE);
		}

		input.clearKeyPressedRecord();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(Vals.FONT_PLAY);

		// renders world
		drawWorld();

		// add player status container if invoked
		if (showOverview) {
			playerOverview.render(g);
		}

		// add effects overview container
		effectOverview.render(g);

		// shows selectors
		if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
			actionSelector.updateSelector(world, localPlayerName, tileWidth, tileHeight);
		}

		// show ui info to player
		playerinfo.render(g);

		if (options) {
			optionsOverlay.render(g);
		}

		if (gameOver) {
			gameOverOverlay.render(g);
		}
	}

	private void drawWorld() throws SlickException {
		//draw the wall sprite
		Image wall = new Image(SpriteLocations.TILE_WALL, false, Image.FILTER_NEAREST);
		wall.draw(0, 0, Vals.SCREEN_WIDTH, tileHeight);

		// check every position in the world to render what is needed at that
		// location
		for (int y = 0; y < world.ySize; y++) {
			for (int x = 0; x < world.xSize; x++) {
				float tileX = x * tileWidth;
				float tileY = (y + 1) * (tileHeight / 2);

				// find out what tile is in this location
				Tile found = world.getTile(x,y,0);
				
				//check to draw computer marker
				drawComputerMarker(tileX, tileY, found);
				
				//draw the tile at this location
				drawTile(tileX, tileY, found);

				//draw any players at this location
				drawPlayers(x, y, tileX, tileY);
			}
		}
	}
	
	private void drawComputerMarker(float tileX, float tileY, Tile found) throws SlickException{
		TileType type = found.type;
		if(type.equals(TileType.COMPUTER) && showOverview){
			String ownerName = TileTypeComputer.getOwningPlayer((MetaTile) found);
			if (ownerName.equals(localPlayerName)) {
				Image identifier = new Image(ImageLocations.PLAYER_IDENTIFIER, false, Image.FILTER_NEAREST);
				identifier.draw(tileX + tileWidth / 6, tileY + tileHeight/8, 2*tileWidth/3, tileHeight/8);
			}
		}
	}
	
	private void drawTile(float tileX, float tileY, Tile tile){
		Direction facing = tile.facing;
		TileType type = tile.type;;
		
		//draw the tile
		int mtID = tile.multitileID;
		if (mtID == -1) {
			mtID++;
		}
		HashMap<Direction, Image[]> directionMap = tileMap.get(type);
		Image[] images = directionMap.get(facing);
		images[mtID].draw(tileX, tileY, tileWidth, tileHeight);
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
				playerMap.get(player).drawPlayer(tileX, tileY, tileWidth, tileHeight);
				Tile tile = playerLocation.getTile();
				if (tile.type.equals(TileType.CHAIR) && tile.facing.equals(Direction.NORTH)) {
					tileMap.get(tile.type).get(tile.facing)[0].draw(tileX, tileY, tileWidth, tileHeight);
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
		if (player.status.hasState(PlayerState.sitting)) {
			playerMap.get(player).seated(playerFacing);
		} else {
			playerMap.get(player).turn(player.getFacing());
		}
	}

	private void gameFinished(GameFinishedEvent e) {
		gameOver = true;
	}

	@Override
	public void mouseWheelMoved(int newValue) {
		if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
			actionSelector.changeSelection(newValue);
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if (!gameOver) {
			switch (key) {
			case Input.KEY_ESCAPE:
				options = !options;
				break;
			case Input.KEY_TAB:
				showOverview = true;
				break;
			case Input.KEY_W:
				Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_UP), localPlayerName));
				break;
			case Input.KEY_S:
				Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_DOWN), localPlayerName));
				break;
			case Input.KEY_D:
				Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_RIGHT), localPlayerName));
				break;
			case Input.KEY_A:
				Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_LEFT), localPlayerName));
				break;
			case Input.KEY_E:
				if (world.getPlayer(localPlayerName).status.hasState(PlayerState.sitting)) {
					switch (actionSelector.getAction()) {
					case ActionSelector.WORK:
						Events.trigger(
								new PlayerInputEvent(new InputTypeInteraction(InteractionType.WORK), localPlayerName));
						break;
					case ActionSelector.HACK:
						Events.trigger(
								new PlayerInputEvent(new InputTypeInteraction(InteractionType.HACK), localPlayerName));
						break;
					}
				} else {
					Events.trigger(
							new PlayerInputEvent(new InputTypeInteraction(InteractionType.OTHER), localPlayerName));
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
			}
		} else {
			exit = true;
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
