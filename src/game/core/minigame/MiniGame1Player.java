package game.core.minigame;

/**
 * Created by samtebbs on 04/03/2017.
 */
public abstract class MiniGame1Player extends MiniGame {

    public static final String AI_SCORE = "ai_score";

    public MiniGame1Player(String player) {
        super(player);
        setVar(AI_SCORE, 0);
    }

    @Override
    public void update() {
        super.update();
        if((int) getVar(AI_SCORE) == MAX_SCORE) end("AI");
    }
}
