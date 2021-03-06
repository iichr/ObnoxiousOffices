package game.core.world.tile.type;

import game.core.event.Events;
import game.core.event.GameStartedEvent;
import game.core.input.InteractionType;
import game.core.player.Player;
import game.core.player.state.PlayerState;
import game.core.player.action.*;
import game.core.world.Direction;
import game.core.world.Location;
import game.core.world.World;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.metadata.ComputerMetadata;
import game.util.Sets;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by samtebbs on 23/01/2017.
 */
public class TileTypeComputer extends TileTypeAction {

    static {
        Events.on(GameStartedEvent.class, TileTypeComputer::onGameStarted);
    }

    private static void onGameStarted(GameStartedEvent event) {
        int i = 0;
        List<Tile> computers = event.world.getTiles(TileTypeComputer.class);
        for (Player player : event.world.getPlayers()) {
            MetaTile computer = (MetaTile) computers.get(i++);
            computer.metadata = new ComputerMetadata(computer.location.coords, player.name);
        }
    }

    public TileTypeComputer(int id) {
        super(id);
    }

    @Override
    public void onWalkOver(Player player) {

    }

    @Override
    public boolean canWalkOver() {
        return false;
    }

    @Override
    public Collection<PlayerState> getRequiredStates() {
        return Sets.asSet(PlayerState.sitting);
    }

    @Override
    protected PlayerAction getAction(Player player, Tile tile, InteractionType type) {
        System.out.println("computer.getAction");
        if(!TileTypeComputer.getOnFire((MetaTile) tile)) {
            System.out.println("pc not on fire");
            String owner = getOwningPlayer((MetaTile) tile);
            if (type.getClass() == InteractionType.InteractionTypeWork.class && owner.equals(player.name))
                return (player.isAI ? new PlayerActionWorkTimed(player) : new PlayerActionWork(player));
            else if (type instanceof InteractionType.InteractionTypeHack) {
                Player target = World.world.getPlayer(((InteractionType.InteractionTypeHack) type).target);
                return player.isAI ? new PlayerActionHackTimed(player, target) : new PlayerActionHack(player, target);
            } else return null;
        } else return null;
    }

    @Override
    public Collection<Tile> getTiles(Location location, Direction facing) {
        return Collections.singletonList(new MetaTile(location, this, facing, 0, new ComputerMetadata(location.coords, "")));
    }

    public static Optional<Tile> getComputer(Player player) {
        List<Tile> computers = World.world.getTiles(TileTypeComputer.class);
        return computers.stream().filter(c -> getOwningPlayer((MetaTile) c).equals(player.name)).findFirst();
    }

    public static Tile getChair(Player player) {
        Optional<Tile> computer = getComputer(player);
        if(computer.isPresent()) {
            List<Tile> neighbours = World.world.getNeighbours(computer.get());
            return neighbours.stream().filter(t -> t.type.equals(TileType.CHAIR)).findFirst().orElse(null);
        }
        return null;
    }

    public static String getOwningPlayer(MetaTile computerTile) {
        Object player = computerTile.metadata.getVar(ComputerMetadata.PLAYER);
        return player == null ? "" : (String) player;
    }

    public static void setOnFire(MetaTile computer, boolean onFire) {
        System.out.printf("Setting computer at %s onFire: %b%n", computer.location, onFire);
        computer.metadata.setVar(ComputerMetadata.ON_FIRE, onFire);
    }

    public static void ignite(MetaTile computer) {
        setOnFire(computer, true);
    }

    public static void extinguish(MetaTile computer) {
        System.out.println("extinguish");
        setOnFire(computer, false);
    }

    public static boolean getOnFire(MetaTile computer) {
        return computer.metadata.getBoolVar(ComputerMetadata.ON_FIRE);
    }

}
