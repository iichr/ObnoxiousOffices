package game.core.sync;

import game.core.Updateable;

/**
 * Created by samtebbs on 24/02/2017.
 */
public class Updater implements Runnable {

    public final Updateable updateable;
    public final long rateMilliseconds;
    private long lastUpdate = 0;
    private boolean run;

    public Updater(Updateable updateable, long rateMilliseconds, boolean run) {
        this.updateable = updateable;
        this.rateMilliseconds = rateMilliseconds;
        this.run = run;
    }

    @Override
    public void run() {
        while (true) {
            if(run) {
                long time = System.currentTimeMillis();
                if (time - lastUpdate >= rateMilliseconds) {
                    lastUpdate = time;
                    updateable.update();
                }
            }
        }
    }

    public void start() {
        this.run = true;
    }

    public void stop() {
        this.run = false;
    }

    public void reset() {
        this.lastUpdate = 0;
    }

}
