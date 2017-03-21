package game.core.event.minigame;

import game.core.event.Event;
import game.core.minigame.MiniGame;
import game.core.player.Player;

import java.util.List;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameStartedEvent extends Event {

    public final MiniGame game;

    public MiniGameStartedEvent(MiniGame game) {
        this.game = game;
    }

    public boolean isLocal() {
        return game.isLocal();
    }

}
