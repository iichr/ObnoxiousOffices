package game.core.player;

import game.core.Updateable;
import game.core.player.action.PlayerAction;
import game.core.player.effect.PlayerEffect;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class PlayerStatus {

    private HashMap<PlayerAttribute, Double> attributes = new HashMap<>();
    private Set<PlayerAction> actions = new HashSet<>();
    private Set<PlayerEffect> effects = new HashSet<>();

    public PlayerStatus() {
        // Add all attributes with their initial values
        Arrays.stream(PlayerAttribute.values()).forEach(attr -> setAttribute(attr, attr.initialVal));
    }

    public void addEffect(PlayerEffect effect) {
        effects.add(effect);
    }

    public void addAction(PlayerAction action) {
        actions.add(action);
        action.start();
    }

    public void update(Player player) {
        actions = update(actions, player);
        effects = update(effects, player);
    }

    private <T extends Updateable<Player>> Set<T> update(Set<T> updateables, Player player) {
        updateables.forEach(u -> u.update(player));
        return updateables.stream().filter(Updateable::ended).collect(Collectors.toSet());
    }

    public void setAttribute(PlayerAttribute attribute, double val) {
        attributes.put(attribute, Math.max(0, Math.min(val, attribute.maxVal)));
    }

    public void addToAttribute(PlayerAttribute attribute, double val) {
        if (hasAttribute(attribute)) setAttribute(attribute, val + getAttribute(attribute));
        else setAttribute(attribute, val);
    }

    public boolean hasAttribute(PlayerAttribute attribute) {
        return attributes.containsKey(attribute);
    }

    public double getAttribute(PlayerAttribute attribute) {
        return attributes.get(attribute);
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
