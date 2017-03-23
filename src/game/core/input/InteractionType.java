package game.core.input;

import java.io.Serializable;

/**
 * Created by samtebbs on 01/03/2017.
 */
public class InteractionType implements Serializable {

    public static class InteractionTypeHack extends InteractionType {
        public final String target;

        public InteractionTypeHack(String target) {
            this.target = target;
        }
    }

    public static class InteractionTypeWork extends InteractionType {}
    public static class InteractionTypeSit extends InteractionType {}
    public static class InteractionTypeOther extends InteractionType {}

}
