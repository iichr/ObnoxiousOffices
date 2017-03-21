package game.core.player.action;

import game.core.player.Player;
import game.util.Time;

/**
 * Created by samtebbs on 21/03/2017.
 */
public class PlayerActionHackTimed extends TimedPlayerAction {
    public final Player target;

    public PlayerActionHackTimed(Player player, Player target) {
        super(player);
        this.target = target;
    }

    @Override
    public void end() {
        super.end();
        target.removeProgress();
    }

    @Override
    protected void timedUpdate() {

    }

    @Override
    protected int getDuration() {
        return (int) Time.ticks(10000);
    }
}
