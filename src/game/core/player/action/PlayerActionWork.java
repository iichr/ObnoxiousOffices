package game.core.player.action;

import game.core.player.Player;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionWork extends TimedPlayerAction {

    public PlayerActionWork(Player player) {
        super(player);
    }

    @Override
    protected void timedUpdate() {
    	System.out.println("adding progress");
        player.addProgress();
    }

    @Override
    protected int getDuration() {
        return 10000;
    }

}
