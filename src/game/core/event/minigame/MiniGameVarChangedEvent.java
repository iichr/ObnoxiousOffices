package game.core.event.minigame;

import game.core.event.Event;
import game.core.event.player.PlayerEvent;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameVarChangedEvent extends PlayerEvent {
    public final Object val;
    public final String var;

    public MiniGameVarChangedEvent(String playerName, String var, Object val) {
        super(playerName);
        this.var = var;
        this.val = val;
    }
}
