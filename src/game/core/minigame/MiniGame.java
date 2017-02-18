package game.core.minigame;

import game.core.Updateable;
import game.core.event.Events;
import game.core.event.MiniGameEndedEvent;
import game.core.event.PlayerInputEvent;
import game.core.player.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by samtebbs on 18/02/2017.
 */
public abstract class MiniGame implements Updateable {

    public static final String SCORE = "SCORE";
    protected boolean ended = false;
    public final int MAX_SCORE = 2;

    private Map<String, Map<String, Integer>> stats = new HashMap<>();
    private Map<String, Integer> vars = new HashMap<>();
    private List<String> players = new ArrayList<>();

    protected void addVar(String var, int val) {
        setVar(var, getVar(var) + val);
    }

    protected void negVar(String var) {
        setVar(var, -getVar(var));
    }

    public abstract void onInput(PlayerInputEvent event);

    protected void setStat(String player, String stat, int val) {
        if(!stats.containsKey(player)) {
            addPlayer(player);
        }
        stats.get(player).put(stat, val);
    }

    protected void setVar(String var, int val) {
        vars.put(var, val);
    }

    protected void addPlayer(String player) {
        stats.put(player, new HashMap<>());
        players.add(player);
    }

    public int getStat(String player, String stat) {
        if(!stats.containsKey(player) || !stats.get(player).containsKey(stat)) return 0;
        return stats.get(player).get(stat);
    }

    public int getVar(String var) {
        return vars.containsKey(var) ? vars.get(var) : 0;
    }

    public List<String> getPlayers() {
        return players;
    }

    @Override
    public void update() {
        for (String player : getPlayers()) if (getStat(player, SCORE) == MAX_SCORE) end(player);
    }

    @Override
    public boolean ended() {
        return ended;
    }

    protected void end(String player) {
        ended = true;
        Events.trigger(new MiniGameEndedEvent(getPlayers(), player));
    }
}
