package game.core.sync;

import game.core.event.CreateAIPlayerRequest;
import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.core.event.chat.ChatMessageCreatedEvent;
import game.core.event.player.PlayerInputEvent;
import game.core.input.InputType;
import game.core.input.InputTypeInteraction;
import game.core.input.InputTypeMovement;
import game.core.input.InteractionType;
import game.core.player.Player;
import game.core.player.PlayerState;
import game.core.player.action.PlayerAction;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import game.core.world.tile.type.TileTypeComputer;
import game.util.Sets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by samtebbs on 12/02/2017.
 */
public class ServerSync {

	static Map<InteractionType, Set<TileType>> interactionMap = new HashMap<InteractionType, Set<TileType>>() {{
		put(InteractionType.HACK, Sets.asSet(TileType.COMPUTER));
		put(InteractionType.WORK, Sets.asSet(TileType.COMPUTER));
		put(InteractionType.OTHER, Sets.asSet(TileType.CHAIR, TileType.COFFEE_MACHINE, TileType.SOFA));
	}};

	public static void init() {
		Events.on(PlayerInputEvent.class, ServerSync::onPlayerInput);
		Events.on(ChatMessageCreatedEvent.class, ServerSync::onChatMessageCreated);
		Events.on(GameStartedEvent.class, ServerSync::onGameStarted);
	}

    private static void onGameStarted(GameStartedEvent event) {
        System.out.println("########### on game started");
        event.world.getPlayers().forEach(player -> {
            System.out.println("Adding sitting state to player " + player.name);
            // Make player sit on chair and face correct way
            player.setFacing(player.getLocation().getTile().facing);
            player.status.addState(PlayerState.sitting);
            System.out.println(player.status.hasState(PlayerState.sitting));
        });
    }

	private static void onChatMessageCreated(ChatMessageCreatedEvent event) {
		Events.trigger(event.toChatReceivedEvent());
	}

    private static void onPlayerInput(PlayerInputEvent event) {
        InputType type = event.inputType;
        Player player = World.world.getPlayer(event.playerName);
        if(type.isMovement()) processMovement(type, player);
        else processInteraction((InputTypeInteraction) type, player);
    }

    private static void processInteraction(InputTypeInteraction type, Player player) {
	    if (!player.status.canInteract()) return;
        Tile targetTile = player.getLocation().forward(player.getFacing()).getTile();
        if(targetTile != null) {
            boolean valid = interactionMap.get(type.type).stream().anyMatch(t -> t.equals(targetTile.type));
            if(valid) targetTile.onInteraction(player);
        }
    }

    private static void processMovement(InputType type, Player player) {
	    if(!player.status.canMove()) return;
        Direction direction = null;
        Location loc = player.getLocation();
        if(type instanceof InputTypeMovement) {
            switch (((InputTypeMovement) type).type) {
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
        }
        player.setFacing(direction);
        Location forwards = loc.forward(direction);
        Tile tile;
        if(forwards.checkBounds() && (tile = forwards.getTile()).type.canWalkOver()) {
            player.setLocation(forwards);
            tile.onWalkOver(player);
        }
    }

}
