package game.core.minigame;

import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeCharacter;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by samtebbs on 03/03/2017.
 */
public class MiniGameHangman extends MiniGame1Player {

    String word = "";
    public static final String NUM_CHARS = "n", PROGRESS = "p", ENTERED = "e", WRONG = "w";
    public static final int MAX_WRONG = 10;

	/**
	 * Hangman mini-game constructor, the word is converted to a char array for
	 * display. Initialises number of wrong attempts and letters entered thus
	 * far.
	 * 
	 * @param player
	 *            A player name.
	 */
	public MiniGameHangman(String player) {
		super(player);
		initialising = true;
		setVar(NUM_CHARS, word.length());
		setVar(PROGRESS, makeCharArrayGreatAgain(word.length()));
		setVar(ENTERED, "");
		setVar(WRONG, 0);
		initialising = false;
	}

    @Override
    public void onInput(PlayerInputEvent event) {
        if(event.inputType instanceof InputTypeCharacter) {
            char ch = ((InputTypeCharacter) event.inputType).ch;
            String entered = (String) getVar(ENTERED);
            if(!Arrays.asList(entered.toCharArray()).contains(ch)) {
                IntStream indexes = IntStream.range(0, word.length()).filter(i -> word.charAt(i) == ch);
                if(indexes.count() > 0) {
                    char[] progress = (char[]) getVar(PROGRESS);
                    indexes.forEach(i -> progress[i] = ch);
                    setVar(PROGRESS, progress);
                    setVar(ENTERED, entered + ch);
                } else setVar(WRONG, (int) getVar(WRONG) + 1);
                if((int) getVar(WRONG) >= MAX_WRONG) addVar(AI_SCORE, 1);
                else if(word.equals(new String((char[]) getVar(PROGRESS)))) addStat(getPlayers().get(0), SCORE, 1);
            }
        }
    }
}
