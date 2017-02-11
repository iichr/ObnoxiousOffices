package game.ui;

import java.awt.Shape;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

import game.core.player.Player;
import game.core.world.World;
import game.core.world.tile.TileType;

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
	//TODO
	/*
	 * 
	 * 
	 * 
	 * */
	public void render(Graphics g) {
		if(isActive){
			g.draw(new Rectangle(x,y, 50, 50));
		}
		

	}
	public void update(){
		x = player.getLocation().x;
		y = player.getLocation().y;
		//TileType type = player.getLocation().world.getTile(x, y, 0).type;
		//if(type == TileType.COMPUTER){
			
		//}
	}

}
