package game.core.player;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.GameStartedEvent;

import java.io.Serializable;

/**
 * Created by samtebbs on 28/02/2017.
 */

public abstract class PlayerState implements PlayerCondition, Serializable {

    public static final PlayerState sitting = new PlayerStateSitting(), sleeping = new PlayerStateSleeping();

    public abstract boolean cancelsOnMove();

    @Override
    public boolean allowsInteraction() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean ended() {
        return false;
    }

    @Override
    public void end() {

    }

    private static class PlayerStateSitting extends game.core.player.PlayerState {

        @Override
        public boolean cancelsOnMove() {
            return true;
        }

        @Override
        public void onStart(Player player) {

        }

        @Override
        public void onEnd(Player player) {

        }
    }

    private static class PlayerStateSleeping extends PlayerState {

        @Override
        public boolean cancelsOnMove() {
            return true;
        }

        @Override
        public void onStart(Player player) {

        }

        @Override
        public void onEnd(Player player) {

        }
    }

    public abstract void onStart(Player player);
    public abstract void onEnd(Player player);

}
