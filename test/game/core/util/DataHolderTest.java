package game.core.util;

import game.core.event.Event;
import game.core.event.GameFullEvent;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 25/03/2017.
 */
class DataHolderTest {

    DataHolder data = new DataHolder() {

        @Override
        protected Event getUpdateEvent(String var, Object val) {
            return new GameFullEvent();
        }
    };

    @Test
    void getVar() {
        assertEquals(data.getVar("memes"), null);
    }

    @Test
    void addVar() {
        data.setVar("addVar", 1);
        data.addVar("addVar", 2);
        assertEquals(data.getVar("addVar"), 3);
    }

    @Test
    void addVar1() {
        data.setVar("addVar1", 1f);
        data.addVar("addVar1", 2f);
        assertEquals(data.getVar("addVar1"), 3f);
    }

    @Test
    void setVar() {
        wrapped("setVar", v -> Objects.equals(data.getVar(v), "hi"), v -> data.setVar(v, "hi"));
    }

    @Test
    void negVar() {
        data.setVar("negVar", 2f);
        data.negVar("negVar");
        assertEquals(data.getVar("negVar"), -2f);
    }

    @Test
    void getIntVar() {
        data.setVar("getIntVar", 10);
        assertEquals(data.getIntVar("getIntVar"), 10);
    }

    @Test
    void getBoolVar() {
        data.setVar("getBoolVar", true);
        assertEquals(data.getBoolVar("getBoolVar"), true);
    }

}