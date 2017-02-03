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

    public Direction getFacing() {
        return facing;
    }

    /**
     * Move forwards one space in the given direction
     * @param direction
     */
    public void move(Direction direction) {
        location = location.forward(direction);
    }

    /**
     * Move forwards one space in the direction of the player's facing
     */
    public void moveForwards() {
        move(facing);
    }

    /**
     * Move backwards one space in the direction of the player's facing
     */
    public void moveBackwards() {
        location = location.backward(facing);
    }

    /**
     * Rotate the 90 degrees player left
     */
    public void rotateLeft() {
        facing = facing.left();
    }

    /**
     * Rotate the 90 degrees player left
     */
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

    /**
     * Set the player's work progress
     * @param progress
     */
    public void setProgress(double progress) {
        this.progress += progress;
        if(this.progress >= 100) {
            onProgressDone();
            this.progress = 0;
        }
    }

    public double getProgress() {
        return progress;
    }

    private void onProgressDone() {
        // TODO
    }

    public void removeProgress() {

    }

    /**
     * Add the standard amount of progress (using multiplier)
     */
    public void addProgress() {
        double toAdd = 1.0 * getProgressMultiplier();
        setProgress(progress + toAdd);
    }

    /**
     * Gets the player's progress multiplier, which depends on attributes and effects
     * @return
     */
    private double getProgressMultiplier() {
        return status.getAttribute(PlayerStatus.PlayerAttribute.PRODUCTIVITY);
    }

}
