package game.core.event.player;

import game.core.player.Player;

/**
 * Created by samtebbs on 18/03/2017.
 */
public class PlayerJoinedEvent extends PlayerEvent {
    public final Player player;

    public PlayerJoinedEvent(String playerName, Player player) {
        super(playerName);
        this.player = player;
    }
}
