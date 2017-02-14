package game.core.event;

/**
 * Created by samtebbs on 10/02/2017.
 */
public class CreateAIPlayerRequest extends Event {

    public final Object serverListener;

    public CreateAIPlayerRequest(Object serverListener) {
        this.serverListener = serverListener;
    }
    
}
