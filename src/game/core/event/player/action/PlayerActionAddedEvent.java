package game.core.event.player.action;

import game.core.player.action.PlayerAction;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerActionAddedEvent extends PlayerActionEvent {

    public PlayerActionAddedEvent(PlayerAction action, String playerName) {
        super(playerName, action);
    }
}
