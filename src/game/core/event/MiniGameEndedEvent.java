package game.core.event;

import java.util.List;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameEndedEvent extends MiniGameEvent {

    private final String victor;

    public MiniGameEndedEvent(List<String> players, String victor) {
        super(players);
        this.victor = victor;
    }
}
