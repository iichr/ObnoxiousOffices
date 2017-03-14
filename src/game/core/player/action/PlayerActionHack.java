package game.core.player.action;

import game.core.minigame.MiniGameHangman;
import game.core.minigame.MiniGamePong;
import game.core.player.Player;
import game.core.world.World;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionHack extends TimedPlayerAction {

    protected final Player target;

    public PlayerActionHack(Player player, Player target) {
        super(player);
        this.target = target;
        World.world.startMiniGame(new MiniGameHangman(player.name));
    }

    @Override
    protected void timedUpdate() {
        target.removeProgress();
    }

    @Override
    protected int getDuration() {
        return 5000;
    }
}
