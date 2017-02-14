package game.core.ifc;

import game.AI.AIPlayer;
import game.core.Input;
import game.core.event.CreateAIPlayerRequest;
import game.core.event.Event;
import game.core.event.Events;
import game.core.event.PlayerInputEvent;
import game.core.player.Player;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.networking.ServerListener;

/**
 * Created by samtebbs on 12/02/2017.
 */
public class ServerSync {

    public static void init() {
        Events.on(PlayerInputEvent.class, ServerSync::onPlayerInput);
        Events.on(CreateAIPlayerRequest.class, ServerSync::makeAIPlayer);
    }

    private static void makeAIPlayer(CreateAIPlayerRequest request) {
        Player ai = AIPlayer.createAIPalyer("Volker", Direction.SOUTH,  new Location(4, 0, 0, World.world));
        ((ServerListener)request.serverListener).addPlayerToGame(ai);
    }

    private static void onPlayerInput(PlayerInputEvent event) {
    	System.out.println("managing input event");
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
        Location forwards = loc.forward(direction);
        if(forwards.checkBounds() && forwards.getTile().type.canWalkOver()) {
            player.setFacing(direction);
            player.setLocation(forwards);
        }
    }

}
