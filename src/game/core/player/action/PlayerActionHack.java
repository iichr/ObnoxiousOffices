package game.core.player.action;

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
    public void end() {
        target.removeProgress();
    }

    @Override
    public MiniGame getMiniGame() {
        return new MiniGameHangman(player.name);
    }
}
