package game.core.world;

import game.core.minigame.MiniGameHangman;
import game.core.player.Player;
import game.core.player.PlayerTest;
import game.core.world.tile.type.TileType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 23/03/2017.
 */
class WorldTest {

    public static final int numPlayers = 2;
    World world = World.load(Paths.get("data/office " + numPlayers + "Player.level"), numPlayers);

    WorldTest() throws IOException {
    }

    @Test
    void removePlayer() {
        wrapped(PlayerTest.player.name, world::hasPlayer, s -> world.addPlayer(PlayerTest.player), world::removePlayer);
    }

    @Test
    void startMiniGame() {
        wrapped(new MiniGameHangman("bob"), world::hasMiniGame, world::removeMiniGame);
    }

    @Test
    void getSpawnPoint() {
        assertTrue(IntStream.range(0, numPlayers).mapToObj(world::getSpawnPoint).map(Location::getTile).map(t -> t.type).allMatch(t -> t.equals(TileType.CHAIR)));
    }

    @Test
    void getPlayer() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void getPlayers() {
    }

    @Test
    void getTile() {
    }

    @Test
    void setTile() {
    }

    @Test
    void addTile() {
    }

    @Test
    void checkBounds() {
    }

    @Test
    void getTiles() {
    }

    @Test
    void playerAt() {
    }

    @Test
    void getMiniGame() {
    }

    @Test
    void getNeighbours() {
    }

}