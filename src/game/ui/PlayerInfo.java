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

	public PlayerInfo(World world) {
		this.world = world;
		players = world.getPlayers();
		tileWidth = (float) Vals.SCREEN_WIDTH / world.xSize;
	}

	public void render(Graphics g) {
		for (Player p : players) {
			g.drawString(p.name, (p.getLocation().x) * tileWidth,
					((float) (p.getLocation().y + 0.5) / (world.ySize + 2)) * Vals.SCREEN_HEIGHT);

		}

	}

}
