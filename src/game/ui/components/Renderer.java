package game.ui.components;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.event.Events;
import game.core.event.player.PlayerMovedEvent;
import game.core.player.Player;
import game.core.player.action.PlayerActionSleep;
import game.core.player.state.PlayerState;
import game.core.util.Coordinates;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import game.core.world.tile.type.TileTypeComputer;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.SpriteLocations;
import game.ui.interfaces.Vals;
import game.ui.player.PlayerAnimation;

/**
 * Renders the game to the screen
 */
public class Renderer {
	// world info
	private World world;
	private String localPlayerName;

	// tile info
	private float tileWidth;
	private float tileHeight;
	private HashMap<TileType, HashMap<Direction, Image[]>> tileMap;

	// player animations
	private List<PlayerAnimation> playerAnimations;

	// boolean toggles
	private boolean showOverview;

	/**
	 * Constructor: Creates a renderer object and sets up player animations
	 * 
	 * @param world
	 *            The world variable
	 * @param localPlayerName
	 *            The name of the local player
	 * @param tileWidth
	 *            The width of a single tile
	 * @param tileHeight
	 *            The height of a single tile
	 * @param showOverview
	 *            Whether the player overview should be rendered
	 * @throws SlickException
	 */
	public Renderer(World world, String localPlayerName, float tileWidth, float tileHeight, boolean showOverview)
			throws SlickException {
		this.world = world;
		this.localPlayerName = localPlayerName;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.showOverview = showOverview;

		Events.on(PlayerMovedEvent.class, this::animateMovement);

		// get the tileMap
		SpriteLocations sp = new SpriteLocations();
		tileMap = sp.getTileMap();

		// add player animations
		playerAnimations = animatePlayers(world.getPlayers());
	}

	/**
	 * Renders the world to the screen
	 * 
	 * @param visible
	 *            Array containing whether each tile is visible to he local
	 *            player
	 * @throws SlickException
	 */
	public void drawWorld(boolean[][] visible) throws SlickException {
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

	/**
	 * Draws a marker for the local players computer
	 * 
	 * @param tileX
	 *            The x position of the tile to draw over
	 * @param tileY
	 *            The y position of the tile to draw over
	 * @param found
	 *            The tile at this location
	 * @throws SlickException
	 */
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

	/**
	 * Renders a tile to the screen
	 * 
	 * @param tileX
	 *            The x position to draw the tile on the screen
	 * @param tileY
	 *            The y position to draw the tile on the screen
	 * @param tile
	 *            The tile to draw
	 * @param visible
	 *            Whether the tile is visible or not
	 */
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
			if (type.equals(TileType.WALL) || type.equals(TileType.WALL_CORNER) || type.equals(TileType.DOOR)) {
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
		List<Player> players = World.world.getPlayers();
		// render the players
		for (Player player : players) {
			Location playerLocation = player.getLocation();
			if (playerLocation.coords.x == x && playerLocation.coords.y == y) {
				PlayerAnimation animation = playerAnimations.get(player.getHair());
				changeAnimation(player, animation);
				if (player.status.hasAction(PlayerActionSleep.class)) {
					Location right = new Location(new Coordinates(x - 1, y, 0), world);
					if (right.checkBounds()) {
						animation.drawPlayer((x - 1) * tileWidth, (y + 2) * (tileHeight / 2), tileWidth * 2,
								tileHeight / 2);
					} else {
						// otherwise draw to the left
						animation.drawPlayer(tileX, (y + 2) * (tileHeight / 2), tileWidth * 2, tileHeight / 2);
					}
				} else {
					animation.drawPlayer(tileX, tileY, tileWidth, tileHeight);
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
	private void changeAnimation(Player player, PlayerAnimation animation) {
		Direction playerFacing = player.getFacing();
		if (player.status.hasState(PlayerState.sleeping)) {
			animation.sleeping(playerFacing);
		} else if (player.status.hasState(PlayerState.sitting)) {
			animation.seated(playerFacing);
		} else {
			animation.turn(player.getFacing());
		}
	}

	/**
	 * Map players to player animations
	 * 
	 * @param players
	 *            The set of Players in the world
	 * 
	 * @throws SlickException
	 */
	private List<PlayerAnimation> animatePlayers(List<Player> players) throws SlickException {
		LinkedList<PlayerAnimation> animations = new LinkedList<PlayerAnimation>();
		for (Player p : players) {
			PlayerAnimation animation = new PlayerAnimation(p.getHair());
			animation.turn(p.getFacing());
			animations.add(animation);
		}
		return animations;
	}

	/**
	 * Animates the players movement
	 * 
	 * @param e
	 *            A player moved event
	 */
	private void animateMovement(PlayerMovedEvent e) {
		Player moved = World.world.getPlayer(e.playerName);
		playerAnimations.get(moved.getHair()).nextFrame();
	}

	/**
	 * Find which tiles are visible to the local player
	 * 
	 * @return 2D boolean array [x][y] = true then visible, false = not visible
	 */
	public boolean[][] findVisibles() {
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
	 *            Array containing whether each tile is visible to he local
	 *            player
	 * @param current
	 *            The current location being looked at
	 * @return 2D boolean array [x][y] = true then visible, false = not visible
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
	 *            Array containing whether each tile is visible to he local
	 *            player
	 * @param toCheck
	 *            The location being checked
	 * @return 2D boolean array [x][y] = true then visible, false = not visible
	 */
	private boolean[][] checkContinue(boolean[][] visible, Location toCheck) {
		int x = toCheck.coords.x;
		int y = toCheck.coords.y;
		if (x >= 0 && x < visible.length && y >= 0 && y < visible[0].length) {
			if (visible[x][y] != true) {
				if (toCheck.checkBounds()) {
					TileType found = toCheck.getTile().type;
					if (!found.equals(TileType.WALL) && !found.equals(TileType.WALL_CORNER)
							&& !found.equals(TileType.DOOR)) {
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
}
