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

    public PlayerActionMinigame(Player player) {
        super(player);
        Events.on(MiniGameEndedEvent.class, e -> {
            if(e.victor.equals(player.name))  end();
        });
    }

    public abstract MiniGame getMiniGame();

    @Override
    public boolean ended() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void start() {
        World.world.startMiniGame(getMiniGame());
    }

    @Override
    public void cancel() {

    }
}
