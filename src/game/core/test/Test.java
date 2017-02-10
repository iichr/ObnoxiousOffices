package game.core.test;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.PlayerInputEvent;
import game.core.player.Player;
import game.core.world.Direction;

/**
 * Created by samtebbs on 26/01/2017.
 */
public class Test {

    public static Player localPlayer;

    static {
        Test test = new Test();
        Events.on(PlayerInputEvent.class, test::playerInput);
    }

    private void playerInput(PlayerInputEvent t) {
        switch (t.inputType) {
            case MOVE_DOWN:
                localPlayer.setFacing(Direction.SOUTH);
                break;
            case MOVE_UP:
                localPlayer.setFacing(Direction.NORTH);
                break;
            case MOVE_LEFT:
                localPlayer.setFacing(Direction.WEST);
                break;
            case MOVE_RIGHT:
                localPlayer.setFacing(Direction.EAST);
                break;
        }
        localPlayer.moveForwards();
    }

}
