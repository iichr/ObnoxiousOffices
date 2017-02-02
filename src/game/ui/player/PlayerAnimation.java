package game.ui.player;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import game.core.world.Direction;

public class PlayerAnimation {
	private Animation moveNorth, moveSouth, moveEast, moveWest, move;

	public PlayerAnimation(Image northPlayer, Image southPlayer, Image eastPlayer, Image westPlayer,
			Direction initialDirection) {
		createAnimation(northPlayer, southPlayer, eastPlayer, westPlayer, initialDirection);
	}

	private void createAnimation(Image northPlayer, Image southPlayer, Image eastPlayer, Image westPlayer,
			Direction initialDirection) {
		int[] duration = { 200, 200 };

		Image[] north = { northPlayer, northPlayer };
		Image[] south = { southPlayer, southPlayer };
		Image[] east = { eastPlayer, eastPlayer };
		Image[] west = { westPlayer, westPlayer };

		moveNorth = new Animation(north, duration, false);
		moveSouth = new Animation(south, duration, false);
		moveEast = new Animation(east, duration, false);
		moveWest = new Animation(west, duration, false);

		switch (initialDirection) {
		case NORTH:
			move = moveNorth;
			break;
		case SOUTH:
			move = moveSouth;
			break;
		case EAST:
			move = moveEast;
			break;
		case WEST:
			move = moveWest;
			break;
		}
	}

	public void drawPlayer(float x, float y, float width, float height) {
		move.draw(x, y, width, height);
	}

	public void turnNorth() {
		move = moveNorth;
	}

	public void turnSouth() {
		move = moveSouth;
	}

	public void turnEast() {
		move = moveEast;
	}

	public void turnWest() {
		move = moveWest;
	}
}
