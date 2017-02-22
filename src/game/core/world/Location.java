package game.core.world;

import game.core.util.Coordinates;
import game.core.world.tile.Tile;

import java.io.Serializable;

/**
 * Created by samtebbs on 20/01/2017.
 */
public class Location implements Serializable {

    public final Coordinates coords;
    public final World world;

    public Location(int x, int y, int z, World world) {
        this(new Coordinates(x, y, z), world);
    }

    public Location(int x, int y, World world) {
        this(x, y, 0, world);
    }

    public Location(Coordinates coordinates, World world) {
        this.coords = coordinates;
        this.world = world;
    }

    public Location forward(Direction facing) {
        return add(facing.xAdd, facing.yAdd, 0);
    }

    public Location backward(Direction facing) {
        facing = facing.opposite();
        return add(facing.xAdd, facing.yAdd, 0);
    }

    public void setTile(Tile tile) {
        world.setTile(coords, tile);
    }

    public Tile getTile() {
        return world.getTile(coords);
    }

    public boolean checkBounds() {
        return world.checkBounds(coords);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (coords != null ? !coords.equals(location.coords) : location.coords != null) return false;
        return world != null ? world.equals(location.world) : location.world == null;
    }

    @Override
    public int hashCode() {
        int result = coords != null ? coords.hashCode() : 0;
        result = 31 * result + (world != null ? world.hashCode() : 0);
        return result;
    }

    public Location diff(Location location) {
        Coordinates coords2 = coords.diff(location.coords);
        return new Location(coords2, world);
    }

    public Location add(int dX, int dY, int dZ) {
        return new Location(coords.add(dX, dY, dZ), world);
    }

    public Location neg() {
        return new Location(coords.neg(), world);
    }

}
