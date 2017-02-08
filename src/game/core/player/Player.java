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
    private Location location;

    public Player(String name, Direction facing, Location location) {
        this.name = name;
        this.facing = facing;
        this.location = location;
    }

    /**
     * Move forwards one space in the given direction
     * @param direction
     */
    public void move(Direction direction) {
        setLocation(location.forward(direction));
    }
    
    /**
     * Gets the current facing of the player
     * @return Direction the current facing of the player
     */
    public Direction getFacing() {
        return facing;
    }

    /**
     * Sets the player's facing
     * @param facing
     */
    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    /**
     * Sets the player's location
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
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
        setLocation(location.backward(facing));
    }

    /**
     * Rotate the 90 degrees player left
     */
    public void rotateLeft() {
        setFacing(facing.left());
    }

    /**
     * Rotate the 90 degrees player left
     */
    public void rotateRight() {
        setFacing(facing.right());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Player player = (Player) o;

        if (!name.equals(player.name)) return false;
        if (facing != player.facing) return false;
        return location.equals(player.location);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        temp = Double.doubleToLongBits(progress);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (facing != null ? facing.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
