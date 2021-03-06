package game.core.player.action;

import game.core.event.Event;
import game.core.event.Events;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.minigame.MiniGame;
import game.core.minigame.MiniGameHangman;
import game.core.player.Player;
import game.core.player.effect.PlayerEffectOnFire;
import game.core.world.World;
import game.core.world.tile.MetaTile;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileTypeComputer;
import game.util.Time;

import java.util.Random;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionWork extends PlayerActionMinigame {

    boolean doFire = false;

    public PlayerActionWork(Player player) {
        super(player);
    }

    @Override
    public void onMaxRepetitions() {
        doFire = true;
    }

    @Override
    public int getMaxRepetitions(Random rand) {
        return 3;
    }

    @Override
    public void end(MiniGameEndedEvent event) {
        super.end(event);
        if (doFire) {
            TileTypeComputer.getComputer(player).ifPresent(t -> {
                player.status.addEffect(new PlayerEffectOnFire((int) Time.ticks(5000), player));
                TileTypeComputer.ignite((MetaTile) t);
            });
            doFire = false;
        }
        if(event != null && event.victor.equals(player.name)) player.addProgress();
    }

    @Override
    public MiniGame getMiniGame() {
        return new MiniGameHangman(player.name);
    }
}
