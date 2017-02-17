package game.ui;

import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import game.core.player.Player;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.Tile;
import game.core.world.tile.TileType;

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
			g.setColor(Color.red);
			Location pLocation = p.getLocation();

			float playerX = pLocation.x * tileWidth;
			float playerY = (pLocation.y + 1) * (tileHeight / 2);
			float offsetX = (g.getFont().getWidth(p.name) - tileWidth) / 2;
			float offsetY = (g.getFont().getHeight(p.name) + 5);
			g.drawString(p.name, (playerX - offsetX), (playerY - offsetY));

			if (p.name.equals(Player.localPlayerName)) {
				Location inFront = pLocation.forward(p.getFacing());

				if (inFront.checkBounds()) {
					Tile t = inFront.getTile();
					g.setColor(Color.black);
					if (t.type.equals(TileType.COMPUTER)) {
						g.drawString("WORK", (inFront.x * tileWidth), (inFront.y + 1) * (tileHeight / 2));
					} else if (t.type.equals(TileType.COFFEE_MACHINE)) {
						g.drawString("DRINK", (inFront.x * tileWidth), (inFront.y + 1) * (tileHeight / 2));
					} else if (t.type.equals(TileType.SOFA)) {
						g.drawString("NAP", (inFront.x * tileWidth), (inFront.y + 1) * (tileHeight / 2));
					}
				}
			}
		}
	}
}
