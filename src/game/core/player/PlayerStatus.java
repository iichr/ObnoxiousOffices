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
import game.core.player.action.PlayerActionSleep;
import game.core.player.effect.PlayerEffect;
import game.core.player.state.PlayerState;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class PlayerStatus implements Serializable {

    private static final int ATTRIBUTE_UPDATE_THRESHOLD = 5;
    private Map<PlayerAttribute, Double> attributes = new HashMap<>();
    private Map<PlayerAttribute, Integer> attributeUpdateCounter = new HashMap<>();
    private Set<PlayerState> states = new HashSet<>();
    private final Set<PlayerAction> actions = new HashSet<>();
    private final List<PlayerEffect> effects = new ArrayList<>();
    public final Player player;
    public boolean initialising = true;
    public Map<Class<? extends PlayerAction>, Integer> actionRepetitions = new HashMap<>();

    public static double FATIGUE_INCREASE = 0.003;

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
        if(!hasEffect(effect.getClass())) {
            effects.add(effect);
            Events.trigger(new PlayerEffectAddedEvent(effect, player.name), true);
        }
    }

    public boolean hasEffect(Class<? extends PlayerEffect> effectClass) {
        return getEffects().stream().anyMatch(effect1 -> effectClass == effect1.getClass());
    }

    public List<PlayerEffect> getEffects() {
        return new ArrayList<>(effects);
    }

    /**
     * Add an action
     * @param action
     */
    public void addAction(PlayerAction action) {
        actionRepetitions.compute(action.getClass(), (c, i) -> i == null ? 1 : i + 1);
        if(actionRepetitions.get(action.getClass()) >= action.getMaxRepetitions(new Random())) {
            action.onMaxRepetitions();
            actionRepetitions.put(action.getClass(), 0);
        }
        actions.add(action);
        Events.trigger(new PlayerActionAddedEvent(action, player.name), true);
        action.start();
    }

    public <T extends PlayerAction> boolean hasAction(Class<T> actionClass) {
        synchronized (actions) {
            return actions.stream().anyMatch(a -> a.getClass() == actionClass);
        }
    }

    public Set<PlayerAction> getActions() {
        return new HashSet<>(actions);
    }

    public void update(Player player) {
        synchronized (actions) {
            Updateable.updateAll(actions).forEach(a -> removeAction(a.getClass()));
        }
        synchronized (effects) {
            Updateable.updateAll(effects).forEach(e -> removeEffect(e.getClass()));
        }

        addToAttribute(PlayerAttribute.FATIGUE, FATIGUE_INCREASE);
        if(getAttribute(PlayerAttribute.FATIGUE) >= PlayerAttribute.FATIGUE.maxVal) {
            PlayerActionSleep sleep = new PlayerActionSleep(player);
            sleep.forced = true;
            player.status.addAction(sleep);
        }
    }

    /**
     * Set an attribute value
     * @param attribute
     * @param val
     */
    public void setAttribute(PlayerAttribute attribute, double val) {
        double diff = val - getAttribute(attribute);
        updateAttributeCounter(attribute);
        attributes.put(attribute, Math.max(0, Math.min(val, attribute.maxVal)));
        // Check for synchronised attributes and change them accordingly
        Arrays.stream(PlayerAttribute.values()).filter(a -> a.sync == attribute).forEach(a -> addToAttribute(a, a.syncFunc.apply(diff, getAttribute(attribute))));
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
        removeAction(action.getClass());
    }

    public void removeAction(Class<? extends PlayerAction> actionClass) {
        synchronized (actions) {
            Set<PlayerAction> matching = actions.stream().filter(a -> a.getClass() == actionClass).collect(Collectors.toSet());
            matching.forEach(action -> {
                Events.trigger(new PlayerActionEndedEvent(action, player.name), true);
                actions.remove(action);
            });
        }
    }

    public void removeEffect(Class<? extends PlayerEffect> effectClass) {
        synchronized (effects) {
            Set<PlayerEffect> matching = effects.stream().filter(e -> e.getClass() == effectClass).collect(Collectors.toSet());
            matching.forEach(effect -> {
                Events.trigger(new PlayerEffectEndedEvent(effect, player.name), true);
                effects.remove(effect);
            });
        }
    }

    public Set<PlayerState> getStates() {
        return new HashSet<>(states);
    }

    public PlayerEffect getEffect(Class<? extends PlayerEffect> playerEffectClass) {
        return getEffects().stream().filter(effect -> effect.getClass() == playerEffectClass).findFirst().orElse(null); // Don't judge me
    }

    public boolean canMove() {
        return actions.stream().allMatch(PlayerAction::allowsMove);
    }

    public boolean canInteract() {
        return actions.stream().allMatch(PlayerAction::allowsInteraction) && effects.stream().allMatch(PlayerEffect::allowsInteraction) && states.stream().allMatch(PlayerState::allowsInteraction);
    }

    public enum PlayerAttribute {
        FATIGUE(0.0, 1.0), PRODUCTIVITY(1.0, 1.0, FATIGUE, (diff, val) -> -val); // synchronise productivity with fatigue

        public final double initialVal, maxVal;
        public final PlayerAttribute sync;
        public final BiFunction<Double, Double, Double> syncFunc;

        PlayerAttribute(double initialVal, double maxVal, PlayerAttribute sync, BiFunction<Double, Double, Double> syncFunc) {
            this.initialVal = initialVal;
            this.maxVal = maxVal;
            this.sync = sync;
            this.syncFunc = syncFunc;
        }

        PlayerAttribute(double initialVal, double maxVal) {
            this(initialVal, maxVal, null, null);
        }
    }

}
