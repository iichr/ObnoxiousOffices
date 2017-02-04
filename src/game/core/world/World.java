package game.core.world;

import game.core.Updateable;
import game.core.player.Player;
import game.core.world.tile.Tile;
import game.core.world.tile.TileType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by samtebbs on 19/01/2017.
 */
public class World implements Updateable {

    private final Set<Player> players;
    private final int maxPlayers;
    private final Tile[][][] tiles;
    public final int xSize, ySize, zSize;
    // TODO: Add preset spawn points

    public World(int maxPlayers, int sizeX, int sizeY, int sizeZ) {
        this.tiles = new Tile[sizeX][sizeY][sizeZ];
        this.players = new HashSet<>(maxPlayers);
        this.maxPlayers = maxPlayers;
        zSize = sizeZ;
        ySize = sizeY;
        xSize = sizeX;
    }

    // TODO: getSpawnPoint(int i) = spawnPoints[i]

    public void addPlayer(Player player) {
        if(players.size() < maxPlayers) players.add(player);
    }

    public Set<Player> getPlayers() {
        return players.stream().collect(Collectors.toSet());
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

    public boolean checkBounds(int x, int y, int z) {
        return x < tiles.length && x >= 0 && y < tiles[x].length && y >= 0 && z < tiles[x][y].length && z >= 0;
    }

    public static World load(String[] lines, int maxPlayers) {
        int sizeX = Arrays.stream(lines).mapToInt(String::length).min().orElse(0), sizeY = lines.length, sizeZ = 1;
        World world = new World(maxPlayers, sizeX, sizeY, sizeZ);
        IntStream.range(0, sizeY).forEach(y -> {
            IntStream.range(0, sizeX).forEach(x -> {
                IntStream.range(0, sizeZ).forEach(z -> {
                    char ch = lines[y].charAt(x);
                    Location location = new Location(x, y, z, world);
                    if(TileType.typeExists(ch)) location.setTile(new Tile(location, TileType.getType(ch), Direction.NORTH));
                });
            });
        });
        return world;
    }

    public static World load(Path path, int maxPlayers) throws IOException {
        List<String> l = Files.readAllLines(path);
        return load(l.toArray(new String[l.size()]), maxPlayers);
    }

}
