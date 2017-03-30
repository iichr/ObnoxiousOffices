package game.core.world;

import game.core.util.Coordinates;
import game.core.world.tile.Tile;

import java.io.Serializable;

/**
 * Created by samtebbs on 20/01/2017.
 */
public class Location implements Serializable {

    public final Coordinates coords;

    public Location(int x, int y, int z) {
        this(new Coordinates(x, y, z));
    }

    public Location(int x, int y) {
        this(x, y, 0);
    }

    public Location(Coordinates coordinates) {
        this.coords = coordinates;
    }

    /**
     * Gets the location forwards of this to the given direction
     * @param facing the direction
     * @return new location
     */
    public Location forward(Direction facing) {
        return add(facing.xAdd, facing.yAdd, 0);
    }

    /**
     * Gets the location backwards of this to the given direction
     * @param facing the direction
     * @return new location
     */
    public Location backward(Direction facing) {
        facing = facing.opposite();
        return add(facing.xAdd, facing.yAdd, 0);
    }

    /**
     * Sets the tile at this location
     * @param tile the tile
     */
    public void setTile(Tile tile) {
        World.world.setTile(coords, tile);
    }

    /**
     * Gets the tile at this location
     * @return the tile
     */
    public Tile getTile() {
        return World.world.getTile(coords);
    }

    /**
     * Checks if the location is within the worl'd bounds
     * @return true if it is, else false
     */
    public boolean checkBounds() {
        return World.world.checkBounds(coords);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (coords != null ? !coords.equals(location.coords) : location.coords != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = coords != null ? coords.hashCode() : 0;
        return result;
    }

    /**
     * Gets the distance between this location and the given one
     * @param location
     * @return
     */
    public Location diff(Location location) {
        Coordinates coords2 = coords.diff(location.coords);
        return new Location(coords2);
    }

    /**
     * Adds the parameters to this location's coords
     * @param dX x to add
     * @param dY y to add
     * @param dZ z to add
     * @return
     */
    public Location add(int dX, int dY, int dZ) {
        return new Location(coords.add(dX, dY, dZ));
    }

    /**
     * Negates this location's coordinates
     * @return the new location
     */
    public Location neg() {
        return new Location(coords.neg());
    }

}
