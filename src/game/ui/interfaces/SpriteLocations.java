package game.ui.interfaces;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.world.Direction;
import game.core.world.tile.TileType;

public class SpriteLocations {
	private HashMap<TileType, HashMap<Direction, Image[]>> tileMap;

	public SpriteLocations() throws SlickException {
		tileMap = new HashMap<TileType, HashMap<Direction, Image[]>>();
		createTileMap();
	}
	
	public HashMap<TileType, HashMap<Direction, Image[]>> getTileMap(){
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

		Image d = new Image(TILE_DESK, false, Image.FILTER_NEAREST);
		Image[] desk = { d };
		deskMap.put(Direction.NORTH, desk);

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
		// TODO get Sofa images
	}
	
	private void addDecorativeImages() throws SlickException {
		HashMap<Direction, Image[]> decorationMap = new HashMap<>();
		
		Image decN = new Image(TILE_PLANT, false, Image.FILTER_NEAREST);
		Image[] decNorth = { decN };
		decorationMap.put(Direction.EAST, decNorth);
		
		tileMap.put(TileType.PLANT, decorationMap);
	}

	// TILE LOCATIONS
	public static final String TILE_FLOOR = "/res/sprites/tiles/floor.png";

	public static final String TILE_PC_NORTH = "/res/sprites/tiles/computerNorth.png";
	public static final String TILE_PC_SOUTH = "/res/sprites/tiles/computerSouth.png";

	public static final String TILE_DESK = "/res/sprites/tiles/desk.png";

	public static final String TILE_CHAIR_NORTH = "/res/sprites/tiles/chair.png";
	public static final String TILE_CHAIR_SOUTH = "/res/sprites/tiles/chair.png";
	public static final String TILE_CHAIR_EAST = "/res/sprites/tiles/chair.png";
	public static final String TILE_CHAIR_WEST = "/res/sprites/tiles/chair.png";

	public static final String TILE_COFFEE_MACHINE_EAST = "/res/sprites/tiles/coffee.png";
	public static final String TILE_COFFEE_MACHINE_WEST = "/res/sprites/tiles/coffee.png";

	public static final String TILE_PLANT = "/res/sprites/tiles/plant.png";

	public static final String TILE_WALL = "/res/sprites/tiles/wall.png";

	// PLAYER SPRITE LOCATIONS
	public static final String PLAYER_BLONDE_STANDING_NORTH = "/res/sprites/players/PlayerBlondeStandingNorth.png";
	public static final String PLAYER_BLONDE_STANDING_SOUTH = "/res/sprites/players/PlayerBlondeStandingSouth.png";
	public static final String PLAYER_BLONDE_STANDING_EAST = "/res/sprites/players/PlayerBlondeStandingEast.png";
	public static final String PLAYER_BLONDE_STANDING_WEST = "/res/sprites/players/PlayerBlondeStandingWest.png";
	
	public static final String PLAYER_DARK_STANDING_NORTH = "/res/sprites/players/PlayerDarkStandingNorth.png";
	public static final String PLAYER_DARK_STANDING_SOUTH = "/res/sprites/players/PlayerDarkStandingSouth.png";
	public static final String PLAYER_DARK_STANDING_EAST = "/res/sprites/players/PlayerDarkStandingEast.png";
	public static final String PLAYER_DARK_STANDING_WEST = "/res/sprites/players/PlayerDarkStandingWest.png";
	
	public static final String PLAYER_BROWN_STANDING_NORTH = "/res/sprites/players/PlayerBrownStandingNorth.png";
	public static final String PLAYER_BROWN_STANDING_SOUTH = "/res/sprites/players/PlayerBrownStandingSouth.png";
	public static final String PLAYER_BROWN_STANDING_EAST = "/res/sprites/players/PlayerBrownStandingEast.png";
	public static final String PLAYER_BROWN_STANDING_WEST = "/res/sprites/players/PlayerBrownStandingWest.png";
	
	public static final String PLAYER_PINK_STANDING_NORTH = "/res/sprites/players/PlayerPinkStandingNorth.png";
	public static final String PLAYER_PINK_STANDING_SOUTH = "/res/sprites/players/PlayerPinkStandingSouth.png";
	public static final String PLAYER_PINK_STANDING_EAST = "/res/sprites/players/PlayerPinkStandingEast.png";
	public static final String PLAYER_PINK_STANDING_WEST = "/res/sprites/players/PlayerPinkStandingWest.png";
	
}
