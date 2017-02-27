package game.ui;

import java.util.Set;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.player.Player;
import game.core.player.PlayerStatus.PlayerAttribute;
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
	private Animation[] playerAvatars;
	private double[] playerProgress;
	private double[] playerFatigue;

	private Image progressBarBase;
	private Image progressBarFull;
	private float x;
	private float y;

	private World world;
	private Set<Player> players;

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
		playerAvatars = new Animation[size];
		playerProgress = new double[size];
		playerFatigue = new double[size];

		int i = 1;
		for (Player p : players) {
			Image[] temp = new Image[2];
			if (!p.name.equals(localPlayerName)) {
				// add name
				playerNames[i] = p.name;

				// add image
				temp = setImages(p);
				playerAvatars[i] = new Animation(temp, 50);

				// add progress and fatigue
				playerProgress[i] = p.getProgress();
				playerFatigue[i] = p.status.getAttribute(PlayerAttribute.FATIGUE);
				i++;
			} else {
				// add name
				playerNames[0] = p.name;

				// add image
				temp = setImages(p);
				playerAvatars[0] = new Animation(temp, 50);

				// add progress and fatigue
				playerProgress[0] = p.getProgress();
				playerFatigue[0] = p.status.getAttribute(PlayerAttribute.FATIGUE);
			}
		}
	}

	/**
	 * Render the status container.
	 * 
	 * @param g
	 *            The graphics context
	 **/
	public void render(Graphics g) {
		final float xSize = Vals.SCREEN_WIDTH / 30;
		final float ySize = Vals.SCREEN_HEIGHT / 9;

		for (int i = 0; i < playerNames.length; i++) {
			float xPos = this.x + xSize / 3;
			float yPos = this.y + ySize * (i + 1);
			float progress = (float) (playerProgress[i] / 100);
			float fatigue = (float) (playerFatigue[i] / 1);
			float xPad = xSize/2;
			float yPad = ySize/6;

			// player avatar
			playerAvatars[i].draw(xPos, yPos + ySize/6, 2*ySize /3, 2*ySize /3);
			g.drawString(playerNames[i], xPos + xPad + xSize, yPos);

			// set values
			g.setColor(Color.white);
			float cornerX = xPos + xPad + xSize;
			float cornerY = yPos + yPad;

			// progress bar
			progressBarBase.draw(cornerX, cornerY, xSize * 4, ySize / 3);
			progressBarFull.draw(cornerX, cornerY, cornerX + xSize * 4 * progress, cornerY + ySize / 3, 0, 0,
					progressBarBase.getWidth() * progress, progressBarBase.getHeight());
			// draw label
			String progressString = "PROGRESS";
			float stringWidth = g.getFont().getWidth(progressString);
			float stringHeight = g.getFont().getHeight(progressString);
			g.drawString(progressString, cornerX + xSize * 2 - stringWidth / 2, cornerY + ySize / 6 - stringHeight / 2);

			// fatigue bar
			progressBarBase.draw(cornerX, cornerY + ySize / 3, xSize * 4, ySize / 3);
			progressBarFull.draw(cornerX, cornerY + ySize / 3, cornerX + xSize * 4 * fatigue, cornerY + 2 * ySize / 3,
					0, 0, progressBarBase.getWidth() * fatigue, progressBarBase.getHeight(), Color.red);
			// draw label
			String fatigueString = "FATIGUE";
			stringWidth = g.getFont().getWidth(fatigueString);
			stringHeight = g.getFont().getHeight(fatigueString);
			g.drawString(fatigueString, cornerX + xSize * 2 - stringWidth / 2, cornerY + ySize / 2 - stringHeight / 2);

			// set colour back to black
			g.setColor(Color.black);
		}
	}

	public void toggleSleep(Player p) {
		Animation a = playerAvatars[p.getHair()];
		a.setCurrentFrame((a.getFrame() + 1) % a.getFrameCount());
	}

	private Image[] setImages(Player p) throws SlickException {
		Image[] temp = new Image[2];
		switch (p.getHair()) {
		case 0:
			temp[0] = new Image(ImageLocations.BLONDE_HEAD, false, Image.FILTER_NEAREST);
			temp[1] = new Image(ImageLocations.BLONDE_HEAD_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 1:
			temp[0] = new Image(ImageLocations.BROWN_HEAD, false, Image.FILTER_NEAREST);
			temp[1] = new Image(ImageLocations.BROWN_HEAD_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 2:
			temp[0] = new Image(ImageLocations.DARK_HEAD, false, Image.FILTER_NEAREST);
			temp[1] = new Image(ImageLocations.DARK_HEAD_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 3:
			temp[0] = new Image(ImageLocations.PINK_HEAD, false, Image.FILTER_NEAREST);
			temp[1] = new Image(ImageLocations.PINK_HEAD_SLEEP, false, Image.FILTER_NEAREST);
			break;
		}
		;
		return temp;
	}

}
