package game.core.event.minigame;

import game.core.event.Event;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameStatChangedEvent extends Event {

    public final String player, stat;
    public final Object val;

    public MiniGameStatChangedEvent(String player, String stat, Object val) {
        this.player = player;
        this.stat = stat;
        this.val = val;
    }

}
