package game.core.player;

/**
 * Created by samtebbs on 15/01/2017.
 */
public class Player {

    public final String name;
    public final PlayerStatus status = new PlayerStatus();

    public Player(String name) {
        this.name = name;
    }

    public void update() {
        status.update(this);
    }

}
