package game.core.player.effect;

import game.core.player.Player;
import game.core.player.PlayerStatus;

/**
 * Created by samtebbs on 28/02/2017.
 */
public class PlayerEffectCoffeeBuzz extends PlayerEffect {

    public PlayerEffectCoffeeBuzz(int duration, Player player) {
        super(duration, player);
    }

    @Override
    public void update() {
        super.update();
        player.status.addToAttribute(PlayerStatus.PlayerAttribute.PRODUCTIVITY, -0.005);
    }
}
