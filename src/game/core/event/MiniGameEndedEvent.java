package game.core.event;

import java.util.List;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameEndedEvent extends Event {

    public final String victor;
    public final List<String> players;

    public MiniGameEndedEvent(List<String> players, String victor) {
        this.players = players;
        this.victor = victor;
    }
}
