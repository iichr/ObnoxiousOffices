package game.core.player;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class Player {

    public final String name;
    public final PlayerStatus status = new PlayerStatus();
    private double progress = 0;

    public Player(String name) {
        this.name = name;
    }

    public void update() {
        status.update(this);
    }

    public void setProgress(double progress) {
        this.progress += progress;
        if(this.progress >= 100) {
            onProgressDone();
            this.progress = 0;
        }
    }

    private void onProgressDone() {
        // TODO
    }

    public void removeProgress() {

    }

    public void addProgress() {
        double toAdd = 1.0 * getProgressMultiplier();
        setProgress(progress + toAdd);
    }

    private double getProgressMultiplier() {
        return status.getAttribute(PlayerStatus.PlayerAttribute.PRODUCTIVITY);
    }

}
