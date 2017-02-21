package game.core.minigame;

/**
 * Created by samtebbs on 18/02/2017.
 */
public abstract class MiniGame2Player extends MiniGame {

    protected final String player1, player2;

    public MiniGame2Player(String player1, String player2) {
        addPlayer(player1);
        addPlayer(player2);
        setStat(player1, SCORE, 0);
        setStat(player2, SCORE, 0);
        this.player1 = player1;
        this.player2 = player2;
    }

}
