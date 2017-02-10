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

    private static final HashMap<Class<? extends Event>, List<Consumer>> subscribers = new HashMap<>();

    public static <T extends Event> void trigger(T event) {
        if(subscribers.containsKey(event.getClass())) {
            List<Consumer> consumers = subscribers.get(event.getClass());
            consumers.forEach(c -> invoke(c, event));
        }
    }

    private static <T extends Event> void invoke(Consumer c, T event) {
        Thread thread = new Thread(() -> {
            c.accept(event);
        });
    }

    public static <T extends Event> void on(Class<? extends Event> eventClass, Consumer<T> method) {
        if(subscribers.containsKey(eventClass)) subscribers.get(eventClass).add(method);
        else subscribers.put(eventClass, Arrays.asList(method));
    }

}