package game.core.player;

import game.core.Updateable;

/**
 * Created by samtebbs on 16/01/2017.
 */
public abstract class PlayerAction implements Updateable<Player> {

    public final Player player;

    public PlayerAction(Player player) {
        this.player = player;
    }

    public abstract void update(Player _);

    public abstract void start();

    public abstract void cancel();

    public abstract boolean cancelable();

    public abstract boolean ended();

}
