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

    /**
     * Check if the action allows a player to move
     * @return true if it does, else false
     */
    public boolean allowsMove() {
        return !forced;
    }

    /**
     * Get the number of repetitions before {@link PlayerAction#onMaxRepetitions()} is called
     * @param rand a new Random object
     * @return the number of max repetitions
     */
    public int getMaxRepetitions(Random rand) {
        return Integer.MAX_VALUE;
    }

    /**
     * A method called on max repetitions
     */
    public void onMaxRepetitions() {

    }

    /**
     * Update the action
     */
    public abstract void update();

    /**
     * Start the action
     */
    public abstract void start();

    /**
     * Cancel the action
     */
    public abstract void cancel();

    /**
     * Checks if the action cancels when the player moves
     * @return true if it does else false
     */
    @Override
    public boolean cancelsOnMove() {
        return true;
    }

    /**
     * Check if the action allows a player to interact with objects
     * @return true if it does, else false
     */
    @Override
    public boolean allowsInteraction() {
        return false;
    }

}
