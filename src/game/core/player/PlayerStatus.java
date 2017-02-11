package game.core.player;

import game.core.Updateable;
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
    }

    /**
     * Add an action
     * @param action
     */
    public void addAction(PlayerAction action) {
        actions.add(action);
        action.start();
    }

    public void update(Player player) {
        actions = Updateable.updateAll(actions);
        effects = Updateable.updateAll(effects);
    }

    // TODO: Change productivity based on fatigue
    /**
     * Set an attribute value
     * @param attribute
     * @param val
     */
    public void setAttribute(PlayerAttribute attribute, double val) {
        attributes.put(attribute, Math.max(0, Math.min(val, attribute.maxVal)));
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

    public enum PlayerAttribute {
        FATIGUE(0.0, 1.0), PRODUCTIVITY(1.0, 1.0);

        public final double initialVal, maxVal;

        PlayerAttribute(double initialVal, double maxVal) {
            this.initialVal = initialVal;
            this.maxVal = maxVal;
        }
    }

}
