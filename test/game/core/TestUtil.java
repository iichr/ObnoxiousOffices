package game.core;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Predicate;

import game.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by samtebbs on 21/03/2017.
 */
public class TestUtil {

    private static List<Object> lambdas = new ArrayList<>();

    /**
     * Asserts false on the condition, eecutes action1, asserts true on the condition, excutes action2 then asserts false on the condition.
     * @param val Value to pass to the condition and actions
     * @param condition The condition to be false, true then false
     * @param action1 The action to run after the first (false) assertion
     * @param action2 The action to run after the second (true) assertion
     * @param <T> The type of 'val'
     */
    public static <T> void wrapped(T val, Predicate<T> condition, Consumer<T> action1, Consumer<T> action2) {
        assertFalse(condition.test(val));
        action1.accept(val);
        assertTrue(condition.test(val));
        action2.accept(val);
        assertFalse(condition.test(val));
    }

    /**
     * Asserts false on the condition, eecutes action and asserts true on the condition
     * @param condition The condition to be false then true
     * @param action The action to run after the first (false) assertion
     * @param <T> The type of 'val'
     */
    public static <T> void wrapped(BooleanSupplier condition, Runnable action) {
        assertFalse(condition.getAsBoolean());
        action.run();
        assertTrue(condition.getAsBoolean());
    }

    /**
     * Asserts false on the condition, eecutes action1 and asserts true on the condition
     * @param val Value to pass to the condition and action
     * @param condition The condition to be false then true
     * @param action The action to run after the first (false) assertion
     * @param <T> The type of 'val'
     */
    public static <T> void wrapped(T val, Predicate<T> condition, Consumer<T> action) {
        assertFalse(condition.test(val));
        action.accept(val);
        assertTrue(condition.test(val));
    }

    /**
     * Add a consumer to be tracked. Used with {@link TestUtil#assertCalledOrdered(Object...)}} and {@link TestUtil#assertCalled(Object)}
     * @param consumer The consumer
     * @param <T> The consumer's parameter type
     * @return A new consumer that tracks the usage of the given one
     */
    public static <T> Consumer<T> addLambda(Consumer<T> consumer) {
        return t -> {
            lambdas.add(consumer);
            consumer.accept(t);
        };
    }

    /**
     * Assert that a lambda has been called (must have been registered with {@link TestUtil#addLambda(Consumer)}
     * @param lambda The lambda to check
     */
    public static void assertCalled(Object lambda) {
        assertTrue(lambdas.contains(lambda));
    }

    /**
     * Constructs a list of pairs formed from the consecutive elements of the given list. The list will be empty if the given list is of size 0 or 1
     * @param list The list from which to construct the new list
     * @param <T> The type of the list's elements
     * @return A list of pairs
     */
    public static <T> List<Pair<T, T>> consecutive(List<T> list) {
        List<Pair<T, T>> pairs = new LinkedList<>();
        list.stream().reduce((a, b) -> {
            pairs.add(new Pair<>(a, b));
            return b;
        });
        return pairs;
    }

    /**
     * Assert that the lambdas were called in the given order (must have been registered with {@link TestUtil#addLambda(Consumer)}
     * @param array The array of lambdas
     */
    public static void assertCalledOrdered(Object... array) {
        List<Pair<Object, Object>> pairs = consecutive(Arrays.asList(array));
        assertTrue(pairs.stream().allMatch(p -> lambdas.contains(p.getL()) && lambdas.indexOf(p.getL()) < lambdas.indexOf(p.getR())));
    }

}
