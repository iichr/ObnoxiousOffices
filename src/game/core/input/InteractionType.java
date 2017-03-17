package game.core.input;

import java.io.Serializable;

/**
 * Created by samtebbs on 01/03/2017.
 */
public class InteractionType implements Serializable {
    public final InteractionType WORK = new InteractionType(), SIT = new InteractionType(), OTHER = new InteractionType();

    public static class InteractionTypeHack extends InteractionType {
        public final String target;

        public InteractionTypeHack(String target) {
            this.target = target;
        }
    }

}
