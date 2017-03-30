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
import java.util.function.Consumer;

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
    private Consumer<MiniGameEndedEvent> onEnd;

    public MiniGame(String... players) {
        Arrays.stream(players).forEach(p -> {
            addPlayer(p);
            setStat(p, SCORE, 0);
        });
    }

    /**
     * Add to a recorded stat
     * @param player the player who the stat belongs to
     * @param stat the stat itself
     * @param val the value to add
     */
    protected void addStat(String player, String stat, int val) {
        setStat(player, stat, getIntStat(player, stat) + val);
    }

    /**
     * Called when a player sends input to a minigame
     * @param event the input event
     */
    public abstract void onInput(PlayerInputEvent event);

    /**
     * Set the value of a stat
     * @param player the player who the stat belongs to
     * @param stat the stat itself
     * @param val the value to set it to
     */
    public void setStat(String player, String stat, Object val) {
        if(!stats.containsKey(player)) {
            addPlayer(player);
        }
        stats.get(player).put(stat, val);
        Events.trigger(new MiniGameStatChangedEvent(player, stat, val), true);
    }

    /**
     * Add a player
     * @param player the player to add
     */
    protected void addPlayer(String player) {
        stats.put(player, new HashMap<>());
        players.add(player);
    }

    /**
     * Get the value of a stat
     * @param player the player who the stat belongs to
     * @param stat the stat itself
     * @return the value
     */
    public Object getStat(String player, String stat) {
        if(!stats.containsKey(player) || !stats.get(player).containsKey(stat)){
        	return null;
        }
        return stats.get(player).get(stat);
    }

    /**
     * Get all players in the minigame
     * @return the player list
     */
    public List<String> getPlayers() {
        return players;
    }

    /**
     * Update the minigame state
     */
    @Override
    public void update() {
        for (String player : getPlayers()){
        	if (getIntStat(player, SCORE) == MAX_SCORE){
        		end(player);
        	}
        }
    }

    /**
     * Get a stat as an integer {@link MiniGame#getStat(String, String)}
     * @param player
     * @param var
     * @return
     */
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

    /**
     * End the game with a winner
     * @param winner the winner
     */
    protected void end(String winner) {
        ended = true;
        MiniGameEndedEvent event = new MiniGameEndedEvent(getPlayers(), winner);
        if(onEnd != null) onEnd.accept(event);
        Events.trigger(event,true);
    }

    /**
     * Gets the update event to trigger when a variable is changed
     * @param var the variable name
     * @param val the variable value
     * @return the update event
     */
    @Override
    protected MiniGameVarChangedEvent getUpdateEvent(String var, Object val) {
        return new MiniGameVarChangedEvent(players.get(0), var, val);
    }

    /**
     * Checks if a player is in this minigame
     * @param playerName the player name
     * @return true if they are, else false
     */
    public boolean hasPlayer(String playerName) {
        return players.contains(playerName);
    }

    /**
     * Checks if the local player is playing
     * @return true if the are else false
     */
    public boolean isLocal() {
        return isPlaying(Player.localPlayerName);
    }

    /**
     * {@link MiniGame#hasPlayer(String)}
     * @param player
     * @return
     */
    public boolean isPlaying(String player) {
        return getPlayers().contains(player);
    }

    /**
     * Set the consumer to run when the game ends
     * @param onEnd the consumer to run
     */
    public void onEnd(Consumer<MiniGameEndedEvent> onEnd) {
        this.onEnd = onEnd;
    }
}
