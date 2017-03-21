package game.core.player.state;

import game.core.player.Player;

/**
 * Created by samtebbs on 21/03/2017.
 */
class PlayerStateSitting extends PlayerState {

    @Override
    public boolean cancelsOnMove() {
        return true;
    }

    @Override
    public void onStart(Player player) {

    }

    @Override
    public void onEnd(Player player) {

    }
}
