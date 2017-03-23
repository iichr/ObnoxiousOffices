package game.core.player.action;

import game.core.player.Player;
import game.util.Time;

import java.util.Random;

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
        if(player.workSucceeded(new Random())) target.removeProgress((int) (25 * player.getProgressMultiplier()));
    }

    @Override
    protected void timedUpdate() {}

    @Override
    protected int getDuration() {
        return (int) Time.ticks(10000);
    }
}
