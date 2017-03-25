package game.core.world;

import game.core.minigame.MiniGameHangman;
import game.core.player.Player;
import game.core.player.PlayerTest;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import game.core.world.tile.type.TileTypeComputer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 23/03/2017.
 */
class WorldTest {

    public static final int numPlayers = 2;
    World world = World.load(Paths.get("data/office" + numPlayers + "Player.level"), numPlayers);

    WorldTest() throws IOException {
    }

    @Test
    void removePlayer() {
        wrapped(PlayerTest.player.name, world::hasPlayer, s -> world.addPlayer(PlayerTest.player), world::removePlayer);
    }

    @Test
    void startMiniGame() {
        wrapped(new MiniGameHangman("bob"), world::hasMiniGame, world::startMiniGame, world::removeMiniGame);
    }

    @Test
    void getSpawnPoint() {
        assertTrue(IntStream.range(0, numPlayers).mapToObj(world::getSpawnPoint).map(Location::getTile).map(t -> t.type).allMatch(t -> t.equals(TileType.CHAIR)));
    }

    @Test
    void getPlayer() {
        wrapped(PlayerTest.player, p -> world.getPlayer(PlayerTest.player.name) == p, world::addPlayer, world::removePlayer);
    }

    @Test
    void addPlayer() {
        getPlayer();
    }

    @Test
    void getPlayers() {
        getPlayer();
    }

    @Test
    void getTile() {
        Location loc = new Location(0, 0);
        assertTrue(world.getTile(loc.coords).type.equals(TileType.FLOOR));
    }

    @Test
    void setTile() {
        Location loc = new Location(1, 0);
        Tile t = loc.getTile();
        wrapped(new Location(1, 0), l -> world.getTile(l.coords).type.equals(TileType.CHAIR), l -> world.setTile(l.coords, new Tile(l, TileType.CHAIR, Direction.SOUTH)), l -> world.setTile(l.coords, t));
    }

    @Test
    void addTile() {
        Location loc = new Location(1, 0);
        Tile t = loc.getTile();
        wrapped(new Location(1, 0), l -> world.getTile(l.coords).type.equals(TileType.CHAIR), l -> world.addTile(new Tile(l, TileType.CHAIR, Direction.SOUTH)), l -> world.setTile(l.coords, t));
    }

    @Test
    void checkBounds() {
        assertTrue(new Location(0, 0).checkBounds());
        assertFalse(new Location(world.xSize + 1, world.ySize + 1).checkBounds());
    }

    @Test
    void getTiles() {
        int numComputers = (int) world.getTiles(TileTypeComputer.class).stream().filter(t -> t.type.equals(TileType.COMPUTER)).count();
        assertTrue(numComputers == numPlayers);
    }

    @Test
    void playerAt() {
        wrapped(PlayerTest.player, p -> world.playerAt(p.getLocation()), world::addPlayer, world::removePlayer);
    }

    @Test
    void getMiniGame() {
        wrapped(new MiniGameHangman(PlayerTest.player.name), m -> world.getMiniGame(PlayerTest.player.name) != null, world::startMiniGame, world::removeMiniGame);
    }

    @Test
    void getNeighbours() {
        List<Tile> neighbours = world.getNeighbours(new Location(3, 3).getTile());
        assertTrue(neighbours.size() == 4);
    }

}