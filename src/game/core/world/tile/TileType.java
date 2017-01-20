package game.core.world.tile;

import game.core.player.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by samtebbs on 20/01/2017.
 */
public abstract class TileType {

    public static final HashMap<Character, TileType> types = new HashMap<>();

    public static boolean addTileType(char levelSymbol, TileType type) {
        if(types.containsKey(levelSymbol)) return true;
        types.put(levelSymbol, type);
        return false;
    }

    public abstract void onWalkOver(Player player);

    public abstract void onInteraction(Player player);
}
