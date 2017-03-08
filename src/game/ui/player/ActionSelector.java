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

	public void updateSelector(World world, String localPlayerName, boolean hacking, float tileWidth, float tileHeight,
			Graphics g) throws SlickException {
		Player localPlayer = world.getPlayer(localPlayerName);
		Direction facing = localPlayer.getFacing();
		Location inFront = localPlayer.getLocation().forward(facing);
		
		if (inFront.checkBounds()) {
			Tile tile = inFront.getTile();
			TileType actionTile = tile.type;
			if (actionTile.equals(TileType.COMPUTER)) {
				String ownerName = TileTypeComputer.getOwningPlayer((MetaTile) tile);
				selectorBack = new Image(ImageLocations.SELECTOR, false, Image.FILTER_NEAREST);
				toShow = new LinkedList<String>();
				wg = new WordGenerator();
				if (ownerName.equals(localPlayerName)) {
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
				render(g, inFront, tileWidth, tileHeight / 2);
			} else {
				action = 0;
			}
		} else {
			// nothing to show, off edge of map
		}
	}

	public void changeSelection(int i) {
		System.out.println(toShow);
		if (i < 0) {
			action = (action - 1) % toShow.size();
			if(action < 0){
				action = toShow.size() - Math.abs(action);
			}
		} else {
			action = (action + 1) % toShow.size();
		}
		System.out.println(action);
	}

	public String getSelected() {
		return toShow.get(action);
	}
	
	public void setAction(int action) {
		this.action = action;
	}

	private void render(Graphics g, Location inFront, float width, float height) {
		float x = inFront.coords.x * width;
		float y = inFront.coords.y * height;

		float characterMod = ((float) toShow.get(action).length()) / 4;
		selectorBack.draw(x + width / 2 - (width * characterMod) / 2, y, width * characterMod, height);
		wg.drawCenter(g, toShow.get(action), x + width / 2, y + height / 2, true, 5 / width);

	}
}
