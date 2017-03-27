package game.core.util;

import java.io.Serializable;

/**
 * Created by samtebbs on 22/02/2017.
 */
public class Coordinates implements Serializable {

    public final int x, y, z;

    public Coordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Add to these coordinates
     * @param dX the x to add
     * @param dY the y to add
     * @param dZ the z to add
     * @return a new coordinates object with the new values
     */
    public Coordinates add(int dX, int dY, int dZ) {
        return new Coordinates(x + dX, y + dY, z + dZ);
    }

    /**
     * Negates these coordinates
     * @return new coordinates object with new values
     */
    public Coordinates neg() {
        return new Coordinates(-x, -y, -z);
    }

    /**
     * {@link Coordinates#sub(Coordinates)}
     * @param coords
     * @return
     */
    public Coordinates diff(Coordinates coords) {
        return this.sub(coords);
    }

    /**
     * Subtracts the argument's coordinates from this one
     * @param coordinates
     * @return
     */
    public Coordinates sub(Coordinates coordinates) {
        return add(coordinates.neg());
    }

    private Coordinates add(Coordinates coords) {
        return add(coords.x, coords.y, coords.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinates that = (Coordinates) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return z == that.z;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
