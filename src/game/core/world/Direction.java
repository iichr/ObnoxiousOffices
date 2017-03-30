package game.core.world;

import java.io.Serializable;

/**
 * Created by samtebbs on 20/01/2017.
 */
public enum Direction implements Serializable {

    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

    public final int xAdd, yAdd;

    Direction(int xAdd, int yAdd) {
        this.xAdd = xAdd;
        this.yAdd = yAdd;
    }

    /**
     * This direction to the right
     * @return new direction
     */
    public Direction right() {
        int i = ordinal() + 1;
        if(i >= values().length) i = 0;
        return values()[i];
    }

    /**
     * This direction to the left
     * @return new direction
     */
    public Direction left() {
        int i = ordinal() - 1;
        if(i < 0) i = values().length - 1;
        return values()[i];
    }

    /**
     * This direction opposite to this one
     * @return new direction
     */
    public Direction opposite() {
        return left().left();
    }

}
