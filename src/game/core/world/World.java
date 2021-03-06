package game.core.world;

import game.core.Updateable;
import game.core.event.Event;
import game.core.event.Events;
import game.core.event.minigame.MiniGameStartedEvent;
import game.core.event.player.PlayerEvent;
import game.core.event.player.PlayerQuitEvent;
import game.core.minigame.MiniGame;
import game.core.player.Player;
import game.core.sync.ClientSync;
import game.core.sync.ServerSync;
import game.core.util.Coordinates;
import game.core.world.tile.Tile;
import game.core.world.tile.TilePrototype;
import game.core.world.tile.type.TileType;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by samtebbs on 19/01/2017.
 */
public class World implements Updateable, Serializable {

    private final List<Player> players;
    private final int maxPlayers;
    private final Tile[][][] tiles;
    public final int xSize, ySize, zSize;
    private List<Location> spawnPoints = new ArrayList<>();
    private final Set<MiniGame> miniGames = new HashSet<>();

    public static World world;

    public World(int maxPlayers, int sizeX, int sizeY, int sizeZ) {
        this.tiles = new Tile[sizeX][sizeY][sizeZ];
        this.players = new ArrayList<>(maxPlayers);
        this.maxPlayers = maxPlayers;
        zSize = sizeZ;
        ySize = sizeY;
        xSize = sizeX;
    }

    public void removePlayer(Player player) {
        if(!ClientSync.isClient) removeMiniGame(getMiniGame(player.name));
        synchronized (players) {
            players.remove(player);
        }
    }

    public boolean startMiniGame(MiniGame game) {
        boolean noneInMinigame = game.getPlayers().stream().map(this::getMiniGame).allMatch(Objects::isNull);
        if(!ClientSync.isClient && noneInMinigame) {
            miniGames.add(game);
            Events.trigger(new MiniGameStartedEvent(game), true);
            return true;
        } else return false;
    }

    public Location getSpawnPoint(int i) {
        return spawnPoints.get(i);
    }

    public Player getPlayer(String name) {
        return players.stream().filter(p -> p.name.equals(name)).findFirst().orElse(null);
    }

    public void addPlayer(Player player) {
        if(players.size() < maxPlayers) players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public void update() {
        synchronized (players) {
            Updateable.updateAll(players);
        }
        synchronized (miniGames) {
            miniGames.removeAll(Updateable.updateAll(miniGames));
        }
    }

    @Override
    public boolean ended() {
        return false;
    }

    @Override
    public void end() {

    }

    /**
     * Get's a tile at the given x, y and z. May return null.
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Tile getTile(int x, int y, int z) {
        if(checkBounds(x, y, z)) return tiles[x][y][z];
        else return null;
    }

    /**
     * Set the tile at the given x, y, z.
     * @param x
     * @param y
     * @param z
     * @param tile
     */
    public void setTile(int x, int y, int z, Tile tile) {
        if(checkBounds(x, y, z)) tiles[x][y][z] = tile;
    }

    /**
     * Add a tile to the world. Uses the tile's x, y and z.
     * @param tile
     */
    public void addTile(Tile tile) {
        Coordinates loc = tile.location.coords;
        setTile(loc.x, loc.y, loc.z, tile);
    }

    /**
     * @see #addTile(Tile)
     * @param c
     */
    public void addTiles(Collection<Tile> c) {
        c.forEach(this::addTile);
    }

    /**
     * Returns true if the given x, y, z are within the world's bounds, else false
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean checkBounds(int x, int y, int z) {
        return x < tiles.length && x >= 0 && y < tiles[x].length && y >= 0 && z < tiles[x][y].length && z >= 0;
    }

    /**
     * Loads a world object from a String[].
     * @param lines
     * @param maxPlayers
     * @return
     */
    public static World load(String[] lines, int maxPlayers) {
        int i = Arrays.asList(lines).indexOf("###"), i2 = Arrays.asList(lines).lastIndexOf("###");
        if (i < 0) return null;
        String[] aliasLines = Arrays.copyOfRange(lines, i+1, i2), worldLines = Arrays.copyOfRange(lines, 0, i), spawnLines = Arrays.copyOfRange(lines, i2+1, lines.length);

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
        World world = (World.world = new World(maxPlayers, sizeX, tileStrings.length, sizeZ));
        IntStream.range(0, tileStrings.length).forEach(y -> {
            IntStream.range(0, sizeX).forEach(x -> {
                TilePrototype p = aliases.get(tileStrings[y][x]);
                Collection<Tile> tiles = p.type.getTiles(new Location(x, y, 0), p.facing);
                tiles.forEach(t -> {
                    Tile currTile = t.location.getTile();
                    // Ensure that non-multitiles don't overwrite multitiles
                    if(t.isMultiTile() || currTile == null || !currTile.isMultiTile()) world.addTile(t);
                });
            });
        });

        Arrays.stream(spawnLines).forEach(l -> {        	
            try {
				int[] coords = Arrays.stream(l.split(",")).mapToInt(Integer::parseInt).toArray();
				world.addSpawnPoint(new Location(coords[0], coords[1], coords[2]));
			} catch (NumberFormatException e) {
				System.err.println("The level file contains empty lines, please remove them");
			}
        });
        return world;
    }

    private void addSpawnPoint(Location location) {
        spawnPoints.add(location);
    }

    public static World load(Path path, int maxPlayers) throws IOException {
        List<String> l = Files.readAllLines(path);
        return load(l.toArray(new String[l.size()]), maxPlayers);
    }

    public void setTile(Coordinates coords, Tile tile) {
        this.setTile(coords.x, coords.y, coords.z, tile);
    }

    public Tile getTile(Coordinates coords) {
        return getTile(coords.x, coords.y, coords.z);
    }

    public boolean checkBounds(Coordinates coords) {
        return checkBounds(coords.x, coords.y, coords.z);
    }

    public <T extends TileType> List<Tile> getTiles(Class<T> tileTypeClass) {
        List<Tile> result = new LinkedList<>();
        for (int x = 0; x < tiles.length; x++)
            for (int y = 0; y < tiles[x].length; y++)
                for (int z = 0; z < tiles[x][y].length; z++) {
                    Tile tile = getTile(x, y, z);
                    if (tile != null && tileTypeClass.isInstance(tile.type)) result.add(tile);
                }
        return result;
    }

    public boolean playerAt(Location location) {
        return players.stream().map(Player::getLocation).anyMatch(l -> l.equals(location));
    }
    
    public int getMaxPlayers(){
    	return maxPlayers;
    }

	public void removePlayer(int playerNumber) {
		players.remove(playerNumber);
		
	}

    public MiniGame getMiniGame(String playerName) {
        return miniGames.stream().filter(m -> m.hasPlayer(playerName)).findFirst().orElse(null);
    }

    public List<Tile> getNeighbours(Tile tile) {
        Location loc = tile.location;
        List<Location> locs = Arrays.stream(Direction.values()).map(loc::forward).collect(Collectors.toList());
        return locs.stream().filter(Location::checkBounds).map(Location::getTile).collect(Collectors.toList());
    }

    public boolean hasPlayer(String name) {
        return players.stream().anyMatch(p -> p.name.equals(name));
    }

    public void removePlayer(String name) {
        if(hasPlayer(name)) removePlayer(getPlayer(name));
    }

    public void removeMiniGame(MiniGame minigame) {
        synchronized (miniGames) {
            if (minigame != null) {
                minigame.end();
                miniGames.remove(minigame);
            }
        }
    }

    public boolean hasMiniGame(MiniGame game) {
        return miniGames.contains(game);
    }
}
