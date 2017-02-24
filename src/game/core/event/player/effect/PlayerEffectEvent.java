package game.core.event;

import game.core.player.effect.PlayerEffect;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerEffectEvent extends PlayerEvent {

    public final PlayerEffect effect;

    public PlayerEffectEvent(String playerName, PlayerEffect effect) {
        super(playerName);
        this.effect = effect;
    }
}
