package game.core.event.player.effect;

import game.core.event.player.PlayerEvent;
import game.core.player.Player;
import game.core.player.effect.PlayerEffect;

/**
 * Created by samtebbs on 03/03/2017.
 */
public class PlayerEffectElapsedUpdate extends PlayerEvent {

    public final int elapsed;
    public final Class<? extends PlayerEffect> effectClass;

    public PlayerEffectElapsedUpdate(String name, PlayerEffect effect, int elapsed) {
        super(name);
        this.elapsed = elapsed;
        this.effectClass = effect.getClass();
    }
}
