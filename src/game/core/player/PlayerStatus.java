package game.core.player;

import game.core.Updateable;
import game.core.event.*;
import game.core.ifc.Net;
import game.core.player.action.PlayerAction;
import game.core.player.effect.PlayerEffect;

import java.io.Serializable;
import java.util.*;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class PlayerStatus implements Serializable {

    private HashMap<PlayerAttribute, Double> attributes = new HashMap<>();
    private Set<PlayerAction> actions = new HashSet<>();
    private Set<PlayerEffect> effects = new HashSet<>();
    public final Player player;

    public PlayerStatus(Player player) {
        this.player = player;
        // Add all attributes with their initial values
        Arrays.stream(PlayerAttribute.values()).forEach(attr -> setAttribute(attr, attr.initialVal));
    }

    /**
     * Add an effect to the player
     * @param effect
     */
    public void addEffect(PlayerEffect effect) {
        effects.add(effect);
        Net.broadcast(new PlayerEffectAddedEvent(effect, player.name));
    }

    /**
     * Add an action
     * @param action
     */
    public void addAction(PlayerAction action) {
        actions.add(action);
        action.start();
        Net.broadcast(new PlayerActionAddedEvent(action, player.name));
    }

    public void update(Player player) {
        Set<PlayerAction> actions2 = Updateable.updateAll(actions);
        actions2.forEach(a -> Net.broadcast(new PlayerActionEndedEvent(a, player.name)));
        actions.removeAll(actions2);

        Set<PlayerEffect> effects2 = Updateable.updateAll(effects);
        effects2.forEach(e -> Net.broadcast(new PlayerEffectEndedEvent(e, player.name)));
        effects.removeAll(effects2);
    }

    // TODO: Change productivity based on fatigue
    /**
     * Set an attribute value
     * @param attribute
     * @param val
     */
    public void setAttribute(PlayerAttribute attribute, double val) {
        attributes.put(attribute, Math.max(0, Math.min(val, attribute.maxVal)));
        Net.broadcast(new PlayerAttributeChangedEvent(val, player.name, attribute));
    }

    /**
     * Add the given value to an attribute's value
     * @param attribute
     * @param val
     */
    public void addToAttribute(PlayerAttribute attribute, double val) {
        setAttribute(attribute, val + getAttribute(attribute));
    }

    /**
     * Check if the status has an attribute
     * @param attribute
     * @return
     */
    public boolean hasAttribute(PlayerAttribute attribute) {
        return attributes.containsKey(attribute);
    }

    /**
     * Gets the value of an attribute, or 0 if it isn't set
     * @param attribute
     * @return
     */
    public double getAttribute(PlayerAttribute attribute) {
        return attributes.getOrDefault(attribute, 0.0);
    }

    public void removeAction(PlayerAction action) {
        actions.remove(action);
    }

    public void removeEffect(PlayerEffect effect) {
        effects.remove(effect);
    }

    public enum PlayerAttribute {
        FATIGUE(0.0, 1.0), PRODUCTIVITY(1.0, 1.0);

        public final double initialVal, maxVal;

        PlayerAttribute(double initialVal, double maxVal) {
            this.initialVal = initialVal;
            this.maxVal = maxVal;
        }
    }

}
