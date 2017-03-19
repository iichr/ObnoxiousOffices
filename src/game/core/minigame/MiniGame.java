package game.core.minigame;

import game.core.Updateable;
import game.core.event.*;
import game.core.event.minigame.MiniGameEndedEvent;
import game.core.event.minigame.MiniGameStatChangedEvent;
import game.core.event.minigame.MiniGameVarChangedEvent;
import game.core.event.player.PlayerInputEvent;
import game.core.player.Player;
import game.core.util.DataHolder;

import java.io.Serializable;
import java.util.*;

/**
 * Created by samtebbs on 18/02/2017.
 */
public abstract class MiniGame extends DataHolder implements Updateable, Serializable {

    public static final String SCORE = "SCORE";
    protected boolean ended = false;
    public final int MAX_SCORE = 2;

    public static MiniGame localMiniGame;

    private Map<String, Map<String, Object>> stats = new HashMap<>();
    private List<String> players = new ArrayList<>();

    public MiniGame(String... players) {
        Arrays.stream(players).forEach(p -> {
            addPlayer(p);
            setStat(p, SCORE, 0);
        });
    }

    protected void addStat(String player, String stat, int val) {
        setStat(player, stat, getIntStat(player, stat) + val);
    }

    public abstract void onInput(PlayerInputEvent event);

    public void setStat(String player, String stat, Object val) {
        if(!stats.containsKey(player)) {
            addPlayer(player);
        }
        stats.get(player).put(stat, val);
        Events.trigger(new MiniGameStatChangedEvent(player, stat, val), true);
    }

    protected void addPlayer(String player) {
        stats.put(player, new HashMap<>());
        players.add(player);
    }

    public Object getStat(String player, String stat) {
        if(!stats.containsKey(player) || !stats.get(player).containsKey(stat)) return null;
        return stats.get(player).get(stat);
    }

    public List<String> getPlayers() {
        return players;
    }

    @Override
    public void update() {
        for (String player : getPlayers()) if (getIntStat(player, SCORE) == MAX_SCORE) end(player);
    }

    public int getIntStat(String player, String var) {
        Object val = getStat(player, var);
        return val == null ? 0 : (Integer) val;
    }

    @Override
    public boolean ended() {
        return ended;
    }

    @Override
    public void end() {
        end("none");
    }

    protected void end(String winner) {
        ended = true;
        Events.trigger(new MiniGameEndedEvent(getPlayers(), winner),true);
    }

    @Override
    protected MiniGameVarChangedEvent getUpdateEvent(String var, Object val) {
        return new MiniGameVarChangedEvent(var, val);
    }

    public boolean hasPlayer(String playerName) {
        return players.contains(playerName);
    }

    public boolean isLocal() {
        return isPlaying(Player.localPlayerName);
    }

    public boolean isPlaying(String player) {
        return getPlayers().contains(player);
    }
}
