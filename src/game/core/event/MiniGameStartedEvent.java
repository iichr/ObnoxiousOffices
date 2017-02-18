package game.core.event;

import game.core.minigame.MiniGame;

import java.util.List;

/**
 * Created by samtebbs on 18/02/2017.
 */
public class MiniGameStartedEvent extends Event {

    public final MiniGame game;

    public MiniGameStartedEvent(MiniGame game) {
        this.game = game;
    }
}
