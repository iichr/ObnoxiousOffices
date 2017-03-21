package game.core.player.action;

import game.core.event.minigame.MiniGameEndedEvent;
import game.core.minigame.MiniGame;
import game.core.minigame.MiniGameHangman;
import game.core.minigame.MiniGamePong;
import game.core.player.Player;
import game.core.world.World;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionHack extends PlayerActionMinigame {

    protected final Player target;

    public PlayerActionHack(Player player, Player target) {
        super(player);
        this.target = target;
    }

    @Override
    public void end(MiniGameEndedEvent event) {
        if(event.victor.equals(player.name)) target.removeProgress();
    }

    @Override
    public MiniGame getMiniGame() {
        return target.isAI ? new MiniGameHangman(player.name) : new MiniGamePong(player.name, target.name);
    }
}
