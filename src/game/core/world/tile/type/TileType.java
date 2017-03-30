package game.core.world.tile.type;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import game.core.input.InteractionType;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.tile.Tile;

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
            COFFEE_MACHINE = new TileTypeCoffeeMachine(4),
            PLANT = new TileTypeDecoration(5),
            SOFA = new TileTypeSofa(6),
            FISH = new TileTypeFish(7),
            WALL = new TileTypeWall(8),
            WALL_CORNER = new TileTypeWallCorner(9),
            DOOR = new TileTypeDoor(10);

    static {
        addTileType("computer", COMPUTER);
        addTileType("desk", DESK);
        addTileType("floor", FLOOR);
        addTileType("chair", CHAIR);
        addTileType("coffee", COFFEE_MACHINE);
        addTileType("plant", PLANT);
        addTileType("sofa", SOFA);
        addTileType("fish", FISH);
        addTileType("wall", WALL);
        addTileType("corner", WALL_CORNER);
        addTileType("door", DOOR);
    }

    protected TileType(int id) {
        this.id = id;
    }

    /**
     * Add a tile alias
     * @param levelSymbol the alias
     * @param type the tile type
     * @return true if existed else false
     */
    public static boolean addTileType(String levelSymbol, TileType type) {
        if(types.containsKey(levelSymbol)) return true;
        types.put(levelSymbol, type);
        return false;
    }

    /**
     * All tiles that should be placed in the world upon spawning a tile of this type
     * @param location the location
     * @param facing the facing
     * @return the list of all tiles
     */
    public Collection<Tile> getTiles(Location location, Direction facing) {
        return Collections.singletonList(new Tile(location, this, facing));
    }

    /**
     * Called when a player walks over a tile
     * @param player the player
     */
    public abstract void onWalkOver(Player player);

    /**
     * Checks if the player can walk over this tile
     * @return true if the can else false
     */
    public abstract boolean canWalkOver();

    /**
     * Called on interaction with a player
     * @param player the player
     * @param tile the tile
     * @param type the interaction type
     */
    public abstract void onInteraction(Player player, Tile tile, InteractionType type);

    /**
     * Get the tile type from a tile alias
     * @param symbol the alias
     * @return the type
     */
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
