package game.core.event.player;

import game.core.player.PlayerStatus;

/**
 * Created by samtebbs on 11/02/2017.
 */
public class PlayerAttributeChangedEvent extends PlayerEvent {

    public final double newVal;
    public final PlayerStatus.PlayerAttribute attribute;

    public PlayerAttributeChangedEvent(double newVal, String playerName, PlayerStatus.PlayerAttribute attribute) {
        super(playerName);
        this.newVal = newVal;
        this.attribute = attribute;
    }
}
