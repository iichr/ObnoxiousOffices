package game.core.event;

import game.core.player.Player;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class PlayerCreatedEvent extends Event {

    public final Player localPlayer;

    public PlayerCreatedEvent(Player localPlayer) {
        this.localPlayer = localPlayer;
    }
}
