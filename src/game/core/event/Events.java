package game.core.event;

import javafx.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

/**
 * Created by samtebbs on 24/02/2016.
 */
public class Events {

    private static final HashMap<Class<? extends Event>, List<Consumer<? extends Event>>> subscribers = new HashMap<>();

    public static <T extends Event> void trigger(T event) {
        // TODO
    }

    public static <T extends Event> void on(Class<? extends Event> eventClass, Consumer<T> method) {
        // TODO
    }

}