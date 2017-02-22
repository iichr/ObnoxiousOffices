package game.core.minigame;

import game.core.Updateable;
import game.core.event.*;

import java.util.*;

/**
 * Created by samtebbs on 18/02/2017.
 */
public abstract class MiniGame implements Updateable {

    public static final String SCORE = "SCORE";
    protected boolean ended = false;
    public final int MAX_SCORE = 2;

    public static MiniGame localMiniGame;

    private Map<String, Map<String, Object>> stats = new HashMap<>();
    private Map<String, Object> vars = new HashMap<>();
    private List<String> players = new ArrayList<>();

    protected void addVar(String var, int val) {
        setVar(var, getIntVar(var) + val);
    }

    protected void addStat(String player, String stat, int val) {
        setStat(player, stat, getIntStat(player, stat) + val);
    }

    protected void negVar(String var) {
        setVar(var, -getIntVar(var));
    }

    public abstract void onInput(PlayerInputEvent event);

    protected void setStat(String player, String stat, int val) {
        if(!stats.containsKey(player)) {
            addPlayer(player);
        }
        stats.get(player).put(stat, val);
        Events.trigger(new MiniGameStatChangedEvent(player, stat, val), true);
    }

    protected void setVar(String var, int val) {
        vars.put(var, val);
        Events.trigger(new MiniGameVarChangedEvent(var, val), true);
    }

    protected void addPlayer(String player) {
        stats.put(player, new HashMap<>());
        players.add(player);
    }

    public Object getStat(String player, String stat) {
        if(!stats.containsKey(player) || !stats.get(player).containsKey(stat)) return null;
        return stats.get(player).get(stat);
    }

    public Object getVar(String var) {
        return vars.containsKey(var) ? vars.get(var) : null;
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

    public int getIntVar(String var) {
        Object val = getVar(var);
        return val == null ? 0 : (Integer) val;
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
