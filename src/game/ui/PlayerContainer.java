package game.ui;

import java.util.Set;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.player.Player;
import game.core.world.World;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

/**
 * The player status container to appear on the left of the screen upon
 * invocation with the TAB key.
 * 
 * @author iichr
 *
 */
public class PlayerContainer {

	private static final long serialVersionUID = 7035477320220180349L;

	// player names
	private String[] playerNames;
	private Image[] playerAvatars;
	private double[] playerProgress;
	private Image progressBarBase;
	private Image progressBarFull;
	private float x;
	private float y;

	private World world;
	private Set<Player> players;

	// String padding
	private final int xPad = 10;
	private final int yPad = 20;

	/**
	 * A constructor for the player status container
	 * 
	 * @param world
	 * @param localPlayerName
	 * @param x
	 *            X coord
	 * @param y
	 *            Y coord
	 * @throws SlickException
	 */
	public PlayerContainer(World world, String localPlayerName, float x, float y) throws SlickException {
		this.world = world;
		players = world.getPlayers();
		this.x = x;
		this.y = y;

		progressBarBase = new Image(ImageLocations.PROGRESS_BAR_BASE, false, Image.FILTER_NEAREST);
		progressBarFull = new Image(ImageLocations.PROGRESS_BAR_FULL, false, Image.FILTER_NEAREST);

		int size = players.size();
		playerNames = new String[size];
		playerAvatars = new Image[size];
		playerProgress = new double[size];

		int i = 1;
		for (Player p : players) {
			if (!p.name.equals(localPlayerName)) {
				playerNames[i] = p.name;
				playerAvatars[i] = new Image(ImageLocations.TEMP_AVATAR, false, Image.FILTER_NEAREST);
				playerProgress[i] = p.getProgress();
				i++;
			} else {
				playerNames[0] = p.name;
				playerAvatars[0] = new Image(ImageLocations.TEMP_AVATAR, false, Image.FILTER_NEAREST);
				playerProgress[0] = p.getProgress();
			}
		}
	}

	// TODO
	// supply with player avatars
	// Player one (current player should be bigger)
	// supply with progress
	// supply with player names

	/**
	 * Render the status container.
	 * 
	 * @param g
	 *            The graphics context
	 * @param invoked
	 *            Whether it has been invoked by the user or not. !! Used to
	 *            update as well.
	 */
	public void render(Graphics g) {
		final float xSize = Vals.SCREEN_WIDTH / 30;
		final float ySize = Vals.SCREEN_HEIGHT / 15;

			for (int i = 0; i < playerNames.length; i++) {
				float xPos = this.x + xSize / 2;
				float yPos = this.y + ySize * (i + 1);
				float progress = (float) (playerProgress[i] / 100);
				playerAvatars[i].draw(xPos, yPos, xSize, ySize);
				g.drawString(playerNames[i], xPos + xPad + xSize, yPos);

				float cornerX = xPos + xPad + xSize;
				float cornerY = yPos + yPad;
				progressBarBase.draw(cornerX, cornerY, xSize * 4, ySize - yPad);
				progressBarFull.draw(cornerX, cornerY, cornerX + xSize * 4 * progress, cornerY + ySize - yPad, 0, 0,
						progressBarBase.getWidth() * progress, progressBarBase.getHeight());
			}
	}
	
	
}
