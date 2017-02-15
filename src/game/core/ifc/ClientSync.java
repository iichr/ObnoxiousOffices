package game.core.ifc;

import game.core.event.*;
import game.core.player.Player;
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
        Events.on(PlayerMovedEvent.class, ClientSync::onPlayerMoved);
        Events.on(PlayerRotatedEvent.class, ClientSync::onPlayerRotated);

        Events.on(TileChangedEvent.class, ClientSync::onTileChanged);
    }

    private static void onTileChanged(TileChangedEvent event) {
        getWorld().setTile(event.x, event.y, event.z, event.tile);
    }

    private static World getWorld() {
        return World.world;
    }

    private static void onPlayerRotated(PlayerRotatedEvent event) {
        getPlayer(event.playerName).setFacing(event.newFacing);
    }

    private static void onPlayerMoved(PlayerMovedEvent event) {
        Player player = getPlayer(event.playerName);
        player.setLocation(player.getLocation().add(event.dX, event.dY, event.dZ));
    }

    private static void onPlayerAttributeChanged(PlayerAttributeChangedEvent event) {
        getPlayer(event.playerName).status.setAttribute(event.attribute, event.newVal);
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
