package game.core;

/**
 * Created by samtebbs on 16/01/2017.
 */
public interface Updateable<T> {

    public void update(T t);
    public boolean ended();

}
