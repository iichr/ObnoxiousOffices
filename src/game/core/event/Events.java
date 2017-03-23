package game.core.event;

import game.core.sync.ClientSync;
import org.lwjgl.Sys;

import java.util.*;
import java.util.function.Consumer;

/**
 * Created by samtebbs on 24/02/2016.
 */
public class Events {

    public enum EventPriority {
        HIGH, MEDIUM, LOW
    }

    private static final Map<Class<? extends Event>, Map<EventPriority, List<Consumer>>> subscribers = new HashMap<>();

    public static <T extends Event> void trigger(T event) {
        if(subscribers.containsKey(event.getClass())) {
            Map<EventPriority, List<Consumer>> consumers = subscribers.get(event.getClass());
            Arrays.stream(EventPriority.values()).map(consumers::get).forEach(l -> l.forEach(c -> invoke(c, event)));
        }
    }

    private static <T extends Event> void invoke(Consumer<T> c, T event) {
        c.accept(event);
    }

    public static <T extends Event> void on(EventPriority priority, Class<T> eventClass, Consumer<T> method) {
        if(subscribers.containsKey(eventClass)) subscribers.get(eventClass).get(priority).add(method);
        else {
            Map<EventPriority, List<Consumer>> map = new HashMap<EventPriority, List<Consumer>>() {{
                Arrays.stream(EventPriority.values()).forEach(p -> put(p, new ArrayList<>()));
            }};
            map.get(priority).add(method);
            subscribers.put(eventClass, map);
        }
    }

    public static <T extends Event> void on(Class<T> eventClass, Consumer<T> method) {
        on(EventPriority.MEDIUM, eventClass, method);
    }

    public static void trigger(Event event, boolean serverSide) {
        if(serverSide ^ ClientSync.isClient) trigger(event);
    }

}