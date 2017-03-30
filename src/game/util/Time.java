package game.util;

/**
 * Created by samtebbs on 03/03/2017.
 */
public class Time {

    public static final long UPDATE_RATE = 100;

    /**
     * Get the number of game ticks for a number of milliseconds
     * @param millis
     * @return
     */
    public static long ticks(long millis) {
        return millis / UPDATE_RATE;
    }
}
