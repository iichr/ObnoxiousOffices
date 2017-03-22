package game.ui.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.core.world.Direction;
import game.ui.interfaces.SpriteLocations;

/**
 * Class that manages player animations
 */
public class PlayerAnimation {
	private Animation moveNorth;
	private Animation moveSouth;
	private Animation moveEast;
	private Animation moveWest;
	private Animation seatedNorth;
	private Animation seatedSouth;
	private Animation sleepNorth;
	private Animation sleepSouth;
	private Animation move;

	/**
	 * Creates a new object of PlayerAnimation and sets up the different
	 * animations for the players
	 * 
	 * @param colour
	 *            The number representing player hair colour
	 * @throws SlickException
	 */
	public PlayerAnimation(int colour) throws SlickException {
		SpriteSheet player = null;

		Image[] cN = new Image[1];
		Image[] cS = new Image[1];
		Image[] sN = new Image[1];
		Image[] sS = new Image[1];

		switch (colour) {
		case 0:
			Image playerSheet = new Image(SpriteLocations.PLAYER_BLONDE_STANDING, false, Image.FILTER_NEAREST);
			player = new SpriteSheet(playerSheet, 30, 50, 34, 10);

			cN[0] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = player.getSprite(2, 2);

			sN[0] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_BLONDE_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 1:
			playerSheet = new Image(SpriteLocations.PLAYER_DARK_STANDING, false, Image.FILTER_NEAREST);
			player = new SpriteSheet(playerSheet, 30, 50, 34, 10);

			cN[0] = new Image(SpriteLocations.PLAYER_DARK_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = player.getSprite(2, 2);
			;

			sN[0] = new Image(SpriteLocations.PLAYER_DARK_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_DARK_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 2:
			playerSheet = new Image(SpriteLocations.PLAYER_BROWN_STANDING, false, Image.FILTER_NEAREST);
			player = new SpriteSheet(playerSheet, 30, 50, 34, 10);

			cN[0] = new Image(SpriteLocations.PLAYER_BROWN_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = player.getSprite(2, 2);
			;

			sN[0] = new Image(SpriteLocations.PLAYER_BROWN_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_BROWN_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 3:
			playerSheet = new Image(SpriteLocations.PLAYER_PINK_STANDING, false, Image.FILTER_NEAREST);
			player = new SpriteSheet(playerSheet, 30, 50, 34, 10);

			cN[0] = new Image(SpriteLocations.PLAYER_PINK_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = player.getSprite(2, 2);
			;

			sN[0] = new Image(SpriteLocations.PLAYER_PINK_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_PINK_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		}
		addAnimation(player, cN, cS, sN, sS);
	}

	/**
	 * Creates an animation from all of the images needed for a player
	 * 
	 * @param player
	 *            The player sprite sheet
	 * @param cN
	 *            The images for a north facing seated player
	 * @param cS
	 *            The images for a south facing seated player
	 * @param sN
	 *            The images for a north facing sleeping player
	 * @param sS
	 *            The images for a south facing sleeping player
	 * @throws SlickException
	 */
	private void addAnimation(SpriteSheet player, Image[] cN, Image[] cS, Image[] sN, Image[] sS)
			throws SlickException {
		int walkLength = 8;
		Image[] n = new Image[walkLength];
		Image[] e = new Image[walkLength];
		Image[] s = new Image[walkLength];
		Image[] w = new Image[walkLength];

		for (int i = 0; i < walkLength; i++) {
			n[i] = player.getSprite(i + 1, 0);
			w[i] = player.getSprite(i, 1);
			s[i] = player.getSprite(i + 1, 2);
			e[i] = player.getSprite(i, 3);
		}

		createAnimation(n, e, s, w, cN, cS, sN, sS);
	}

	/**
	 * Creates animations from all of the player images
	 * 
	 * @param northAnimations
	 *            The images for a north facing standing player
	 * @param eastAnimations
	 *            The images for a east facing standing player
	 * @param southAnimations
	 *            The images for a south facing standing player
	 * @param westAnimations
	 *            The images for a west facing standing player
	 * @param seatedNorthAnimations
	 *            The images for a north facing seated player
	 * @param seatedSouthAnimations
	 *            The images for a south facing seated player
	 * @param sleepNorthAnimations
	 *            The images for a north facing sleeping player
	 * @param sleepSouthAnimations
	 *            The images for a south facing sleeping player
	 */
	private void createAnimation(Image[] northAnimations, Image[] eastAnimations, Image[] southAnimations,
			Image[] westAnimations, Image[] seatedNorthAnimations, Image[] seatedSouthAnimations,
			Image[] sleepNorthAnimations, Image[] sleepSouthAnimations) {
		int duration = 100;

		moveNorth = new Animation(northAnimations, duration, false);
		moveEast = new Animation(eastAnimations, duration, false);
		moveSouth = new Animation(southAnimations, duration, false);
		moveWest = new Animation(westAnimations, duration, false);
		seatedNorth = new Animation(seatedNorthAnimations, duration, false);
		seatedSouth = new Animation(seatedSouthAnimations, duration, false);
		sleepNorth = new Animation(sleepNorthAnimations, duration, false);
		sleepSouth = new Animation(sleepSouthAnimations, duration, false);
	}

	/**
	 * Draws a player with the correct animation
	 * 
	 * @param x
	 *            The x position to draw at
	 * @param y
	 *            The y position to draw at
	 * @param width
	 *            The width to draw the player image
	 * @param height
	 *            The height to draw the player image
	 */
	public void drawPlayer(float x, float y, float width, float height) {
		move.draw(x, y, width, height);
	}

	/**
	 * Changes the player animation according to the player's facing
	 * 
	 * @param facing
	 *            The facing of the player
	 */
	public void turn(Direction facing) {
		switch (facing) {
		case NORTH:
			move = moveNorth;
			break;
		case EAST:
			move = moveEast;
			break;
		case SOUTH:
			move = moveSouth;
			break;
		case WEST:
			move = moveWest;
			break;
		}
	}

	/**
	 * Changes the player animation to seated
	 * 
	 * @param facing
	 *            The facing of the player
	 */
	public void seated(Direction facing) {
		switch (facing) {
		case NORTH:
			move = seatedNorth;
			break;
		case SOUTH:
			move = seatedSouth;
			break;
		}
	}

	/**
	 * Changes the player animation to sleeping
	 * 
	 * @param facing
	 *            The facing of the player
	 */
	public void sleeping(Direction facing) {
		switch (facing) {
		case NORTH:
			move = sleepNorth;
			break;
		case EAST:
			move = sleepNorth;
			break;
		case WEST:
			move = sleepSouth;
			break;
		case SOUTH:
			move = sleepSouth;
			break;
		}
	}

	/**
	 * Moves the animation on to the next frame
	 */
	public void nextFrame() {
		int frame = move.getFrame();
		if (frame + 2 >= move.getFrameCount()) {
			move.setCurrentFrame(0);
		} else {
			move.setCurrentFrame(frame + 2);
		}
	}
}
