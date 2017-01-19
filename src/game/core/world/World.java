package game.core.world;

import game.core.Updateable;
import game.core.player.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by samtebbs on 19/01/2017.
 */
public class World implements Updateable {

    private final Set<Player> players;

    public World(int maxPlayers) {
        this.players = new HashSet<>(maxPlayers);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public void update(Object o) {
        Updateable.updateAll(players, null);
    }

    @Override
    public boolean ended() {
        return false;
    }

}
