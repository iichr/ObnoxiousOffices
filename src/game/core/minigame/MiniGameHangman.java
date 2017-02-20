package game.core.minigame;

/**
 * @author iichr
 *
 */
public class MiniGameHangman {
	
	private String[] dict = {"replace", "with", "dictionary", "ofatleast" , "100words"};
	private int attempts = 0;
	private int PERMITED_ATTEMPTS;
	private boolean guessed = false;
	private char[] alreadyEntered;

	public MiniGameHangman() {
		// generate a word at random from list
		setDifficulty();
	}

	public void setDifficulty() {
		// set dictionary
		// set PERMITTED_ATTEMPTS
	}

	private boolean checkAlreadyEntered(char c, char[] entered) {

	}

	private boolean displayWord(String word, char[] entered) {

	}

	private void inputLetter(String word, char[] entered) {

	}

	private boolean lost() {
		if(attempts >= PERMITTED_ATTEMPTS) {
			return true;
		}
		return false;
	}

	private boolean won() {

	}

	private int getAttempts() {
		return attempts;
	}

	private char[] getAlreadyEntered() {
		return alreadyEntered;
	}
}
