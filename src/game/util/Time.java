package game.util;

/**
 * Created by samtebbs on 03/03/2017.
 */
public class Time {

    public static final long UPDATE_RATE = 100;

    public static long ticks(long millis) {
        return millis / UPDATE_RATE;
    }
}
