package game.core.player;

import game.core.Updateable;
import game.core.world.Direction;
import game.core.world.Location;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class Player implements Updateable {

    public final String name;
    public final PlayerStatus status = new PlayerStatus(this);
    private double progress = 0;
    private Direction facing;
    public Location location;

    public Player(String name, Direction facing, Location location) {
        this.name = name;
        this.facing = facing;
        this.location = location;
    }

    // TODO: move(Direction)

    public void moveForwards() {
        location = location.forward(facing);
    }

    public void moveBackwards() {
        location = location.backward(facing);
    }

    public void rotateLeft() {
        facing = facing.left();
    }

    public void rotateRight() {
        facing = facing.right();
    }

    public void update() {
        status.update(this);
    }

    @Override
    public boolean ended() {
        return false;
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
