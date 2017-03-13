package game.core;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 16/01/2017.
 */
public interface Updateable {

    public void update();
    public boolean ended();
    public void end();

    public static <T extends Updateable> List<T> updateAll(Collection<T> updateables) {
        updateables.forEach(Updateable::update);
        return updateables.stream().filter(Updateable::ended).collect(Collectors.toList());
    }

}
