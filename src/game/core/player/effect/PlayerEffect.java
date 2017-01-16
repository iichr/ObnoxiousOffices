package game.core.player.effect;

import game.core.Updateable;
import game.core.player.Player;

/**
 * Created by samtebbs on 15/01/2017.
 */
public abstract class PlayerEffect implements Updateable<Player> {

    protected final int duration;
    protected int elapsed;
    protected boolean expired;

    protected PlayerEffect(int duration) {
        this.duration = duration;
    }

    public void update(Player player) {
        if(!expired) {
            if (elapsed++ >= duration) expired = true;
            else updatePlayer(player);
        }
    }

    protected abstract void updatePlayer(Player player);

    public int getDuration() {
        return duration;
    }

    public int getElapsed() {
        return elapsed;
    }

    public boolean ended() {
        return expired;
    }
}
