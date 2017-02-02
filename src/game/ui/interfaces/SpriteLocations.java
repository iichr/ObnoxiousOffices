package game.ui.interfaces;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.world.tile.TileType;

/**
 * An interface for all the game sprite locations
 */
public interface SpriteLocations {
	
	//SPRITE LOCATIONS
	public static final String TEST_CIRCLE_GREEN = "/res/sprites/circle.png";
	public static final String TEST_CIRCLE_PINK = "/res/sprites/circle2.png";
	
	//TILE LOCATIONS
	public static final String TILE_CHAIR = "/res/sprites/tiles/chair.png";
	public static final String TILE_DESK_CHAIR = "/res/sprites/tiles/desk_chair.png";
	public static final String TILE_DESK = "/res/sprites/tiles/desk.png";
	public static final String TILE_PC = "/res/sprites/tiles/pc.png";
	public static final String TILE_COFFEE_MACHINE = "/res/sprites/tiles/coffee.png";
	public static final String TILE_FLOOR1 = "/res/sprites/tiles/floor1.png";
	public static final String TILE_FLOOR2 = "/res/sprites/tiles/floor2.png";
	public static final String TILE_PLANT = "/res/sprites/tiles/plant.png";
	public static final String TILE_WALL = "/res/sprites/tiles/wall.png";
	
	//PLAYER SPRITE LOCATIONS
	public static final String PLAYER_BLONDE_STANDING_NORTH = "/res/sprites/players/PlayerBlondeStandingNorth.png";
	public static final String PLAYER_BLONDE_STANDING_SOUTH = "/res/sprites/players/PlayerBlondeStandingSouth.png";
	public static final String PLAYER_BLONDE_STANDING_EAST = "/res/sprites/players/PlayerBlondeStandingEast.png";
	public static final String PLAYER_BLONDE_STANDING_WEST = "/res/sprites/players/PlayerBlondeStandingWest.png";
	
	//map between tile types and images
	public static HashMap<TileType, Image[]> createMap() throws SlickException {
		HashMap<TileType, Image[]> imageMap = new HashMap<>();
		Image[] floor = { new Image(TILE_FLOOR2, false, Image.FILTER_NEAREST),
				new Image(TILE_FLOOR1, false, Image.FILTER_NEAREST) };
		imageMap.put(TileType.FLOOR, floor);

		Image[] desk = { new Image(TILE_DESK, false, Image.FILTER_NEAREST) };
		imageMap.put(TileType.DESK, desk);

		Image[] chair = { new Image(TILE_CHAIR, false, Image.FILTER_NEAREST) };
		imageMap.put(TileType.CHAIR, chair);

		Image[] pc = { new Image(TILE_PC, false, Image.FILTER_NEAREST) };
		imageMap.put(TileType.COMPUTER, pc);

		Image[] cm = { new Image(TILE_COFFEE_MACHINE, false, Image.FILTER_NEAREST) };
		imageMap.put(TileType.COFFEE_MACHINE, cm);

		Image[] plant = { new Image(TILE_PLANT, false, Image.FILTER_NEAREST) };
		imageMap.put(TileType.PLANT, plant);
		return imageMap;
	}
	
}
