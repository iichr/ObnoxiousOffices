package game.core.event;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class ConnectionAttemptEvent extends Event {

    public final String name;

    public ConnectionAttemptEvent(String name) {
        this.name = name;
    }
    
}
