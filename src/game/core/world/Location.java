package game.core.world;

import game.core.world.tile.Tile;

/**
 * Created by samtebbs on 20/01/2017.
 */
public class Location {

    public final int x, y, z;
    public final World world;

    public Location(int x, int y, int z, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Location(int x, int y, World world) {
        this(x, y, 0, world);
    }

    public Location forward(Direction facing) {
        return new Location(x + facing.xAdd, y + facing.yAdd, z, world);
    }

    public Location backward(Direction facing) {
        facing = facing.opposite();
        return new Location(x + facing.xAdd, y + facing.yAdd, z, world);
    }

    public void setTile(Tile tile) {
        world.setTile(x, y, z, tile);
    }

    public Tile getTile() {
        return world.getTile(x, y, z);
    }

    public boolean checkBounds() {
        return world.checkBounds(x, y, z);
    }

}
