package game.core.player.action;

import game.core.player.Player;
import game.core.player.PlayerStatus;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionDrink extends TimedPlayerAction {

    public PlayerActionDrink(Player player) {
        super(player);
    }

    @Override
    protected void update() {
        player.status.addToAttribute(PlayerStatus.PlayerAttribute.FATIGUE, -0.1);
    }

    @Override
    protected int getDuration() {
        return 5000;
    }
}
