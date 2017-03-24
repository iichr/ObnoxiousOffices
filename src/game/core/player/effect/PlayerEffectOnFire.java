package game.core.player.effect;

import game.core.player.Player;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileTypeComputer;

/**
 * Created by samtebbs on 23/03/2017.
 */
public class PlayerEffectOnFire extends PlayerEffect {
    private static final long serialVersionUID = -4438140090215929505L;

    public PlayerEffectOnFire(int duration, Player player) {
        super(duration, player);
    }

    @Override
    public void update() {
        super.update();
        System.out.printf("onFire.update %d/%d%n", getElapsed(), getDuration());
    }

    @Override
    public void end() {
        super.end();
        System.out.println("onFire.end");
        TileTypeComputer.getComputer(player).ifPresent(t -> TileTypeComputer.extinguish((MetaTile) t));
    }
}
