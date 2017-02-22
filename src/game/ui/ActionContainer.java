package game.ui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Rectangle;

import game.core.player.Player;
import game.core.util.Coordinates;
import game.core.world.World;

public class ActionContainer {
	private Player player;
	private int x, y;
	private World world;
	private boolean isActive = false;
	private ShapeFill fill;

	public ActionContainer() {

	}

	public void activate() {
		isActive = !isActive;
	}

	// TODO
	/*
	 * 
	 * 
	 * 
	 * */
	public void render(Graphics g) {
		if (isActive) {
			g.draw(new Rectangle(x, y, 50, 50));
		}

	}

	public void update() {
		Coordinates coords = player.getLocation().coords;
		x = coords.x;
		y = coords.y;
		// TileType type = player.getLocation().world.getTile(x, y, 0).type;
		// if(type == TileType.COMPUTER){

		// }
	}

}
