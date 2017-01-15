package game.core.player.effect;

import game.core.player.Player;

/**
 * Created by samtebbs on 15/01/2017.
 */
public abstract class PlayerEffect {

    protected final int duration;
    protected int elapsed;
    protected boolean expired;

    protected PlayerEffect(int duration) {
        this.duration = duration;
    }

    public final boolean update(Player player) {
        if(elapsed++ >= duration) expired = true;
        else update2(player);
        return expired;
    }

    protected abstract void update2(Player player);

    public int getDuration() {
        return duration;
    }

    public int getElapsed() {
        return elapsed;
    }
}
