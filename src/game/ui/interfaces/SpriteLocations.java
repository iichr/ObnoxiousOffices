package game.ui.interfaces;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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

	private void createTileMap() throws SlickException {
		addFloorImages();
		addComputerImages();
		addDeskImages();
		addChairImages();
		addCoffeeImages();
		addSofaImages();
		addDecorativeImages();
		addFishTankImages();
		addWallImages();
		addCornerImages();
		addDoorImages();
	}

	private void addFloorImages() throws SlickException {
		HashMap<Direction, Image[]> floorMap = new HashMap<>();

		Image fl1 = new Image(TILE_FLOOR, false, Image.FILTER_NEAREST);
		Image[] floors = { fl1 };
		floorMap.put(Direction.NORTH, floors);
		tileMap.put(TileType.FLOOR, floorMap);
	}

	private void addComputerImages() throws SlickException {
		HashMap<Direction, Image[]> pcMap = new HashMap<>();

		Image pcN = new Image(TILE_PC_NORTH, false, Image.FILTER_NEAREST);
		Image[] pcNorth = { pcN };
		pcMap.put(Direction.NORTH, pcNorth);

		Image pcS = new Image(TILE_PC_SOUTH, false, Image.FILTER_NEAREST);
		Image[] pcSouth = { pcS };
		pcMap.put(Direction.SOUTH, pcSouth);

		tileMap.put(TileType.COMPUTER, pcMap);
	}

	private void addDeskImages() throws SlickException {
		HashMap<Direction, Image[]> deskMap = new HashMap<>();

		Image dN = new Image(TILE_DESK_END_NORTH, false, Image.FILTER_NEAREST);
		Image[] deskNorth = { dN };
		deskMap.put(Direction.NORTH, deskNorth);

		Image dS = new Image(TILE_DESK_END_SOUTH, false, Image.FILTER_NEAREST);
		Image[] deskSouth = { dS };
		deskMap.put(Direction.SOUTH, deskSouth);

		Image dE = new Image(TILE_DESK_END_EAST, false, Image.FILTER_NEAREST);
		Image[] deskEast = { dE };
		deskMap.put(Direction.EAST, deskEast);

		Image dW = new Image(TILE_DESK_END_WEST, false, Image.FILTER_NEAREST);
		Image[] deskWest = { dW };
		deskMap.put(Direction.WEST, deskWest);

		tileMap.put(TileType.DESK, deskMap);
	}

	private void addChairImages() throws SlickException {
		HashMap<Direction, Image[]> chairMap = new HashMap<>();

		Image chN = new Image(TILE_CHAIR_NORTH, false, Image.FILTER_NEAREST);
		Image[] chNorth = { chN };
		chairMap.put(Direction.NORTH, chNorth);

		Image chS = new Image(TILE_CHAIR_SOUTH, false, Image.FILTER_NEAREST);
		Image[] chSouth = { chS };
		chairMap.put(Direction.SOUTH, chSouth);

		Image chE = new Image(TILE_CHAIR_EAST, false, Image.FILTER_NEAREST);
		Image[] chEast = { chE };
		chairMap.put(Direction.EAST, chEast);

		Image chW = new Image(TILE_CHAIR_WEST, false, Image.FILTER_NEAREST);
		Image[] chWest = { chW };
		chairMap.put(Direction.WEST, chWest);

		tileMap.put(TileType.CHAIR, chairMap);
	}

	private void addCoffeeImages() throws SlickException {
		HashMap<Direction, Image[]> coffeeMap = new HashMap<>();

		Image cmE = new Image(TILE_COFFEE_MACHINE_EAST, false, Image.FILTER_NEAREST);
		Image[] cmEast = { cmE };
		coffeeMap.put(Direction.EAST, cmEast);

		Image cmW = new Image(TILE_COFFEE_MACHINE_WEST, false, Image.FILTER_NEAREST);
		Image[] cmWest = { cmW };
		coffeeMap.put(Direction.WEST, cmWest);

		tileMap.put(TileType.COFFEE_MACHINE, coffeeMap);
	}

	private void addSofaImages() throws SlickException {
		HashMap<Direction, Image[]> sofaMap = new HashMap<>();

		Image sNR = new Image(TILE_SOFA_NORTH_RIGHT, false, Image.FILTER_NEAREST);
		Image sNL = new Image(TILE_SOFA_NORTH_LEFT, false, Image.FILTER_NEAREST);
		Image[] sNorth = { sNL, sNR };
		sofaMap.put(Direction.NORTH, sNorth);

		Image sSR = new Image(TILE_SOFA_SOUTH_RIGHT, false, Image.FILTER_NEAREST);
		Image sSL = new Image(TILE_SOFA_SOUTH_LEFT, false, Image.FILTER_NEAREST);
		Image[] sSouth = { sSR, sSL };
		sofaMap.put(Direction.SOUTH, sSouth);

		tileMap.put(TileType.SOFA, sofaMap);
	}

	private void addDecorativeImages() throws SlickException {
		HashMap<Direction, Image[]> decorationMap = new HashMap<>();

		Image decN = new Image(TILE_PLANT, false, Image.FILTER_NEAREST);
		Image[] decNorth = { decN };
		decorationMap.put(Direction.EAST, decNorth);

		tileMap.put(TileType.PLANT, decorationMap);
	}

	private void addFishTankImages() throws SlickException {
		HashMap<Direction, Image[]> fishTankMap = new HashMap<>();

		Image fTL = new Image(TILE_FISH_TANK_LEFT, false, Image.FILTER_NEAREST);
		Image fTR = new Image(TILE_FISH_TANK_RIGHT, false, Image.FILTER_NEAREST);
		Image[] fTSouth = { fTL, fTR };
		fishTankMap.put(Direction.NORTH, fTSouth);

		tileMap.put(TileType.FISH, fishTankMap);
	}
	
	private void addWallImages() throws SlickException {
		HashMap<Direction, Image[]> wallMap = new HashMap<>();

		Image wN = new Image(TILE_WALL_NORTH, false, Image.FILTER_NEAREST);
		Image[] wNorth = { wN };
		wallMap.put(Direction.NORTH, wNorth);

		Image wE = new Image(TILE_WALL_EAST, false, Image.FILTER_NEAREST);
		Image[] wEast = { wE };
		wallMap.put(Direction.EAST, wEast);
		
		Image wS = new Image(TILE_WALL_SOUTH, false, Image.FILTER_NEAREST);
		Image[] wSouth = { wS };
		wallMap.put(Direction.SOUTH, wSouth);
		
		Image wW = new Image(TILE_WALL_WEST, false, Image.FILTER_NEAREST);
		Image[] wWest = { wW };
		wallMap.put(Direction.WEST, wWest);

		tileMap.put(TileType.WALL, wallMap);
	}
	
	private void addCornerImages() throws SlickException {
		HashMap<Direction, Image[]> wallMap = new HashMap<>();

		Image wcN = new Image(TILE_CORNER_NORTH, false, Image.FILTER_NEAREST);
		Image[] wcNorth = { wcN };
		wallMap.put(Direction.NORTH, wcNorth);

		Image wcE = new Image(TILE_CORNER_EAST, false, Image.FILTER_NEAREST);
		Image[] wcEast = { wcE };
		wallMap.put(Direction.EAST, wcEast);
		
		Image wcS = new Image(TILE_CORNER_SOUTH, false, Image.FILTER_NEAREST);
		Image[] wcSouth = { wcS };
		wallMap.put(Direction.SOUTH, wcSouth);
		
		Image wcW = new Image(TILE_CORNER_WEST, false, Image.FILTER_NEAREST);
		Image[] wcWest = { wcW };
		wallMap.put(Direction.WEST, wcWest);

		tileMap.put(TileType.WALL_CORNER, wallMap);
	}
	
	private void addDoorImages() throws SlickException {
		HashMap<Direction, Image[]> doorMap = new HashMap<>();

		Image dN = new Image(TILE_DOOR_NORTH, false, Image.FILTER_NEAREST);
		Image[] dNorth = { dN };
		doorMap.put(Direction.NORTH, dNorth);

		Image dS = new Image(TILE_DOOR_SOUTH, false, Image.FILTER_NEAREST);
		Image[] dSouth = { dS };
		doorMap.put(Direction.SOUTH, dSouth);

		tileMap.put(TileType.DOOR, doorMap);
	}

	// TILE LOCATIONS
	public static final String TILE_FLOOR = "/res/sprites/tiles/floor.png";

	public static final String TILE_PC_NORTH = "/res/sprites/tiles/computerNorth.png";
	public static final String TILE_PC_SOUTH = "/res/sprites/tiles/computerSouth.png";

	public static final String TILE_DESK_END_NORTH = "/res/sprites/tiles/deskEndNorth.png";
	public static final String TILE_DESK_END_SOUTH = "/res/sprites/tiles/deskEndSouth.png";
	public static final String TILE_DESK_END_EAST = "/res/sprites/tiles/deskEndEast.png";
	public static final String TILE_DESK_END_WEST = "/res/sprites/tiles/deskEndWest.png";

	public static final String TILE_CHAIR_NORTH = "/res/sprites/tiles/chairNorth.png";
	public static final String TILE_CHAIR_SOUTH = "/res/sprites/tiles/chairSouth.png";
	public static final String TILE_CHAIR_EAST = "/res/sprites/tiles/chairNorth.png";
	public static final String TILE_CHAIR_WEST = "/res/sprites/tiles/chairNorth.png";

	public static final String TILE_COFFEE_MACHINE_EAST = "/res/sprites/tiles/coffeeEast.png";
	public static final String TILE_COFFEE_MACHINE_WEST = "/res/sprites/tiles/coffeeWest.png";

	public static final String TILE_SOFA_NORTH_LEFT = "/res/sprites/tiles/sofaNorthLeft.png";
	public static final String TILE_SOFA_NORTH_RIGHT = "/res/sprites/tiles/sofaNorthRight.png";
	public static final String TILE_SOFA_SOUTH_LEFT = "/res/sprites/tiles/sofaSouthLeft.png";
	public static final String TILE_SOFA_SOUTH_RIGHT = "/res/sprites/tiles/sofaSouthRight.png";

	public static final String TILE_PLANT = "/res/sprites/tiles/plant.png";

	public static final String TILE_FISH_TANK_LEFT = "/res/sprites/tiles/fishTankLeft.png";
	public static final String TILE_FISH_TANK_RIGHT = "/res/sprites/tiles/fishTankRight.png";

	public static final String TILE_WALL = "/res/sprites/tiles/wall.png";
	
	public static final String TILE_WALL_NORTH = "/res/sprites/tiles/wallNorth.png";
	public static final String TILE_WALL_SOUTH = "/res/sprites/tiles/wallSouth.png";
	public static final String TILE_WALL_EAST = "/res/sprites/tiles/wallSide.png";
	public static final String TILE_WALL_WEST = "/res/sprites/tiles/wallSide.png";
	
	public static final String TILE_CORNER_NORTH = "/res/sprites/tiles/wallCornerNorthWest.png";
	public static final String TILE_CORNER_SOUTH = "/res/sprites/tiles/wallCornerSouthEast.png";
	public static final String TILE_CORNER_EAST = "/res/sprites/tiles/wallCornerNorthEast.png";
	public static final String TILE_CORNER_WEST = "/res/sprites/tiles/wallCornerSouthWest.png";
	
	public static final String TILE_DOOR_NORTH = "/res/sprites/tiles/doorNorth.png";
	public static final String TILE_DOOR_SOUTH = "/res/sprites/tiles/doorSouth.png";

	// PLAYER SPRITE LOCATIONS
	public static final String PLAYER_BLONDE_NORTH_WALK1 = "/res/sprites/players/blonde/PlayerBlondeNorthWalk1.png";
	public static final String PLAYER_BLONDE_NORTH_WALK2 = "/res/sprites/players/blonde/PlayerBlondeNorthWalk2.png";
	public static final String PLAYER_BLONDE_NORTH_WALK3 = "/res/sprites/players/blonde/PlayerBlondeNorthWalk3.png";
	public static final String PLAYER_BLONDE_NORTH_WALK4 = "/res/sprites/players/blonde/PlayerBlondeNorthWalk4.png";
	public static final String PLAYER_BLONDE_NORTH_WALK5 = "/res/sprites/players/blonde/PlayerBlondeNorthWalk5.png";
	public static final String PLAYER_BLONDE_NORTH_WALK6 = "/res/sprites/players/blonde/PlayerBlondeNorthWalk6.png";
	public static final String PLAYER_BLONDE_NORTH_WALK7 = "/res/sprites/players/blonde/PlayerBlondeNorthWalk7.png";
	public static final String PLAYER_BLONDE_NORTH_WALK8 = "/res/sprites/players/blonde/PlayerBlondeNorthWalk8.png";

	public static final String PLAYER_BLONDE_STANDING_SOUTH = "/res/sprites/players/blonde/PlayerBlondeStandingSouth.png";
	public static final String PLAYER_BLONDE_STANDING_EAST = "/res/sprites/players/blonde/PlayerBlondeStandingEast.png";
	public static final String PLAYER_BLONDE_STANDING_WEST = "/res/sprites/players/blonde/PlayerBlondeStandingWest.png";

	public static final String PLAYER_BLONDE_NORTH_SEAT = "/res/sprites/players/blonde/PlayerBlondeSeatedNorth.png";

	public static final String PLAYER_BLONDE_NORTH_SLEEP = "/res/sprites/players/blonde/PlayerBlondeSleepNorth.png";
	public static final String PLAYER_BLONDE_SOUTH_SLEEP = "/res/sprites/players/blonde/PlayerBlondeSleepSouth.png";

	public static final String PLAYER_DARK_STANDING_NORTH = "/res/sprites/players/dark/PlayerDarkStandingNorth.png";
	public static final String PLAYER_DARK_STANDING_SOUTH = "/res/sprites/players/dark/PlayerDarkStandingSouth.png";
	public static final String PLAYER_DARK_STANDING_EAST = "/res/sprites/players/dark/PlayerDarkStandingEast.png";
	public static final String PLAYER_DARK_STANDING_WEST = "/res/sprites/players/dark/PlayerDarkStandingWest.png";

	public static final String PLAYER_DARK_NORTH_SEAT = "res/sprites/players/dark/PlayerDarkSeatedNorth.png";

	public static final String PLAYER_DARK_NORTH_SLEEP = "res/sprites/players/dark/PlayerDarkSleepNorth.png";
	public static final String PLAYER_DARK_SOUTH_SLEEP = "res/sprites/players/dark/PlayerDarkSleepSouth.png";

	public static final String PLAYER_BROWN_STANDING_NORTH = "res/sprites/players/brown/PlayerBrownStandingNorth.png";
	public static final String PLAYER_BROWN_STANDING_SOUTH = "res/sprites/players/brown/PlayerBrownStandingSouth.png";
	public static final String PLAYER_BROWN_STANDING_EAST = "res/sprites/players/brown/PlayerBrownStandingEast.png";
	public static final String PLAYER_BROWN_STANDING_WEST = "res/sprites/players/brown/PlayerBrownStandingWest.png";

	public static final String PLAYER_BROWN_NORTH_SEAT = "res/sprites/players/brown/PlayerBrownSeatedNorth.png";

	public static final String PLAYER_BROWN_NORTH_SLEEP = "res/sprites/players/brown/PlayerBrownSleepNorth.png";
	public static final String PLAYER_BROWN_SOUTH_SLEEP = "res/sprites/players/brown/PlayerBrownSleepSouth.png";

	public static final String PLAYER_PINK_STANDING_NORTH = "res/sprites/players/pink/PlayerPinkStandingNorth.png";
	public static final String PLAYER_PINK_STANDING_SOUTH = "res/sprites/players/pink/PlayerPinkStandingSouth.png";
	public static final String PLAYER_PINK_STANDING_EAST = "res/sprites/players/pink/PlayerPinkStandingEast.png";
	public static final String PLAYER_PINK_STANDING_WEST = "res/sprites/players/pink/PlayerPinkStandingWest.png";

	public static final String PLAYER_PINK_NORTH_SEAT = "res/sprites/players/pink/PlayerPinkSeatedNorth.png";

	public static final String PLAYER_PINK_NORTH_SLEEP = "res/sprites/players/pink/PlayerPinkSleepNorth.png";
	public static final String PLAYER_PINK_SOUTH_SLEEP = "res/sprites/players/pink/PlayerPinkSleepSouth.png";
}
