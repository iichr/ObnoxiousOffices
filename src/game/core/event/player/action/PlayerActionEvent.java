package game.core.event.player.action;

import game.core.event.player.PlayerEvent;
import game.core.player.action.PlayerAction;

import java.io.Serializable;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerActionEvent extends PlayerEvent implements Serializable {

    public final PlayerAction action;

    public PlayerActionEvent(String playerName, PlayerAction action) {
        super(playerName);
        this.action = action;
    }
}
