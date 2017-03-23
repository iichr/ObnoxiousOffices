package game.core.player.action;

import game.core.player.Player;
import game.core.player.PlayerStatus;
import game.core.player.effect.PlayerEffect;
import game.core.player.effect.PlayerEffectCoffeeBuzz;
import game.util.Time;

import java.util.Random;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionDrink extends TimedPlayerAction {

    public PlayerActionDrink(Player player) {
        super(player);
    }

    @Override
    public int getMaxRepetitions(Random rand) {
        return rand.nextInt(10);
    }

    @Override
    public void onMaxRepetitions() {
        PlayerEffect effect = new PlayerEffectCoffeeBuzz((int) Time.ticks(60000), player);
        if(!player.status.hasEffect(effect.getClass())) player.status.addEffect(effect);
        else player.status.getEffect(PlayerEffectCoffeeBuzz.class).setElapsed(effect.getDuration());
    }

    @Override
    protected void timedUpdate() {
        player.status.addToAttribute(PlayerStatus.PlayerAttribute.FATIGUE, -0.05);
    }

    @Override
    protected int getDuration() {
        return 5000;
    }
}
