package game.core.player.action;

import game.core.event.Events;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.minigame.MiniGame;
import game.core.player.Player;
import game.core.world.World;

/**
 * Created by samtebbs on 14/03/2017.
 */
public abstract class PlayerActionMinigame extends PlayerAction {

    protected boolean ended = false;

    public PlayerActionMinigame(Player player) {
        super(player);
    }

    public abstract MiniGame getMiniGame();

    @Override
    public boolean ended() {
        return ended;
    }

    @Override
    public void update() {

    }

    @Override
    public void start() {
        MiniGame miniGame = getMiniGame();
        miniGame.onEnd(this::end);
        if(!World.world.startMiniGame(miniGame)) end();
    }

    @Override
    public void end() {
        end(null);
    }

    public void end(MiniGameEndedEvent event) {
        ended = true;
    }

    @Override
    public void cancel() {
        end();
    }
}
