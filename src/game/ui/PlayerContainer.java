package game.ui;

import java.util.Set;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import game.core.player.Player;
import game.core.world.World;

/**
 * The player status container to appear on the left of the screen upon
 * invocation with the TAB key.
 * 
 * @author iichr
 *
 */
public class PlayerContainer extends Rectangle {

	private static final long serialVersionUID = 7035477320220180349L;

	// player names
	private String p1 = "Player 1";
	private String p2 ;
	private String p3 ;
	private String p4 ;
	private String[] others = new String[]{"Player 2","Player 3","Player 4"};
	private int i;

	private Set<Player> players;
	private World world;
	// player avatars
	private Image i1, i2, i3, i4;

	// String padding
	private int pad = 10;

	/**
	 * A constructor for the player status container
	 * 
	 * @param world
	 * @param x
	 *            X coord
	 * @param y
	 *            Y coord
	 * @param width
	 *            Width of container
	 * @param height
	 *            Height of container
	 * @param i1
	 *            Player 1's avatar
	 * @param i2
	 *            Player 2's avatar
	 * @param i3
	 *            Player 3's avatar
	 * @param i4
	 *            Player 4's avatar.
	 */
	public PlayerContainer(World world, float x, float y, float width, float height, Image i1, Image i2, Image i3,
			Image i4) {
		super(x, y, width, height);
		this.i1 = resize(i1);
		this.i2 = resize(i2);
		this.i3 = resize(i3);
		this.i4 = resize(i4);
		this.world = world;
		players = world.getPlayers();
		p1 = Player.localPlayerName;
		if (!players.isEmpty()) {
			for (Player p : players) {
				others[i] = p.name;
				i++;
			}
		}
		p2 = others[0];
		p3 = others[1];
		p4 = others[2];
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
	public void render(Graphics g, boolean invoked) {
		if (invoked) {
			// Player names
			g.drawString(p1, this.x + getImgWidth(i1) + pad, this.y + pad);
			g.drawString(p2, this.x + getImgWidth(i2) + pad, this.y + getImgHeight(i1) + pad);
			g.drawString(p3, this.x + getImgWidth(i3) + pad, this.y + getImgHeight(i1) + getImgHeight(i2) + pad);
			g.drawString(p4, this.x + getImgWidth(i4) + pad,
					this.y + getImgHeight(i1) + getImgHeight(i2) + getImgHeight(i3) + pad);

			// Player avatars
			g.drawImage(i1, this.x, y);
			g.drawImage(i2, this.x, y + i1.getHeight());
			g.drawImage(i3, this.x, y + 2 * i2.getHeight());
			g.drawImage(i4, this.x, y + 3 * i3.getHeight());
		} else {
			// leave empty
			// do not invoke clear!
		}
	}

	/**
	 * Set image height proportional to the rectangle container's size
	 * 
	 * @return Height of a single avatar.
	 */
	int setImgHeight() {
		return (int) (this.height / 4);
	}

	/**
	 * Get the height of a player's avatar.
	 * 
	 * @param img
	 *            The image
	 * @return Height of said image
	 */
	private int getImgHeight(Image img) {
		return img.getHeight();
	}

	/**
	 * Get the width of a player's avatar.
	 * 
	 * @param img
	 *            The image
	 * @return Width of said image
	 */
	private int getImgWidth(Image img) {
		return img.getWidth();
	}

	/**
	 * 
	 * 
	 */
	private Image resize(Image img) {
		return img.getScaledCopy((int) img.getWidth() / 2, setImgHeight());
	}
}
