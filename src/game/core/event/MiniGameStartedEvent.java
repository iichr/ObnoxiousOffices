package game.core.event;

import game.core.minigame.MiniGame;

import java.util.List;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameStartedEvent extends MiniGameEvent {

    public final MiniGame game;

    public MiniGameStartedEvent(List<String> players, MiniGame game) {
        super(players);
        this.game = game;
    }
}
