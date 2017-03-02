package game.util;

import game.core.world.tile.type.TileType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by samtebbs on 01/03/2017.
 */
public class Sets {
    public static <T> Set<T> asSet(T... ts) {
        return new HashSet<T>(Arrays.asList(ts));
    }
}
