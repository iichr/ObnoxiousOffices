package game.util;

import game.core.world.tile.type.TileType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by samtebbs on 01/03/2017.
 */
public class Sets {
    /**
     * Create a set of the given items
     * @param ts the items
     * @param <T> the type of the items
     * @return the new set
     */
    public static <T> Set<T> asSet(T... ts) {
        return new HashSet<T>(Arrays.asList(ts));
    }
}
