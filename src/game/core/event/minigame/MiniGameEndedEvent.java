package game.core.event.minigame;

import game.core.event.Event;
import game.core.player.Player;
import game.ui.states.Play;

import java.util.List;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameEndedEvent extends Event {

    public final String victor;
    public final List<String> players;

    public MiniGameEndedEvent(List<String> players, String victor) {
        this.players = players;
        this.victor = victor;
    }

    public boolean isLocal() {
        return players.contains(Player.localPlayerName);
    }

}
