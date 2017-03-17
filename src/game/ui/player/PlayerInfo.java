package game.ui.player;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.player.Player;
import game.core.util.Coordinates;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.type.TileType;
import game.ui.components.WordGenerator;
import game.ui.interfaces.ImageLocations;

public class PlayerInfo {
	private List<Player> players;
	private String localPlayerName;

	private float tileWidth;
	private float tileHeight;
	private WordGenerator wg;
	private Image sitDialogue, drinkDialogue, sleepDialogue;
	private Image playerIdentifier;

	/**
	 * Constructor: sets up variables and reads in the images needed
	 * 
	 * @param world
	 *            The world variable
	 * @param localPlayerName
	 *            The name of the local player
	 * @param tileWidth
	 *            The width of an individual tile
	 * @param tileHeight
	 *            The height of an individual tile
	 * @throws SlickException
	 */
	public PlayerInfo(World world, String localPlayerName, float tileWidth, float tileHeight, WordGenerator wg) throws SlickException {
		this.localPlayerName = localPlayerName;
		players = world.getPlayers();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.wg = wg;

		sitDialogue = new Image(ImageLocations.SIT_DIALOGUE, false, Image.FILTER_NEAREST);
		drinkDialogue = new Image(ImageLocations.DRINK_DIALOGUE, false, Image.FILTER_NEAREST);
		sleepDialogue = new Image(ImageLocations.SLEEP_DIALOGUE, false, Image.FILTER_NEAREST);
		playerIdentifier = new Image(ImageLocations.PLAYER_IDENTIFIER, false, Image.FILTER_NEAREST);
	}

	/**
	 * Renders information about the players
	 * 
	 * @param g
	 *            The graphics object
	 * @param visible
	 *            The array containing which tiles are visible to the player
	 */
	public void render(Graphics g, boolean[][] visible) {
		for (Player p : players) {
			g.setColor(Color.black);
			Location pLocation = p.getLocation();

			float playerX = pLocation.coords.x * tileWidth;
			float playerY = (pLocation.coords.y + 1) * (tileHeight / 2);
			float offsetX = tileWidth / 2;
			float offsetY = tileHeight / 8;

			// draw player names
			if (visible[pLocation.coords.x][pLocation.coords.y]) {
				wg.drawCenter(g, p.name, (playerX + offsetX), (playerY - offsetY), true, 0.1f);
			}

			if (p.name.equals(localPlayerName)) {
				// add identifier for player
				playerIdentifier.draw(playerX + tileWidth / 4, playerY - tileHeight / 3, tileWidth / 2, tileHeight / 8);

				// add pop-ups
				Location inFront = pLocation.forward(p.getFacing());
				if (inFront.checkBounds()) {
					TileType type = inFront.getTile().type;
					Coordinates coords = inFront.coords;
					checkDialogue(type, coords);
				}
			}
		}
	}

	/**
	 * Check if the tile has a pop-up
	 * 
	 * @param type
	 *            The tile type to display dialogue about
	 * @param coords
	 *            the coordinates of the tile
	 */
	private void checkDialogue(TileType type, Coordinates coords) {
		if (type.equals(TileType.CHAIR)) {
			drawDialogue(sitDialogue, coords);
		} else if (type.equals(TileType.COFFEE_MACHINE)) {
			drawDialogue(drinkDialogue, coords);
		} else if (type.equals(TileType.SOFA)) {
			drawDialogue(sleepDialogue, coords);
		}
	}

	/**
	 * Display pop-up dialogue about a tile
	 * 
	 * @param toDraw
	 *            the dialogue to draw
	 * @param coords
	 *            the coordinates of the tile
	 */
	private void drawDialogue(Image toDraw, Coordinates coords) {
		toDraw.draw((coords.x * tileWidth), (coords.y + 1) * (tileHeight / 2), tileWidth, tileHeight / 2);
	}
}
