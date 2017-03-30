package game.core.sync;

import com.sun.security.ntlm.Server;
import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeInteraction;
import game.core.input.InputTypeMovement;
import game.core.input.InteractionType;
import game.core.input.MovementType;
import game.core.player.Player;
import game.core.player.PlayerTest;
import game.core.player.state.PlayerState;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by samtebbs on 27/03/2017.
 */
class ServerSyncTest {

    World world = World.load(Paths.get("data/office2Player.level"), 2);
    Player player = PlayerTest.player;

    ServerSyncTest() throws IOException {
        world.addPlayer(PlayerTest.player);
    }

    @Test
    void onGameStarted() {
        ServerSync.onGameStarted(new GameStartedEvent(world));
        assertTrue(world.getPlayers().stream().allMatch(p -> p.status.hasState(PlayerState.sitting)));
    }

    @Test
    void onPlayerInput() {
        Location loc = PlayerTest.player.getLocation();
        ServerSync.onPlayerInput(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_DOWN), PlayerTest.player.name));
        assertEquals(loc.forward(Direction.SOUTH), player.getLocation());
        ServerSync.onPlayerInput(new PlayerInputEvent(new InputTypeInteraction(new InteractionType.InteractionTypeOther()), player.name));
    }

}