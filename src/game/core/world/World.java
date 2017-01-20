package game.core.world;

import game.core.Updateable;
import game.core.player.Player;
import game.core.world.tile.Tile;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by samtebbs on 19/01/2017.
 */
public class World implements Updateable {

    private final Set<Player> players;
    private final int maxPlayers;
    private final Tile[][][] tiles;

    public World(int maxPlayers, int sizeX, int sizeY, int sizeZ) {
        this.tiles = new Tile[sizeX][sizeY][sizeZ];
        this.players = new HashSet<>(maxPlayers);
        this.maxPlayers = maxPlayers;
    }

    public void addPlayer(Player player) {
        if(players.size() < maxPlayers) players.add(player);
    }

    @Override
    public void update() {
        Updateable.updateAll(players);
    }

    @Override
    public boolean ended() {
        return false;
    }

    public Tile getTile(int x, int y, int z) {
        if(checkBounds(x, y, z)) return tiles[x][y][z];
        else return null;
    }

    public void setTile(int x, int y, int z, Tile tile) {
        if(checkBounds(x, y, z)) tiles[x][y][z] = tile;
    }

    private boolean checkBounds(int x, int y, int z) {
        return x < tiles.length && x >= 0 && y < tiles[x].length && y >= 0 && z < tiles[x][y].length && z >= 0;
    }

}
