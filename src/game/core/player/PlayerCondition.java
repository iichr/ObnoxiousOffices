package game.core.player;

import game.core.Updateable;

/**
 * Created by samtebbs on 13/03/2017.
 */
public interface PlayerCondition extends Updateable {

    public boolean allowsInteraction();
    public boolean cancelsOnMove();

}
