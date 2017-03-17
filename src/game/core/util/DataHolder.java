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

    public Object getVar(String var) {
        return vars.containsKey(var) ? vars.get(var) : null;
    }

    protected void addVar(String var, int val) {
        setVar(var, getIntVar(var) + val);
    }
    
    protected void addVar(String var, float val) {
        setVar(var, (float)getVar(var) + val);
    }

    public void setVar(String var, Object val) {
        vars.put(var, val);
        Events.trigger(getUpdateEvent(var, val), true);
    }

    protected void negVar(String var) {
        setVar(var, -(float)getVar(var));
    }

    public int getIntVar(String var) {
        Object val = getVar(var);
        return val == null ? 0 : (Integer) val;
    }

}
