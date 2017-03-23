package game.core.event.player.action;

import game.core.player.action.PlayerAction;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerActionEndedEvent extends PlayerActionEvent {

    public PlayerActionEndedEvent(PlayerAction action, String playerName) {
        super(playerName, action);
    }
}
