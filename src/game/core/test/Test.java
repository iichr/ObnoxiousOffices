package game.core.test;

import game.core.event.Events;
import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeMovement;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.World;

/**
 * Created by samtebbs on 26/01/2017.
 */
public class Test {

    public static String localPlayer;

    static {
        Test test = new Test();
        Events.on(PlayerInputEvent.class, test::playerInput);
    }

    private void playerInput(PlayerInputEvent t) {
        if(t.inputType instanceof InputTypeMovement) {
            switch (((InputTypeMovement) t.inputType).type) {
                case MOVE_DOWN:
                    getPlayer(localPlayer).setFacing(Direction.SOUTH);
                    break;
                case MOVE_UP:
                    getPlayer(localPlayer).setFacing(Direction.NORTH);
                    break;
                case MOVE_LEFT:
                    getPlayer(localPlayer).setFacing(Direction.WEST);
                    break;
                case MOVE_RIGHT:
                    getPlayer(localPlayer).setFacing(Direction.EAST);
                    break;
            }
        }
        getPlayer(localPlayer).moveForwards();
    }
    
    private static Player getPlayer(String playerName) {
        return getWorld().getPlayer(playerName);
    }
    
    private static World getWorld() {
        return World.world;
    }

}
