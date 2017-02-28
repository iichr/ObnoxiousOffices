package game.core.event.player;

/**
 * Created by samtebbs on 26/02/2017.
 */
public class PlayerQuitEvent extends PlayerEvent {
    public PlayerQuitEvent(String playerName) {
        super(playerName);
    }
}
