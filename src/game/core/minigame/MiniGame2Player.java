package game.core.minigame;

/**
 * Created by samtebbs on 18/02/2017.
 */
public abstract class MiniGame2Player extends MiniGame {

    public MiniGame2Player(String player1, String player2) {
        addPlayer(player1);
        addPlayer(player2);
        setStat(player1, SCORE, 0);
        setStat(player2, SCORE, 0);
    }

}
