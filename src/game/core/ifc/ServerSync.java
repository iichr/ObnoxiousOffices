package game.core.ifc;

import game.core.Input;
import game.core.event.Event;
import game.core.event.Events;
import game.core.event.PlayerInputEvent;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;

/**
 * Created by samtebbs on 12/02/2017.
 */
public class ServerSync {

    public static void init() {
        Events.on(PlayerInputEvent.class, ServerSync::onPlayerInput);
    }

    private static void onPlayerInput(PlayerInputEvent event) {
        Input.InputType type = event.inputType;
        Player player = World.world.getPlayer(event.playerName);
        if(type.isMovement) processMovement(type, player);
    }

    private static void processMovement(Input.InputType type, Player player) {
        Direction direction = null;
        Location loc = player.getLocation();
        switch (type) {
            case MOVE_UP:
                direction = Direction.NORTH;
                break;
            case MOVE_DOWN:
                direction = Direction.SOUTH;
                break;
            case MOVE_LEFT:
                direction = Direction.WEST;
                break;
            case MOVE_RIGHT:
                direction = Direction.EAST;
                break;
        }
        player.setFacing(direction);
        Location forwards = loc.forward(direction);
        if(forwards.checkBounds() && forwards.getTile().type.canWalkOver()) player.setLocation(forwards);
    }

}
