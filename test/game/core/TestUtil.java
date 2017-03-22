package game.core;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 21/03/2017.
 */
public class TestUtil {

    public static <T> void wrappedTest(T val, Predicate<T> condition, Consumer<T> action1, Consumer<T> action2) {
        assertFalse(condition.test(val));
        action1.accept(val);
        assertTrue(condition.test(val));
        action2.accept(val);
        assertFalse(condition.test(val));
    }

}
