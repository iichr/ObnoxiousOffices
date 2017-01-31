package game.core;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 16/01/2017.
 */
public interface Updateable {

    public void update();
    public boolean ended();

    public static <T extends Updateable> Set<T> updateAll(Set<T> updateables) {
        updateables.forEach(Updateable::update);
        return updateables.stream().filter(Updateable::ended).collect(Collectors.toSet());
    }

}
