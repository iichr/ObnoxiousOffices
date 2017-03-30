package game.core.sync;

import game.core.chat.Chat;
import game.core.event.*;
import game.core.event.chat.*;
import game.core.event.minigame.*;
import game.core.event.player.action.*;
import game.core.event.player.*;
import game.core.event.player.effect.*;
import game.core.event.tile.*;
import game.core.minigame.MiniGame;
import game.core.player.Player;
import game.core.world.*;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class ClientSync {

    public static boolean isClient = false;

    public static void init() {
        isClient = true;
        Events.on(PlayerActionAddedEvent.class, ClientSync::onPlayerActionAdded);
        Events.on(PlayerActionEndedEvent.class, ClientSync::onPlayerActionEnded);
        Events.on(PlayerEffectAddedEvent.class, ClientSync::onPlayerEffectAdded);
        Events.on(PlayerEffectEndedEvent.class, ClientSync::onPlayerEffectEnded);
        Events.on(PlayerEffectElapsedUpdate.class, ClientSync::onPlayerEffectElapsedUpdate);
        Events.on(PlayerAttributeChangedEvent.class, ClientSync::onPlayerAttributeChanged);
        Events.on(PlayerProgressUpdateEvent.class, ClientSync::onPlayerProgressUpdate);
        Events.on(PlayerMovedEvent.class, ClientSync::onPlayerMoved);
        Events.on(PlayerRotatedEvent.class, ClientSync::onPlayerRotated);
        Events.on(PlayerStateAddedEvent.class, ClientSync::onPlayerStateAdded);
        Events.on(PlayerStateRemovedEvent.class, ClientSync::onPlayerStateRemoved);
        Events.on(PlayerJoinedEvent.class, ClientSync::onPlayerJoined);
        Events.on(PlayerQuitEvent.class, ClientSync::onPlayerQuit);

        Events.on(TileChangedEvent.class, ClientSync::onTileChanged);
        Events.on(TileMetadataUpdatedEvent.class, ClientSync::onTileMetadataUpdate);

        Events.on(MiniGameStartedEvent.class, ClientSync::onMiniGameStarted);
        Events.on(MiniGameEndedEvent.class, ClientSync::onMiniGameEnded);
        Events.on(MiniGameStatChangedEvent.class, ClientSync::onMiniGameStatChanged);
        Events.on(MiniGameVarChangedEvent.class, ClientSync::onMiniGameVarChanged);

        Events.on(ChatMessageReceivedEvent.class, ClientSync::onChatMessageReceived);
    }

    public static void onTileMetadataUpdate(TileMetadataUpdatedEvent event) {
        Tile tile = World.world.getTile(event.tile);
        if(tile instanceof MetaTile) ((MetaTile) tile).metadata.setVar(event.var, event.val);
    }

    public static void onPlayerQuit(PlayerQuitEvent event) {
        World.world.removePlayer(World.world.getPlayer(event.playerName));
    }

    public static void onPlayerJoined(PlayerJoinedEvent event) {
        World.world.addPlayer(event.player);
    }

    public static void onMiniGameVarChanged(MiniGameVarChangedEvent event) {
        if(MiniGame.localMiniGame != null && MiniGame.localMiniGame.isPlaying(event.playerName)) MiniGame.localMiniGame.setVar(event.var, event.val);
    }

    public static void onMiniGameStatChanged(MiniGameStatChangedEvent event) {
        if(MiniGame.localMiniGame != null && MiniGame.localMiniGame.isPlaying(event.player)) {
            MiniGame.localMiniGame.setStat(event.player, event.stat, event.val);
        }
    }

    public static void onPlayerEffectElapsedUpdate(PlayerEffectElapsedUpdate event) {
        actOnPlayer(event.playerName, p-> p.status.getEffects().stream().filter(e -> e.getClass() == event.effectClass).findAny().ifPresent(effect -> effect.setElapsed(event.elapsed)));
    }

    public static void onPlayerStateAdded(PlayerStateAddedEvent event) {
        actOnPlayer(event.playerName, p -> p.status.addState(event.state));
    }

    public static void onPlayerStateRemoved(PlayerStateRemovedEvent event) {
        actOnPlayer(event.playerName, p -> p.status.removeState(event.state));
    }

    public static void onChatMessageReceived(ChatMessageReceivedEvent event) {
        Chat.chat.addMessage(event.toChatMessage());
    }

    public static void onMiniGameEnded(MiniGameEndedEvent event) {
        if(event.isLocal()) MiniGame.localMiniGame = null;
    }

    public static void onMiniGameStarted(MiniGameStartedEvent event) {
        if(event.game.isLocal()) MiniGame.localMiniGame = event.game;
    }

    public static void onTileChanged(TileChangedEvent event) {
        getWorld().setTile(event.x, event.y, event.z, event.tile);
    }

    public static World getWorld() {
        return World.world;
    }

    public static void onPlayerRotated(PlayerRotatedEvent event) {
        actOnPlayer(event.playerName, p -> p.setFacing(event.newFacing));
    }

    public static void onPlayerMoved(PlayerMovedEvent event) {
        actOnPlayer(event.playerName, player -> player.setLocation(new Location(event.coords)));
    }

    public static void onPlayerAttributeChanged(PlayerAttributeChangedEvent event) {
        actOnPlayer(event.playerName, p -> p.status.setAttribute(event.attribute, event.newVal));
    }
    
    public static void onPlayerProgressUpdate(PlayerProgressUpdateEvent event) {
        actOnPlayer(event.playerName, p -> p.setProgress(event.newVal));
    }

    public static void onPlayerEffectEnded(PlayerEffectEndedEvent event) {
        actOnPlayer(event.playerName, p -> p.status.removeEffect(event.effect.getClass()));
    }

    public static void onPlayerEffectAdded(PlayerEffectAddedEvent event) {
        actOnPlayer(event.playerName, p -> p.status.addEffect(event.effect));
    }

    public static void onPlayerActionEnded(PlayerActionEndedEvent event) {
        actOnPlayer(event.playerName, p -> p.status.removeAction(event.action.getClass()));
    }

    public static void onPlayerActionAdded(PlayerActionAddedEvent event) {
        actOnPlayer(event.playerName, p -> p.status.addAction(event.action));
    }

    public static void actOnPlayer(String playerName, Consumer<Player> action) {
        Optional.ofNullable(getWorld().getPlayer(playerName)).ifPresent(action);
    }

}
