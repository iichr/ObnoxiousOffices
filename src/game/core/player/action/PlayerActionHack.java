package game.core.player.action;

import game.core.player.Player;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionHack extends TimedPlayerAction {

    protected final Player target;

    public PlayerActionHack(Player player, Player target) {
        super(player);
        this.target = target;
    }

    @Override
    protected void update() {
        target.removeProgress();
    }

    @Override
    protected int getDuration() {
        return 5000;
    }
}
