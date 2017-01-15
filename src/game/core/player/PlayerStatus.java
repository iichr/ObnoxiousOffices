package game.core.player;

import game.core.player.effect.PlayerEffect;

import java.util.*;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class PlayerStatus {

    private HashMap<PlayerAttribute, Double> attributes = new HashMap<>();
    private List<PlayerEffect> effects = new ArrayList<>();

    public PlayerStatus() {
        // Add all attributes with their initial values
        Arrays.stream(PlayerAttribute.values()).forEach(attr -> setAttribute(attr, attr.initialVal));
    }

    public void addEffect(PlayerEffect effect) {
        effects.add(effect);
    }

    public void update(Player player) {
        // Update all effects and remove those that have expired
        int offset = 0;
        int size = effects.size();
        for (int i = 0; i < size; i++) {
            int j = i - offset;
            if(effects.get(j).update(player)) {
                offset++;
                effects.remove(j);
            }
        }
    }

    public void setAttribute(PlayerAttribute attribute, double val) {
        attributes.put(attribute, val);
    }

    public void addToAttribute(PlayerAttribute attribute, double val) {
        if (hasAttribute(attribute)) setAttribute(attribute, val + getAttribute(attribute).get());
        else setAttribute(attribute, val);
    }

    public boolean hasAttribute(PlayerAttribute attribute) {
        return attributes.containsKey(attribute);
    }

    public Optional<Double> getAttribute(PlayerAttribute attribute) {
        return hasAttribute(attribute) ? Optional.of(attributes.get(attribute)) : Optional.empty();
    }

    public enum PlayerAttribute {
        FATIGUE(0.0), PRODUCTIVITY(1.0);

        public final double initialVal;

        PlayerAttribute(double initialVal) {
            this.initialVal = initialVal;
        }
    }

}
