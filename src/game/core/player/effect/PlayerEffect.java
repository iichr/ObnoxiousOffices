package game.core.player.effect;

import game.core.Updateable;
import game.core.player.Player;

import java.io.Serializable;

/**
 * Created by samtebbs on 15/01/2017.
 */
public abstract class PlayerEffect implements Updateable, Serializable {

    protected final int duration;
    protected int elapsed;
    protected boolean expired;
    public final Player player;

    protected PlayerEffect(int duration, Player player) {
        this.duration = duration;
        this.player = player;
    }

    public void update() {
        if(!expired) if (elapsed++ >= duration) expired = true;
    }

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
