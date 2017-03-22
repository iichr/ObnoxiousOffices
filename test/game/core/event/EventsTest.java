package game.core.event;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 22/03/2017.
 */
class EventsTest {

    @Test
    void trigger() {
        Consumer<GameFullEvent> c = x -> {};
        Events.on(GameFullEvent.class, addLambda(c));
        Events.trigger(new GameFullEvent());
        assertCalled(c);
    }

    @Test
    void on() {
        trigger();
    }

    @Test
    void triggerPriority() {
        Consumer<GameFullEvent> low = e -> {};
        Consumer<GameFullEvent> high = e -> { Object o = e; };
        Events.on(Events.EventPriority.LOW, GameFullEvent.class, addLambda(low));
        Events.on(Events.EventPriority.HIGH, GameFullEvent.class, addLambda(high));
        Events.trigger(new GameFullEvent());
        assertCalledOrdered(high, low);
    }

    @Test
    void trigger1() {
        triggerPriority();
    }

}