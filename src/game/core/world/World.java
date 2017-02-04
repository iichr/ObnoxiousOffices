package game.core.world;

import game.core.Updateable;
import game.core.player.Player;
import game.core.world.tile.Tile;
import game.core.world.tile.TilePrototype;
import game.core.world.tile.TileType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
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

    public void addTile(Tile tile) {
        Location loc = tile.location;
        setTile(loc.x, loc.y, loc.z, tile);
    }

    public void addTiles(Collection<Tile> c) {
        c.forEach(this::addTile);
    }

    public boolean checkBounds(int x, int y, int z) {
        return x < tiles.length && x >= 0 && y < tiles[x].length && y >= 0 && z < tiles[x][y].length && z >= 0;
    }

    public static World load(String[] lines, int maxPlayers) {
        int i = Arrays.asList(lines).indexOf("###");
        if (i < 0) return null;
        String[] aliasLines = Arrays.copyOfRange(lines, i+1, lines.length), worldLines = Arrays.copyOfRange(lines, 0, i);

        // Read tile aliases
        HashMap<String, TilePrototype> aliases = new HashMap<>();
        for(String aliasLine : aliasLines) {
            String[] parts = aliasLine.split("=");
            String name = parts[0].trim();
            String[] prototypeParts = parts[1].split(",");
            Direction direction = prototypeParts.length > 1 ? Direction.valueOf(prototypeParts[1].trim().toUpperCase()) : Direction.NORTH;
            aliases.put(name, new TilePrototype(TileType.getType(prototypeParts[0].trim()), direction));
        }

        // Read world lines
        String[][] tileStrings = new String[worldLines.length][];
        for (int j = 0; j < tileStrings.length; j++) tileStrings[j] = worldLines[j].split(",");
        int sizeX = Arrays.stream(tileStrings).mapToInt(a -> a.length).min().orElse(0), sizeZ = 1;
        World world = new World(maxPlayers, sizeX, tileStrings.length, sizeZ);
        IntStream.range(0, tileStrings.length).forEach(y -> {
            IntStream.range(0, sizeX).forEach(x -> {
                TilePrototype p = aliases.get(tileStrings[y][x]);
                world.addTiles(p.type.getTiles(new Location(x, y, 0, world), p.facing));
            });
        });
        return world;
    }

    public static World load(Path path, int maxPlayers) throws IOException {
        List<String> l = Files.readAllLines(path);
        return load(l.toArray(new String[l.size()]), maxPlayers);
    }

}
