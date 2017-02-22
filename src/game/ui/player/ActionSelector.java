package game.ui.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.TileType;
import game.ui.interfaces.ImageLocations;

public class ActionSelector {
	private Animation selector, workSel, hackSel;
	private Animation[] animLoop;

	private int action;

	public static final int WORK = 0;
	public static final int HACK = 1;
	private final int duration = 100;

	public ActionSelector(){
		action = 0;
	}
	
	public void updateSelector(World world, String localPlayerName, float tileWidth, float tileHeight) throws SlickException{
		Player localPlayer = world.getPlayer(localPlayerName);
		Direction facing = localPlayer.getFacing();
		Location inFront = localPlayer.getLocation().forward(facing);
		if (inFront.checkBounds()) {
			TileType actionTile = inFront.getTile().type;

			if (actionTile.equals(TileType.COMPUTER)) {
				workSel = addAnimation(new Image(ImageLocations.WORK_SELECTOR, false, Image.FILTER_NEAREST));
				hackSel = addAnimation(new Image(ImageLocations.HACK_SELECTOR, false, Image.FILTER_NEAREST));
				animLoop = new Animation[] { workSel, hackSel };
				selector = animLoop[action];

				render(inFront, tileWidth, tileHeight / 2);
			} else {
				// nothing to show
			}
		} else {
			// nothing to show, off edge of map
		}
	}

	public void changeSelection(int i) {
		if(i < 0){
			action = (action - 1) % animLoop.length;
			action = Math.abs(action);
		}else{
			action = (action + 1) % animLoop.length;
		}
		System.out.println(action);
	}

	public int getAction() {
		return action;
	}

	private void render(Location inFront, float width, float height) {
		float x = inFront.x * width;
		float y = inFront.y * height;
		selector.draw(x, y, width, height);
	}

	private Animation addAnimation(Image i) {
		Image[] animImages = new Image[] { i, i };
		return new Animation(animImages, duration, false);
	}
}
