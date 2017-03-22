package game.core.player;

import game.core.player.action.PlayerAction;
import game.core.player.action.PlayerActionDrink;
import game.core.player.action.PlayerActionSleep;
import game.core.player.effect.PlayerEffect;
import game.core.player.effect.PlayerEffectCoffeeBuzz;
import game.core.player.effect.PlayerEffectSleeping;
import game.core.player.state.PlayerState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static game.core.TestUtil.*;

/**
 * Created by samtebbs on 21/03/2017.
 */
class PlayerStatusTest {

    public static PlayerStatus status = PlayerTest.player.status;
    private static PlayerAction forcedAction = new PlayerActionSleep(status.player) {{
        forced = true;
    }};

    @Test
    void testState() {
        wrappedTest(PlayerState.sitting, status::hasState, status::addState, status::removeState);
    }

    @Test
    void testEffect() {
        wrappedTest(new PlayerEffectCoffeeBuzz(100, status.player), e -> status.hasEffect(e.getClass()), status::addEffect, e -> status.removeEffect(e.getClass()));
    }

    @Test
    void testAction() {
        wrappedTest(new PlayerActionDrink(status.player), a -> status.hasAction(a.getClass()), status::addAction, a -> status.removeAction(a.getClass()));
    }

    @Test
    void testAttribute() {
        wrappedTest(0.5, i -> status.getAttribute(PlayerStatus.PlayerAttribute.FATIGUE) == i, i -> status.setAttribute(PlayerStatus.PlayerAttribute.FATIGUE, i), i -> status.setAttribute(PlayerStatus.PlayerAttribute.FATIGUE, 0));
    }

    @Test
    void testCanMove() {
        wrappedTest(forcedAction, action -> !status.canMove(), status::addAction, a -> status.removeAction(a.getClass()));
    }

    @Test
    void testCanInteract() {
        wrappedTest(forcedAction, a -> !status.canInteract(), status::addAction, a -> status.removeAction(a.getClass()));
    }

}