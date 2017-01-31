package game.core.world.tile;

import game.core.player.Player;

import java.util.HashMap;

/**
 * Created by samtebbs on 20/01/2017.
 */
public abstract class TileType {

    public static final HashMap<Character, TileType> types = new HashMap<>();

    public static final TileType COMPUTER = new TileTypeComputer(),
            DESK = new TileTypeDesk(),
            FLOOR = new TileTypeFloor(),
            CHAIR = new TileTypeChair(),
            COFFEE_MACHINE = new TileTypeCoffeeMachine(), PLANT = new TileTypeDecoration();

    public static void init() {
        addTileType('c', COMPUTER);
        addTileType('d', DESK);
        addTileType('f', FLOOR);
        addTileType('s', CHAIR);
        addTileType('m', COFFEE_MACHINE);
        addTileType('p', PLANT);
    }

    public static boolean addTileType(char levelSymbol, TileType type) {
        if(types.containsKey(levelSymbol)) return true;
        types.put(levelSymbol, type);
        return false;
    }

    public abstract void onWalkOver(Player player);

    public abstract boolean canWalkOver();

    public abstract void onInteraction(Player player);

    public static boolean typeExists(char ch) {
        return types.containsKey(ch);
    }

    public static TileType getType(char ch) {
        return types.get(ch);
    }
}
