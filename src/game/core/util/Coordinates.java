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

    public Coordinates add(int dX, int dY, int dZ) {
        return new Coordinates(x + dX, y + dY, z + dZ);
    }

    public Coordinates neg() {
        return new Coordinates(-x, -y, -z);
    }

    public Coordinates diff(Coordinates coords) {
        return coords.sub(this);
    }

    private Coordinates sub(Coordinates coordinates) {
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
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
