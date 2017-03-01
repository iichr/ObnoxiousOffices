package game.core.event;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class CreateAIPlayerRequest extends Event {

    public final Object serverListener;
    public final int aiNumber;

    public CreateAIPlayerRequest(Object serverListener, int aiNumber) {
        this.serverListener = serverListener;
        this.aiNumber = aiNumber;
    }
    
}
