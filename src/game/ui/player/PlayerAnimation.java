package game.ui.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.core.world.Direction;
import game.ui.interfaces.SpriteLocations;

public class PlayerAnimation {
	private Animation moveNorth, moveSouth, moveEast, moveWest, seatedNorth, seatedSouth, sleepNorth, sleepSouth, move;

	public PlayerAnimation(int colour, Direction initialDirection) throws SlickException {
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
			cS[0] = player.getSprite(2, 0);

			sN[0] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_BLONDE_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 1:
			playerSheet = new Image(SpriteLocations.PLAYER_DARK_STANDING, false, Image.FILTER_NEAREST);
			player = new SpriteSheet(playerSheet, 30, 50, 14, 10);

			cN[0] = new Image(SpriteLocations.PLAYER_DARK_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = player.getSprite(2, 0);;

			sN[0] = new Image(SpriteLocations.PLAYER_DARK_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_DARK_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 2:
			playerSheet = new Image(SpriteLocations.PLAYER_BROWN_STANDING, false, Image.FILTER_NEAREST);
			player = new SpriteSheet(playerSheet, 30, 50, 14, 10);

			cN[0] = new Image(SpriteLocations.PLAYER_BROWN_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = player.getSprite(2, 0);;

			sN[0] = new Image(SpriteLocations.PLAYER_BROWN_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_BROWN_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		case 3:
			playerSheet = new Image(SpriteLocations.PLAYER_PINK_STANDING, false, Image.FILTER_NEAREST);
			player = new SpriteSheet(playerSheet, 30, 50, 14, 10);

			cN[0] = new Image(SpriteLocations.PLAYER_PINK_NORTH_SEAT, false, Image.FILTER_NEAREST);
			cS[0] = player.getSprite(2, 0);;

			sN[0] = new Image(SpriteLocations.PLAYER_PINK_NORTH_SLEEP, false, Image.FILTER_NEAREST);
			sS[0] = new Image(SpriteLocations.PLAYER_PINK_SOUTH_SLEEP, false, Image.FILTER_NEAREST);
			break;
		}
		addAnimation(player, initialDirection, cN, cS, sN, sS);
	}

	private void addAnimation(SpriteSheet player, Direction initialDirection, Image[] cN, Image[] cS, Image[] sN, Image[] sS) throws SlickException {
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

		createAnimation(n, e, s, w, cN, cS, sN, sS, initialDirection);
	}

	private void createAnimation(Image[] northAnimations, Image[] eastAnimations, Image[] southAnimations,
			Image[] westAnimations, Image[] seatedNorthAnimations, Image[] seatedSouthAnimations,
			Image[] sleepNorthAnimations, Image[] sleepSouthAnimations, Direction initialDirection) {
		int duration = 100;

		moveNorth = new Animation(northAnimations, duration, true);
		moveEast = new Animation(eastAnimations, duration, true);
		moveSouth = new Animation(southAnimations, duration, true);
		moveWest = new Animation(westAnimations, duration, true);
		seatedNorth = new Animation(seatedNorthAnimations, duration, false);
		seatedSouth = new Animation(seatedSouthAnimations, duration, false);
		sleepNorth = new Animation(sleepNorthAnimations, duration, false);
		sleepSouth = new Animation(sleepSouthAnimations, duration, false);

		turn(initialDirection);
	}

	public void drawPlayer(float x, float y, float width, float height) {
		if (move.equals(moveEast) || move.equals(moveWest)) {
			move.draw(x + 8, y, width - 16, height);
		} else {
			move.draw(x, y, width, height);
		}
	}

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
}
