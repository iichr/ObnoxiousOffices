package game.core.player.state;

import game.core.player.Player;
import game.core.player.PlayerCondition;

import java.io.Serializable;

/**
 * Created by samtebbs on 28/02/2017.
 */
public abstract class PlayerState implements PlayerCondition, Serializable {

    public static final PlayerState sitting = new PlayerStateSitting(), sleeping = new PlayerStateSleeping();

    public abstract boolean cancelsOnMove();

    @Override
    public boolean allowsInteraction() {
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean ended() {
        return false;
    }

    @Override
    public void end() {

    }

    public abstract void onStart(Player player);
    public abstract void onEnd(Player player);

}
