package game.core.player.action;

import game.core.player.Player;

/**
 * Created by samtebbs on 16/01/2017.
 */
public abstract class TimedPlayerAction extends PlayerAction {

    protected int counter = 0, counterMax;

    public TimedPlayerAction(Player player) {
        super(player);
    }

    @Override
    public void update() {
        timedUpdate();
        incrementCounter();
        if (ended()) stop();
    }

    protected abstract void timedUpdate();

    @Override
    public void start() {
        startCounter(getDuration());
    }

    protected abstract int getDuration();

    @Override
    public void cancel() {
        stop();
    }

    private void stop() {
        stopCounter();
    }

    @Override
    public boolean cancelable() {
        return true;
    }

    @Override
    public boolean ended() {
        return counterEnded();
    }
    protected void incrementCounter() {
        counter++;
    }

    protected void startCounter(int i) {
        counter = 0;
        counterMax = i;
    }

    protected void stopCounter() {
        counter = counterMax;
    }

    protected boolean counterEnded() {
        return counter >= counterMax;
    }
}
