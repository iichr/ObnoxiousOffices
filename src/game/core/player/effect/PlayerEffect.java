package game.core.player.effect;

import game.core.Updateable;
import game.core.event.Events;
import game.core.event.player.effect.PlayerEffectElapsedUpdate;
import game.core.player.Player;
import game.core.player.PlayerCondition;

import java.io.Serializable;

/**
 * Created by samtebbs on 15/01/2017.
 */
public abstract class PlayerEffect implements PlayerCondition, Serializable {

    protected final int duration;
    protected int elapsed;
    protected boolean expired;
    public final Player player;
    int updates = 0;
    public static final int UPDATE_THRESHOLD = 3;

    public PlayerEffect(int duration, Player player) {
        this.duration = duration;
        this.player = player;
    }

    public void update() {
        if(!expired){
            setElapsed(elapsed + 1);
            if (elapsed >= duration) expired = true;
        }
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

    @Override
    public boolean allowsInteraction() {
        return true;
    }

    @Override
    public boolean cancelsOnMove() {
        return false;
    }

    @Override
    public void end() {
        expired = true;
    }

    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
        if(++updates >= UPDATE_THRESHOLD) {
            updates = 0;
            Events.trigger(new PlayerEffectElapsedUpdate(player.name, this, elapsed), true);
        }
    }
}
