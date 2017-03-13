package game.ui.interfaces;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.core.world.Direction;
import game.core.world.tile.type.TileType;

public class SpriteLocations {
	private HashMap<TileType, HashMap<Direction, Image[]>> tileMap;

	public SpriteLocations() throws SlickException {
		tileMap = new HashMap<TileType, HashMap<Direction, Image[]>>();
		createTileMap();
	}

	public HashMap<TileType, HashMap<Direction, Image[]>> getTileMap() {
		return tileMap;
	}

	/**
	 * Creates a map between a tile and the sprites for that tile
	 * 
	 * @throws SlickException
	 */
	private void createTileMap() throws SlickException {

		Image tileSheet = new Image(TILES, false, Image.FILTER_NEAREST);
		SpriteSheet sprites = new SpriteSheet(tileSheet, 64, 128);
		addFloorImages(sprites);
		addComputerImages(sprites);
		addDeskImages(sprites);
		addChairImages(sprites);
		addCoffeeImages(sprites);
		addSofaImages(sprites);
		addFishTankImages(sprites);
		addWallImages();
	}

	/**
	 * Adds the floor images to the map
	 * 
	 * @param sprites
	 *            The sprite sheet
	 * @throws SlickException
	 */
	private void addFloorImages(SpriteSheet sprites) throws SlickException {
		HashMap<Direction, Image[]> floorMap = new HashMap<>();

		Image[] floors = { sprites.getSprite(0, 0) };
		floorMap.put(Direction.NORTH, floors);
		tileMap.put(TileType.FLOOR, floorMap);
	}

	/**
	 * Adds the computer images to the map
	 * 
	 * @param sprites
	 *            The sprite sheet
	 * @throws SlickException
	 */
	private void addComputerImages(SpriteSheet sprites) throws SlickException {
		HashMap<Direction, Image[]> pcMap = new HashMap<>();

		Image[] pcNorth = { sprites.getSprite(1, 3) };
		pcMap.put(Direction.NORTH, pcNorth);

		Image[] pcSouth = { sprites.getSprite(0, 3) };
		pcMap.put(Direction.SOUTH, pcSouth);

		tileMap.put(TileType.COMPUTER, pcMap);
	}

	/**
	 * Adds the desk images to the map
	 * 
	 * @param sprites
	 *            The sprite sheet
	 * @throws SlickException
	 */
	private void addDeskImages(SpriteSheet sprites) throws SlickException {
		HashMap<Direction, Image[]> deskMap = new HashMap<>();

		Image[] deskNorth = { sprites.getSprite(4, 6) };
		deskMap.put(Direction.NORTH, deskNorth);

		Image[] deskSouth = { sprites.getSprite(3, 6) };
		deskMap.put(Direction.SOUTH, deskSouth);

		Image[] deskEast = { sprites.getSprite(0, 6) };
		deskMap.put(Direction.EAST, deskEast);

		Image[] deskWest = { sprites.getSprite(2, 6) };
		deskMap.put(Direction.WEST, deskWest);

		tileMap.put(TileType.DESK, deskMap);
	}

	/**
	 * Adds the chair images to the map
	 * 
	 * @param sprites
	 *            The sprite sheet
	 * @throws SlickException
	 */
	private void addChairImages(SpriteSheet sprites) throws SlickException {
		HashMap<Direction, Image[]> chairMap = new HashMap<>();

		Image[] chNorth = { sprites.getSprite(1, 2) };
		chairMap.put(Direction.NORTH, chNorth);

		Image[] chSouth = { sprites.getSprite(0, 2) };
		chairMap.put(Direction.SOUTH, chSouth);

		Image[] chEast = { sprites.getSprite(1, 2) };
		chairMap.put(Direction.EAST, chEast);

		Image[] chWest = { sprites.getSprite(1, 2) };
		chairMap.put(Direction.WEST, chWest);

		tileMap.put(TileType.CHAIR, chairMap);
	}

	/**
	 * Adds the coffee images to the map
	 * 
	 * @param sprites
	 *            The sprite sheet
	 * @throws SlickException
	 */
	private void addCoffeeImages(SpriteSheet sprites) throws SlickException {
		HashMap<Direction, Image[]> coffeeMap = new HashMap<>();

		Image[] cmEast = { sprites.getSprite(0, 1) };
		coffeeMap.put(Direction.EAST, cmEast);

		Image[] cmWest = { sprites.getSprite(1, 1) };
		coffeeMap.put(Direction.WEST, cmWest);

		tileMap.put(TileType.COFFEE_MACHINE, coffeeMap);
	}

	/**
	 * Adds the sofa images to the map
	 * 
	 * @param sprites
	 *            The sprite sheet
	 * @throws SlickException
	 */
	private void addSofaImages(SpriteSheet sprites) throws SlickException {
		HashMap<Direction, Image[]> sofaMap = new HashMap<>();

		Image[] sNorth = { sprites.getSprite(0, 5), sprites.getSprite(1, 5) };
		sofaMap.put(Direction.NORTH, sNorth);

		Image[] sSouth = { sprites.getSprite(3, 5), sprites.getSprite(2, 5) };
		sofaMap.put(Direction.SOUTH, sSouth);

		tileMap.put(TileType.SOFA, sofaMap);
	}

	/**
	 * Adds the fish tank images to the map
	 * 
	 * @param sprites
	 *            The sprite sheet
	 * @throws SlickException
	 */
	private void addFishTankImages(SpriteSheet sprites) throws SlickException {
		HashMap<Direction, Image[]> fishTankMap = new HashMap<>();

		Image[] fTSouth = { sprites.getSprite(0, 4), sprites.getSprite(1, 4) };
		fishTankMap.put(Direction.NORTH, fTSouth);

		tileMap.put(TileType.FISH, fishTankMap);
	}

	/**
	 * Adds the wall images to the map
	 * 
	 * @throws SlickException
	 */
	private void addWallImages() throws SlickException {
		// load sprite sheet
		Image wallSheet = new Image(TILE_WALLS, false, Image.FILTER_NEAREST);
		SpriteSheet walls = new SpriteSheet(wallSheet, 64, 192);

		HashMap<Direction, Image[]> wallMap = new HashMap<>();
		HashMap<Direction, Image[]> cornerMap = new HashMap<>();
		HashMap<Direction, Image[]> doorMap = new HashMap<>();

		// wall sprites
		Image[] wNorth = { walls.getSprite(0, 0) };
		wallMap.put(Direction.NORTH, wNorth);

		Image[] wSouth = { walls.getSprite(1, 0) };
		wallMap.put(Direction.SOUTH, wSouth);

		Image[] wSide = { walls.getSprite(2, 0) };
		wallMap.put(Direction.EAST, wSide);
		wallMap.put(Direction.WEST, wSide);

		// corner sprites
		Image[] wcEast = { walls.getSprite(3, 0) };
		cornerMap.put(Direction.EAST, wcEast);

		Image[] wcNorth = { walls.getSprite(4, 0) };
		cornerMap.put(Direction.NORTH, wcNorth);

		Image[] wcSouth = { walls.getSprite(5, 0) };
		cornerMap.put(Direction.SOUTH, wcSouth);

		Image[] wcWest = { walls.getSprite(6, 0) };
		cornerMap.put(Direction.WEST, wcWest);

		// door sprites
		Image[] dNorth = { walls.getSprite(7, 0) };
		doorMap.put(Direction.NORTH, dNorth);

		Image[] dSouth = { walls.getSprite(8, 0) };
		doorMap.put(Direction.SOUTH, dSouth);

		tileMap.put(TileType.WALL, wallMap);
		tileMap.put(TileType.WALL_CORNER, cornerMap);
		tileMap.put(TileType.DOOR, doorMap);
	}

	// TILE LOCATIONS
	public static final String TILES = "/res/sprites/tiles/tiles.png";

	public static final String TILE_WALL = "/res/sprites/tiles/wall.png";

	public static final String TILE_WALLS = "res/sprites/tiles/walls.png";

	// PLAYER SPRITE LOCATIONS
	public static final String PLAYER_BLONDE_STANDING = "/res/sprites/players/blonde/blondeStanding.png";
	public static final String PLAYER_BLONDE_NORTH_SEAT = "/res/sprites/players/blonde/PlayerBlondeSeatedNorth.png";
	public static final String PLAYER_BLONDE_NORTH_SLEEP = "/res/sprites/players/blonde/PlayerBlondeSleepNorth.png";
	public static final String PLAYER_BLONDE_SOUTH_SLEEP = "/res/sprites/players/blonde/PlayerBlondeSleepSouth.png";

	public static final String PLAYER_DARK_STANDING = "/res/sprites/players/dark/darkStanding.png";
	public static final String PLAYER_DARK_NORTH_SEAT = "res/sprites/players/dark/PlayerDarkSeatedNorth.png";
	public static final String PLAYER_DARK_NORTH_SLEEP = "res/sprites/players/dark/PlayerDarkSleepNorth.png";
	public static final String PLAYER_DARK_SOUTH_SLEEP = "res/sprites/players/dark/PlayerDarkSleepSouth.png";

	public static final String PLAYER_BROWN_STANDING = "/res/sprites/players/brown/brownStanding.png";
	public static final String PLAYER_BROWN_NORTH_SEAT = "res/sprites/players/brown/PlayerBrownSeatedNorth.png";
	public static final String PLAYER_BROWN_NORTH_SLEEP = "res/sprites/players/brown/PlayerBrownSleepNorth.png";
	public static final String PLAYER_BROWN_SOUTH_SLEEP = "res/sprites/players/brown/PlayerBrownSleepSouth.png";

	public static final String PLAYER_PINK_STANDING = "/res/sprites/players/pink/pinkStanding.png";
	public static final String PLAYER_PINK_NORTH_SEAT = "res/sprites/players/pink/PlayerPinkSeatedNorth.png";

	public static final String PLAYER_PINK_NORTH_SLEEP = "res/sprites/players/pink/PlayerPinkSleepNorth.png";
	public static final String PLAYER_PINK_SOUTH_SLEEP = "res/sprites/players/pink/PlayerPinkSleepSouth.png";
}
