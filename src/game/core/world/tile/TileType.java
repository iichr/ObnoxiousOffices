package game.core.world.tile;

import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by samtebbs on 20/01/2017.
 */
public abstract class TileType implements Serializable {

    public static final HashMap<String, TileType> types = new HashMap<>();
    public final int id;

    public static final TileType COMPUTER = new TileTypeComputer(0),
            DESK = new TileTypeDesk(1),
            FLOOR = new TileTypeFloor(2),
            CHAIR = new TileTypeChair(3),
            COFFEE_MACHINE = new TileTypeCoffeeMachine(4), PLANT = new TileTypeDecoration(5), SOFA = new TileTypeSofa(6), FISH = new TileTypeFish(7);

    static {
        addTileType("computer", COMPUTER);
        addTileType("desk", DESK);
        addTileType("floor", FLOOR);
        addTileType("chair", CHAIR);
        addTileType("coffee", COFFEE_MACHINE);
        addTileType("plant", PLANT);
        addTileType("sofa", SOFA);
        addTileType("fish", FISH);
    }

    protected TileType(int id) {
        this.id = id;
    }

    public static boolean addTileType(String levelSymbol, TileType type) {
        if(types.containsKey(levelSymbol)) return true;
        types.put(levelSymbol, type);
        return false;
    }

    public Collection<Tile> getTiles(Location location, Direction facing) {
        return Arrays.asList(new Tile(location, this, facing));
    }

    public abstract void onWalkOver(Player player);

    public abstract boolean canWalkOver();

    public abstract void onInteraction(Player player);

    public static boolean typeExists(char ch) {
        return types.containsKey(ch);
    }

    public static TileType getType(String symbol) {
        return types.get(symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileType tileType = (TileType) o;

        return id == tileType.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
