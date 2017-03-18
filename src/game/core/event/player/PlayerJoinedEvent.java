package game.core.event.player;

import game.core.player.Player;

/**
 * Created by samtebbs on 18/03/2017.
 */
public class PlayerJoinedEvent {
    public final Player player;

    public PlayerJoinedEvent(Player player) {
        this.player = player;
    }
}
