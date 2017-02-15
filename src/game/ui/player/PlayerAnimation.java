package game.ui.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.world.Direction;
import game.ui.interfaces.SpriteLocations;

public class PlayerAnimation {
	private Animation moveNorth, moveSouth, moveEast, moveWest, move;

	public PlayerAnimation(int colour, Direction initialDirection) throws SlickException {
		Image[] n = new Image[8];
		Image[] e = new Image[1];
		Image[] s = new Image[1];
		Image[] w = new Image[1];

		switch (colour) {
		case 0:
			n[0] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK1, false, Image.FILTER_NEAREST);
			n[1] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK2, false, Image.FILTER_NEAREST);
			n[2] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK3, false, Image.FILTER_NEAREST);
			n[3] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK4, false, Image.FILTER_NEAREST);
			n[4] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK5, false, Image.FILTER_NEAREST);
			n[5] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK6, false, Image.FILTER_NEAREST);
			n[6] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK7, false, Image.FILTER_NEAREST);
			n[7] = new Image(SpriteLocations.PLAYER_BLONDE_NORTH_WALK8, false, Image.FILTER_NEAREST);
			e[0] = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_EAST, false, Image.FILTER_NEAREST);
			s[0] = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			w[0] = new Image(SpriteLocations.PLAYER_BLONDE_STANDING_WEST, false, Image.FILTER_NEAREST);
			break;
		case 1:
			n[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[1] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[2] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[3] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[4] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[5] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[6] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[7] = new Image(SpriteLocations.PLAYER_DARK_STANDING_NORTH, false, Image.FILTER_NEAREST);

			e[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_EAST, false, Image.FILTER_NEAREST);
			s[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			w[0] = new Image(SpriteLocations.PLAYER_DARK_STANDING_WEST, false, Image.FILTER_NEAREST);
			break;
		case 2:
			n[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[1] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[2] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[3] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[4] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[5] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[6] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[7] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_NORTH, false, Image.FILTER_NEAREST);

			e[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_EAST, false, Image.FILTER_NEAREST);
			s[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			w[0] = new Image(SpriteLocations.PLAYER_BROWN_STANDING_WEST, false, Image.FILTER_NEAREST);
			break;
		case 3:
			n[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[1] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[2] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[3] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[4] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[5] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[6] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);
			n[7] = new Image(SpriteLocations.PLAYER_PINK_STANDING_NORTH, false, Image.FILTER_NEAREST);

			e[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_EAST, false, Image.FILTER_NEAREST);
			s[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_SOUTH, false, Image.FILTER_NEAREST);
			w[0] = new Image(SpriteLocations.PLAYER_PINK_STANDING_WEST, false, Image.FILTER_NEAREST);
			break;
		}

		createAnimation(n, e, s, w, initialDirection);
	}

	private void createAnimation(Image[] northAnimations, Image[] eastAnimations, Image[] southAnimations,
			Image[] westAnimations, Direction initialDirection) {
		int duration = 100;

		moveNorth = new Animation(northAnimations, duration, false);
		moveEast = new Animation(eastAnimations, duration, false);
		moveSouth = new Animation(southAnimations, duration, false);
		moveWest = new Animation(westAnimations, duration, false);
		
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
}
