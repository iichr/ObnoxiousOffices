package game.core.player.action;

import game.core.Updateable;
import game.core.player.Player;

/**
 * Created by samtebbs on 16/01/2017.
 */
public abstract class PlayerAction implements Updateable {

    public final Player player;

    public PlayerAction(Player player) {
        this.player = player;
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

    /**
     * Returns true if the action can be canceled
     * @return (see above)
     */
    public abstract boolean cancelable();

    /**
     * Returns true if the action has ended
     * @return (see above)
     */
    public abstract boolean ended();

}
