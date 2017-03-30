package game.core.util;

import game.core.event.Event;
import game.core.event.Events;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by samtebbs on 22/02/2017.
 */
public abstract class DataHolder implements Serializable {

    private Map<String, Object> vars = new HashMap<>();

    protected abstract Event getUpdateEvent(String var, Object val);

    /**
     * Gets a variable's value
     * @param var the variable
     * @return the value
     */
    public Object getVar(String var) {
        return vars.getOrDefault(var, null);
    }

    /**
     * Adds to a variable's value
     * @param var the variable
     *            @param val the value to add
     * @return the new value
     */
    public void addVar(String var, int val) {
        setVar(var, getIntVar(var) + val);
    }

    /**
     * {@link DataHolder#addVar(String, int)}
     * @param var
     * @param val
     */
    public void addVar(String var, float val) {
        setVar(var, (float)getVar(var) + val);
    }

    /**
     * Sets the value of a variable
     * @param var the variable
     * @param val the value
     */
    public void setVar(String var, Object val) {
        vars.put(var, val);
        Events.trigger(getUpdateEvent(var, val), true);
    }

    /**
     * Negates a variable
     * @param var the variable
     */
    public void negVar(String var) {
        setVar(var, -(float)getVar(var));
    }

    /**
     * Returns a variable as an int
     * @param var the variabke
     * @return int form
     */
    public int getIntVar(String var) {
        Object val = getVar(var);
        return val == null ? 0 : (Integer) val;
    }

    /**
     * Returns a variable as a boolean
     * @param var the variabke
     * @return boolean form
     */
    public boolean getBoolVar(String var) {
        return (Boolean) getVar(var);
    }
}
