package game.ui;

import java.util.Set;

import org.newdawn.slick.Graphics;

import game.core.player.Player;
import game.core.world.World;
import game.ui.interfaces.Vals;

public class PlayerInfo {
	private Set<Player> players;
	private World world;

	private float tileWidth;
	private float tileHeight;

	public PlayerInfo(World world, float tileWidth, float tileHeight) {
		this.world = world;
		players = world.getPlayers();
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
	}

	public void render(Graphics g) {
		for (Player p : players) {
			float offset = (p.name.length()*9 - tileWidth)/2;
			float playerX = p.getLocation().x * tileWidth;
			float playerY = (p.getLocation().y + 1) * (tileHeight / 2);
			g.drawString(p.name, (playerX - offset), playerY - 25);

		}
	}

}
