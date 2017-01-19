package game.core;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 16/01/2017.
 */
public interface Updateable<T> {

    public void update(T t);
    public boolean ended();

    public static <E, T extends Updateable<E>> Set<T> updateAll(Set<T> updateables, E e) {
        updateables.forEach(u -> u.update(e));
        return updateables.stream().filter(Updateable::ended).collect(Collectors.toSet());
    }

}
