package game.core.sync;

import game.core.chat.Chat;
import game.core.chat.ChatMessage;
import game.core.event.chat.ChatMessageReceivedEvent;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.event.minigame.MiniGameStartedEvent;
import game.core.event.minigame.MiniGameStatChangedEvent;
import game.core.event.minigame.MiniGameVarChangedEvent;
import game.core.event.player.*;
import game.core.event.player.action.PlayerActionAddedEvent;
import game.core.event.player.action.PlayerActionEndedEvent;
import game.core.event.player.effect.PlayerEffectAddedEvent;
import game.core.event.player.effect.PlayerEffectElapsedUpdate;
import game.core.event.player.effect.PlayerEffectEndedEvent;
import game.core.event.tile.TileChangedEvent;
import game.core.event.tile.TileMetadataUpdatedEvent;
import game.core.minigame.MiniGame;
import game.core.minigame.MiniGameHangman;
import game.core.minigame.MiniGamePong;
import game.core.player.Player;
import game.core.player.PlayerStatus;
import game.core.player.PlayerTest;
import game.core.player.action.PlayerAction;
import game.core.player.action.PlayerActionDrink;
import game.core.player.action.PlayerActionHackTimed;
import game.core.player.effect.PlayerEffect;
import game.core.player.effect.PlayerEffectSleeping;
import game.core.player.state.PlayerState;
import game.core.util.Coordinates;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.metadata.ComputerMetadata;
import game.core.world.tile.type.TileType;
import game.core.world.tile.type.TileTypeComputer;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.Consumer;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 27/03/2017.
 */
class ClientSyncTest {

    World world = World.load(Paths.get("data/office2Player.level"), 2);
    Coordinates coords = new Coordinates(0, 0, 0);
    Player player = PlayerTest.player;

    ClientSyncTest() throws IOException {
        ClientSync.init();
        world.addPlayer(player);
        Player.localPlayerName = player.name;
    }

    @Test
    void onTileMetadataUpdate() {
        Coordinates pc = world.getTiles(TileTypeComputer.class).get(0).location.coords;
        trigger(pc, c -> new TileMetadataUpdatedEvent(c, ComputerMetadata.ON_FIRE, true), ClientSync::onTileMetadataUpdate, c -> TileTypeComputer.getOnFire((MetaTile) world.getTile(pc)));
    }

    @Test
    void onPlayerQuit() {
        trigger(player.name, PlayerQuitEvent::new, ClientSync::onPlayerQuit, p -> !world.hasPlayer(p));
    }

    @Test
    void onPlayerJoined() {
        trigger(player, PlayerJoinedEvent::new, ClientSync::onPlayerJoined, p -> world.hasPlayer(p.name));
    }

    @Test
    void onMiniGameVarChanged() {
        MiniGame g = (MiniGame.localMiniGame = new MiniGamePong(player.name, "adam"));
        trigger("var", v -> new MiniGameVarChangedEvent(player.name, v, "hi"), ClientSync::onMiniGameVarChanged, v -> g.getVar(v).equals("hi"));
    }

    @Test
    void onPlayerStateAdded() {
        trigger(PlayerState.sitting, s -> new PlayerStateAddedEvent(player.name, s), ClientSync::onPlayerStateAdded, player.status::hasState);
    }

    @Test
    void onPlayerStateRemoved() {
        trigger(PlayerState.sitting, s -> new PlayerStateRemovedEvent(player.name, s), ClientSync::onPlayerStateRemoved, s -> !player.status.hasState(s));
    }

    @Test
    void onChatMessageReceived() {
        ChatMessage msg = new ChatMessage("hi", "me");
        trigger(msg, m -> new ChatMessageReceivedEvent(m.from, m.message), ClientSync::onChatMessageReceived, m -> Chat.chat.getLatestMessages(1).get(0).equals(m));
    }

    @Test
    void onMiniGameEnded() {
        trigger(new MiniGameHangman(Player.localPlayerName), m -> new MiniGameEndedEvent(m.getPlayers(), "me"), ClientSync::onMiniGameEnded, m -> MiniGame.localMiniGame == null);
    }

    @Test
    void onMiniGameStarted() {
        trigger(new MiniGameHangman(Player.localPlayerName), MiniGameStartedEvent::new, ClientSync::onMiniGameStarted, m -> MiniGame.localMiniGame == m);
    }

    @Test
    void onTileChanged() {
        Tile tile = new Tile(new Location(coords), TileType.CHAIR, Direction.EAST);
        trigger(coords, c -> new TileChangedEvent(c.x, c.y, c.z, tile), ClientSync::onTileChanged, c -> world.getTile(c) == tile);
    }

    @Test
    void onPlayerRotated() {
        trigger(Direction.NORTH, d -> new PlayerRotatedEvent(d, player.name), ClientSync::onPlayerRotated, d -> player.getFacing() == d);
    }

    @Test
    void onPlayerMoved() {
        trigger(coords, c -> new PlayerMovedEvent(c, player.name), ClientSync::onPlayerMoved, c -> player.getLocation().coords.equals(c));
    }

    @Test
    void onPlayerAttributeChanged() {
        trigger(PlayerStatus.PlayerAttribute.FATIGUE, a -> new PlayerAttributeChangedEvent(0.73, player.name, a), ClientSync::onPlayerAttributeChanged, a -> player.status.getAttribute(a) == 0.73);
    }

    @Test
    void onPlayerProgressUpdate() {
        trigger(33, p -> new PlayerProgressUpdateEvent(p, player.name), ClientSync::onPlayerProgressUpdate, p -> player.getProgress() == p);
    }

    @Test
    void onPlayerEffectEnded() {
        PlayerEffect effect = new PlayerEffectSleeping(100, player);
        trigger(effect, e -> new PlayerEffectEndedEvent(e, player.name), ClientSync::onPlayerEffectEnded, e -> !player.status.hasEffect(e.getClass()));
    }

    @Test
    void onPlayerEffectAdded() {
        PlayerEffect effect = new PlayerEffectSleeping(100, player);
        trigger(effect, e -> new PlayerEffectAddedEvent(e, player.name), ClientSync::onPlayerEffectAdded, e -> player.status.hasEffect(e.getClass()));
    }

    @Test
    void onPlayerActionEnded() {
        PlayerAction action = new PlayerActionDrink(player);
        trigger(action, a -> new PlayerActionEndedEvent(a, player.name), ClientSync::onPlayerActionEnded, a -> !player.status.hasAction(a.getClass()));
    }

    @Test
    void onPlayerActionAdded() {
        PlayerAction action = new PlayerActionDrink(player);
        trigger(action, a -> new PlayerActionAddedEvent(a, player.name), ClientSync::onPlayerActionAdded, a -> player.status.hasAction(a.getClass()));
    }

    @Test
    void actOnPlayer() {
        Consumer<Player> meme = p -> {};
        ClientSync.actOnPlayer(player.name, addLambda(meme));
        assertCalled(meme);
    }

}