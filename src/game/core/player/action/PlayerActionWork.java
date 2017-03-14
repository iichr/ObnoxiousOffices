package game.core.player.action;

import game.core.minigame.MiniGameHangman;
import game.core.player.Player;
import game.core.world.World;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionWork extends TimedPlayerAction {

    public PlayerActionWork(Player player) {
        super(player);
        World.world.startMiniGame(new MiniGameHangman(player.name));
    }

    @Override
    protected void timedUpdate() {
        player.addProgress();
    }

    @Override
    protected int getDuration() {
        return 10000;
    }

}
