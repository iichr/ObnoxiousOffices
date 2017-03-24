package game.core.player.action;

import game.core.event.Events;
import game.core.event.chat.ChatMessageReceivedEvent;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.minigame.MiniGame;
import game.core.minigame.MiniGameHangman;
import game.core.minigame.MiniGamePong;
import game.core.player.Player;
import game.core.world.World;

/**
 * Created by samtebbs on 16/01/2017.
 */
public class PlayerActionHack extends PlayerActionMinigame {

    protected final Player target;

    public PlayerActionHack(Player player, Player target) {
        super(player);
        this.target = target;
    }

    @Override
    public void start() {
        super.start();
        Events.trigger(new ChatMessageReceivedEvent("Server", player.name + " is hacking " + target.name), true);
        player.status.actionRepetitions.put(PlayerActionWork.class, 0);
        player.status.actionRepetitions.put(PlayerActionWorkTimed.class, 0);
    }

    @Override
    public void end(MiniGameEndedEvent event) {
        super.end(event);
        if(event != null && event.victor.equals(player.name)) target.removeProgress((int) (25 * player.getProgressMultiplier()));
    }

    @Override
    public MiniGame getMiniGame() {
        return target.isAI ? new MiniGameHangman(player.name) : new MiniGamePong(player.name, target.name);
    }
}
