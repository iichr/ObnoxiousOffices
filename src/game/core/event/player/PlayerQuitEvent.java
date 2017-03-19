package game.core.event.player;

import game.core.player.Player;
import game.core.world.World;

/**
 * Created by samtebbs on 26/02/2017.
 */
public class PlayerQuitEvent extends PlayerEvent {

    public final Player player;

    public PlayerQuitEvent(String playerName) {
        super(playerName);
        player = World.world.getPlayer(playerName);
    }
}
