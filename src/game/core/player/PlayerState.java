package game.core.player;

/**
 * Created by samtebbs on 28/02/2017.
 */
public abstract class PlayerState {

    public static final PlayerState sitting = new PlayerStateSitting();

    private static class PlayerStateSitting extends game.core.player.PlayerState {

        @Override
        public void onStart(Player player) {

        }

        @Override
        public void onEnd(Player player) {

        }
    }

    public abstract void onStart(Player player);
    public abstract void onEnd(Player player);

}
