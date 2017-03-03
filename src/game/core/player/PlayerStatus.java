package game.core.player;

import game.core.Updateable;
import game.core.event.*;
import game.core.event.player.PlayerStateAddedEvent;
import game.core.event.player.PlayerStateRemovedEvent;
import game.core.event.player.action.PlayerActionAddedEvent;
import game.core.event.player.action.PlayerActionEndedEvent;
import game.core.event.player.PlayerAttributeChangedEvent;
import game.core.event.player.effect.PlayerEffectAddedEvent;
import game.core.event.player.effect.PlayerEffectEndedEvent;
import game.core.player.action.PlayerAction;
import game.core.player.effect.PlayerEffect;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class PlayerStatus implements Serializable {

    private static final int ATTRIBUTE_UPDATE_THRESHOLD = 3;
    private Map<PlayerAttribute, Double> attributes = new HashMap<>();
    private Map<PlayerAttribute, Integer> attributeUpdateCounter = new HashMap<>();
    private Set<PlayerState> states = new HashSet<>();
    private Set<PlayerAction> actions = new HashSet<>();
    private List<PlayerEffect> effects = new ArrayList<>();
    public final Player player;
    public boolean initialising = true;

    public static double FATIGUE_INCREASE = 0.001;

    public PlayerStatus(Player player) {
        this.player = player;
        // Add all attributes with their initial values
        Arrays.stream(PlayerAttribute.values()).forEach(attr -> setAttribute(attr, attr.initialVal));
        initialising = false;
    }

    public void addState(PlayerState state) {
        if(!states.contains(state)) {
            states.add(state);
            state.onStart(player);
            Events.trigger(new PlayerStateAddedEvent(player.name, state), true);
        }
    }

    public void removeState(PlayerState state) {
        if(states.contains(state)) {
            states.remove(state);
            state.onEnd(player);
            Events.trigger(new PlayerStateRemovedEvent(player.name, state), true);
        }
    }

    public boolean hasState(PlayerState state) {
        return states.stream().anyMatch(s -> state.getClass().isInstance(s));
    }

    /**
     * Add an effect to the player
     * @param effect
     */
    public void addEffect(PlayerEffect effect) {
        if(!hasEffect(effect)) {
            effects.add(effect);
            Events.trigger(new PlayerEffectAddedEvent(effect, player.name), true);
        }
    }

    public boolean hasEffect(PlayerEffect effect) {
        return getEffects().stream().anyMatch(effect1 -> effect.getClass() == effect1.getClass());
    }

    public List<PlayerEffect> getEffects() {
        return effects.stream().collect(Collectors.toList());
    }

    /**
     * Add an action
     * @param action
     */
    public void addAction(PlayerAction action) {
        actions.add(action);
        action.start();
        Events.trigger(new PlayerActionAddedEvent(action, player.name), true);
    }

    public <T extends PlayerAction> boolean hasAction(Class<T> actionClass) {
        return actions.stream().anyMatch(a -> a.getClass() == actionClass);
    }

    public Set<PlayerAction> getActions() {
        return actions.stream().collect(Collectors.toSet());
    }

    public void update(Player player) {
        Updateable.updateAll(actions).forEach(this::removeAction);
        Updateable.updateAll(effects).forEach(this::removeEffect);

        addToAttribute(PlayerAttribute.FATIGUE, FATIGUE_INCREASE);
    }

    // TODO: Change productivity based on fatigue
    /**
     * Set an attribute value
     * @param attribute
     * @param val
     */
    public void setAttribute(PlayerAttribute attribute, double val) {
        updateAttributeCounter(attribute);
        attributes.put(attribute, Math.max(0, Math.min(val, attribute.maxVal)));
        if(!initialising && attributeUpdateCounter.get(attribute) >= ATTRIBUTE_UPDATE_THRESHOLD){
            attributeUpdateCounter.put(attribute, 0);
            Events.trigger(new PlayerAttributeChangedEvent(val, player.name, attribute), true);
        }
    }

    private void updateAttributeCounter(PlayerAttribute attribute) {
        if(!attributeUpdateCounter.containsKey(attribute)) attributeUpdateCounter.put(attribute, 1);
        else attributeUpdateCounter.put(attribute, attributeUpdateCounter.get(attribute) + 1);
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

    public void cancelAction(PlayerAction action) {
        action.cancel();
        removeAction(action);
    }

    public void removeAction(PlayerAction action) {
        actions.remove(action);
        Events.trigger(new PlayerActionEndedEvent(action, player.name), true);
    }

    public void removeEffect(PlayerEffect effect) {
        effects.remove(effect);
        Events.trigger(new PlayerEffectEndedEvent(effect, player.name), true);
    }

    public Set<PlayerState> getStates() {
        return states.stream().collect(Collectors.toSet());
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
