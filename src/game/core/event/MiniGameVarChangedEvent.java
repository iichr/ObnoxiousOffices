package game.core.event;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameVarChangedEvent extends Event {
    public final Object val;
    public final String var;

    public MiniGameVarChangedEvent(String var, Object val) {
        this.var = var;
        this.val = val;
    }
}
