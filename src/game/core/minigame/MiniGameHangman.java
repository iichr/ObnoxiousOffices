package game.core.minigame;

import java.util.Arrays;
import java.util.Random;

/**
 * @author iichr
 *
 */
public class MiniGameHangman {

	private String[] dict = { "replace", "with", "dictionary", "ofatleast", "100words" };
	String word;
	private int attempts = 0;
	private int PERMITTED_ATTEMPTS;
	private boolean guessed = false;
	private char[] alreadyEntered;

	public MiniGameHangman() {
		word = pickWord(dict);
		alreadyEntered = new char[word.length()];
		setDifficulty();
	}

	public void setDifficulty() {
		// set dictionary
		// set PERMITTED_ATTEMPTS
	}

	/**
	 * Check whether a letter has already been input by the user
	 * 
	 * @param c
	 *            The letter to check for
	 * @param entered
	 *            The array of letters entered so far
	 * @return
	 */
	private boolean checkAlreadyEntered(char c, char[] entered) {
		return Arrays.asList(entered).indexOf(c) >= 0;
	}

	/**
	 * Display a word.
	 * 
	 * @param word
	 *            The word to display
	 * @param entered
	 *            The array of letters entered so far
	 */
	private void displayWord(String word, char[] entered) {
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			if (checkAlreadyEntered(letter, entered))
				// debugging
				// if encountered before, display
				System.out.print(letter);
			else
				System.out.print('_');
		}

	}

	private void inputLetter(String word, char[] entered) {

	}

	private boolean lost() {
		if (attempts >= PERMITTED_ATTEMPTS) {
			return true;
		}
		return false;
	}

	private boolean won() {
		return false;
	}

	private int getAttempts() {
		return attempts;
	}

	private char[] getAlreadyEntered() {
		return alreadyEntered;
	}

	/**
	 * Pick a random word given an array of strings
	 * 
	 * @param dict
	 * @return A random word.
	 */
	private String pickWord(String[] dict) {
		Random randGenerator = new Random();
		int i = randGenerator.nextInt(dict.length);
		word = dict[i];

		return word;
	}
}
