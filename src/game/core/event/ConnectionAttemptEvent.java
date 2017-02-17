package game.core.event;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class ConnectionAttemptEvent extends Event {

    public final String name, ipAddress;

    public ConnectionAttemptEvent(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }
    
}
