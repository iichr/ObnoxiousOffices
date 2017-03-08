package game.core.player.action;

import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.player.PlayerStatus;
import game.util.Time;

/**
 * Created by samtebbs on 22/02/2017.
 */
public class PlayerActionSleep extends TimedPlayerAction {

    public PlayerActionSleep(Player player) {
        super(player);
        player.status.addState(PlayerState.sleeping);
    }

    @Override
    protected void timedUpdate() {
        player.status.addToAttribute(PlayerStatus.PlayerAttribute.FATIGUE, -0.1);
    }

    @Override
    protected int getDuration() {
        return (int) Time.ticks(20000);
    }

}
