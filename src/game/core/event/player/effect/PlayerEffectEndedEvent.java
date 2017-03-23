package game.core.event.player.effect;

import game.core.player.effect.PlayerEffect;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerEffectEndedEvent extends PlayerEffectEvent {
    public PlayerEffectEndedEvent(PlayerEffect effect, String playerName) {
        super(playerName, effect);
    }
}
