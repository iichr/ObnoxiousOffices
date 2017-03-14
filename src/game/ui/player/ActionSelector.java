package game.ui.player;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import game.core.world.tile.type.TileTypeComputer;
import game.ui.components.WordGenerator;
import game.ui.interfaces.ImageLocations;

public class ActionSelector {
	private Image selectorBack;
	private LinkedList<String> toShow;
	private WordGenerator wg;
	private int action;

	public ActionSelector() {
		action = 0;
	}

	/**
	 * Updates and draws the selector
	 * @param world The world variable
	 * @param localPlayerName The name of the local player
	 * @param hacking Boolean toggle, whether the player is choosing to hack or not
	 * @param width The base width of a tile
	 * @param height The base height of a tile
	 * @param g The Graphics object
	 * @throws SlickException
	 */
	public void updateSelector(World world, String localPlayerName, boolean hacking, float tileWidth, float tileHeight,
			Graphics g) throws SlickException {
		Player localPlayer = world.getPlayer(localPlayerName);
		Direction facing = localPlayer.getFacing();
		Location inFront = localPlayer.getLocation().forward(facing);
		
		//check the tile in front is in the game space
		if (inFront.checkBounds()) {
			Tile tile = inFront.getTile();
			TileType actionTile = tile.type;
			
			//check the tile is a computer
			if (actionTile.equals(TileType.COMPUTER)) {
				//set up selector
				setUpSelector(world, localPlayerName, hacking, tile);
				
				//render selector
				render(g, inFront, tileWidth, tileHeight / 2);
			}
		}
	}
	
	/**
	 * Sets up the selector to show to the user
	 * @param world The world variable
	 * @param localPlayerName The name of the local player
	 * @param hacking Boolean toggle, whether the player is choosing to hack or not
	 * @param tile The tile in front of the player
	 * @throws SlickException
	 */
	private void setUpSelector(World world, String localPlayerName, boolean hacking, Tile tile) throws SlickException{
		//set up variables
		String ownerName = TileTypeComputer.getOwningPlayer((MetaTile) tile);
		selectorBack = new Image(ImageLocations.SELECTOR, false, Image.FILTER_NEAREST);
		toShow = new LinkedList<String>();
		wg = new WordGenerator();
		
		//display different things for your pc to others
		if (ownerName.equals(localPlayerName)) {
			//show player selection if choosing who to hack
			if (hacking) {
				for (Player p : world.getPlayers()) {
					if (!p.name.equals(localPlayerName)) {
						toShow.add(p.name);
					}
				}
			} else {
				toShow.add("WORK");
				toShow.add("HACK");
			}
		} else {
			toShow.add("NONE");
		}
	}

	/**
	 * Change the value selected
	 * @param i The value passed from user input, +1 for up, -1 for down
	 */
	public void changeSelection(int i) {
		if (i < 0) {
			action = (action - 1) % toShow.size();
			if(action < 0){
				action = toShow.size() - Math.abs(action);
			}
		} else {
			action = (action + 1) % toShow.size();
		}
	}

	/**
	 * The render method
	 * @param g Graphics object
	 * @param inFront The location in front of the player
	 * @param width The base width of a tile
	 * @param height The base height of a tile
	 */
	private void render(Graphics g, Location inFront, float width, float height) {
		float x = inFront.coords.x * width;
		float y = inFront.coords.y * height;

		float characterMod = ((float) toShow.get(action).length()) / 4;
		selectorBack.draw(x + width / 2 - (width * characterMod) / 2, y, width * characterMod, height);
		wg.drawCenter(g, toShow.get(action), x + width / 2, y + height / 2, true, 5 / width);

	}
	
	/**
	 * Gets the current value from the selector
	 * @return The selected value
	 */
	public String getSelected() {
		return toShow.get(action);
	}
	
	/**
	 * Sets the action variable of selector
	 * @param action The new value for action
	 */
	public void setAction(int action) {
		this.action = action;
	}
}
