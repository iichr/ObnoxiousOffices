package game.core.player.action;

import game.core.player.Player;
import game.core.player.PlayerCondition;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by samtebbs on 16/01/2017.
 */

public abstract class PlayerAction implements PlayerCondition, Serializable {

    public final Player player;
    public boolean forced = false;

    public PlayerAction(Player player) {
        this.player = player;
    }

    public boolean allowsMove() {
        return !forced;
    }

    public int getMaxRepetitions(Random rand) {
        return Integer.MAX_VALUE;
    }

    public void onMaxRepetitions() {

    }

    public abstract void update();

    /**
     * Start the action
     */
    public abstract void start();

    /**
     * Cancel the action
     */
    public abstract void cancel();

    @Override
    public boolean cancelsOnMove() {
        return true;
    }

    @Override
    public boolean allowsInteraction() {
        return false;
    }

}
