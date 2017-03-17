package game.core.player.action;

import game.core.player.Player;
import game.util.Time;

/**
 * Created by samtebbs on 16/03/2017.
 */
public class PlayerActionWorkTimed extends TimedPlayerAction {
    public PlayerActionWorkTimed(Player player) {
        super(player);
    }

    @Override
    protected void timedUpdate() {
        player.setProgress(player.getProgress() + player.getProgressMultiplier());
    }

    @Override
    protected int getDuration() {
        return (int) Time.ticks(5000);
    }
}
