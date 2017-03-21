package game.core.player.action;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.minigame.MiniGame;
import game.core.minigame.MiniGameHangman;
import game.core.player.Player;
import game.core.world.World;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionWork extends PlayerActionMinigame {

    public PlayerActionWork(Player player) {
        super(player);
    }

    @Override
    public void end(MiniGameEndedEvent event) {
        if(event.victor.equals(player.name)) player.addProgress();
    }

    @Override
    public MiniGame getMiniGame() {
        return new MiniGameHangman(player.name);
    }
}
