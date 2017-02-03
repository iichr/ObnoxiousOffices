package game.core.world;

/**
 * Created by samtebbs on 20/01/2017.
 */
public enum Direction {

    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

    public final int xAdd, yAdd;

    Direction(int xAdd, int yAdd) {
        this.xAdd = xAdd;
        this.yAdd = yAdd;
    }

    public Direction right() {
        int i = ordinal() + 1;
        if(i >= values().length) i = 0;
        return values()[i];
    }

    public Direction left() {
        int i = ordinal() - 1;
        if(i < 0) i = values().length - 1;
        return values()[i];
    }

    public Direction opposite() {
        return left().left();
    }

}
