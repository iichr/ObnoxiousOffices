package game.core.event;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameVarChangedEvent extends Event {
    public final int val;
    public final String var;

    public MiniGameVarChangedEvent(String var, int val) {
        this.var = var;
        this.val = val;
    }
}
