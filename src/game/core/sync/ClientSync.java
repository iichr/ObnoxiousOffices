package game.core.sync;

import game.core.chat.Chat;
import game.core.event.*;
import game.core.event.chat.ChatMessageReceivedEvent;
import game.core.event.player.action.PlayerActionAddedEvent;
import game.core.event.player.action.PlayerActionEndedEvent;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.event.minigame.MiniGameStartedEvent;
import game.core.event.player.*;
import game.core.event.player.effect.PlayerEffectAddedEvent;
import game.core.event.player.effect.PlayerEffectEndedEvent;
import game.core.event.tile.TileChangedEvent;
import game.core.minigame.MiniGame;
import game.core.player.Player;
import game.core.world.Location;
import game.core.world.World;

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
        Events.on(PlayerAttributeChangedEvent.class, ClientSync::onPlayerAttributeChanged);
        Events.on(PlayerProgressUpdateEvent.class, ClientSync::onPlayerProgressUpdate);
        Events.on(PlayerMovedEvent.class, ClientSync::onPlayerMoved);
        Events.on(PlayerRotatedEvent.class, ClientSync::onPlayerRotated);
        Events.on(PlayerStateAddedEvent.class, ClientSync::onPlayerStateAdded);
        Events.on(PlayerStateRemovedEvent.class, ClientSync::onPlayerStateRemoved);

        Events.on(TileChangedEvent.class, ClientSync::onTileChanged);

        Events.on(MiniGameStartedEvent.class, ClientSync::onMiniGameStarted);
        Events.on(MiniGameEndedEvent.class, ClientSync::onMiniGameEnded);

        Events.on(ChatMessageReceivedEvent.class, ClientSync::onChatMessageReceived);
    }

    private static void onPlayerStateAdded(PlayerStateAddedEvent event) {
        getPlayer(event.playerName).status.addState(event.state);
    }

    private static void onPlayerStateRemoved(PlayerStateRemovedEvent event) {
        getPlayer(event.playerName).status.removeState(event.state);
    }

    private static void onChatMessageReceived(ChatMessageReceivedEvent event) {
        Chat.chat.addMessage(event.toChatMessage());
    }

    private static void onMiniGameEnded(MiniGameEndedEvent event) {
        System.out.printf("Mini game ended, %s won!%n", event.victor);
        MiniGame.localMiniGame = null;
    }

    private static void onMiniGameStarted(MiniGameStartedEvent event) {
        System.out.printf("Mini game started with %s%n", event.game.getPlayers());
        MiniGame.localMiniGame = event.game;
    }

    private static void onTileChanged(TileChangedEvent event) {
        getWorld().setTile(event.x, event.y, event.z, event.tile);
    }

    private static World getWorld() {
        return World.world;
    }

    private static void onPlayerRotated(PlayerRotatedEvent event) {
    	System.out.println("managing player rotated event");
        getPlayer(event.playerName).setFacing(event.newFacing);
    }

    private static void onPlayerMoved(PlayerMovedEvent event) {
    	System.out.println("managing player moved event");
        Player player = getPlayer(event.playerName);
        player.setLocation(new Location(event.coords, player.getLocation().world));
    }

    private static void onPlayerAttributeChanged(PlayerAttributeChangedEvent event) {
        getPlayer(event.playerName).status.setAttribute(event.attribute, event.newVal);
    }
    
    private static void onPlayerProgressUpdate(PlayerProgressUpdateEvent event) {
        getPlayer(event.playerName).setProgress(event.change);
    }

    private static void onPlayerEffectEnded(PlayerEffectEndedEvent event) {
        getPlayer(event.playerName).status.removeEffect(event.effect);
    }

    private static void onPlayerEffectAdded(PlayerEffectAddedEvent event) {
        getPlayer(event.playerName).status.addEffect(event.effect);
    }

    private static void onPlayerActionEnded(PlayerActionEndedEvent event) {
        getPlayer(event.playerName).status.removeAction(event.action);
    }

    private static void onPlayerActionAdded(PlayerActionAddedEvent event) {
        getPlayer(event.playerName).status.addAction(event.action);
    }

    private static Player getPlayer(String playerName) {
        return getWorld().getPlayer(playerName);
    }

}
