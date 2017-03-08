package game.core.minigame;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

/**
 * A hangman mini-game
 * 
 * @author iichr
 */

public class MiniGameHangmanOld {

	private String[] dict = { "replace", "with", "dictionary", "ofatleast", "words" };
	String word;
	private int attempts = 0;
	private int PERMITTED_ATTEMPTS = 5;
	private boolean guessed = false;
	private ArrayList<Character> alreadyEntered;

	private Scanner input = new Scanner(System.in);
	private static Timer timer;
	private static int interval = 30;

	public MiniGameHangmanOld() {
		word = pickWord(dict);
		alreadyEntered = new ArrayList<Character>();

		setDifficulty();

		init(word, alreadyEntered);
	}

	/**
	 * Constructor for testing purposes only!
	 * 
	 * @param word
	 *            The word to be guessed.
	 */
	public MiniGameHangmanOld(String word) {
		this.word = word;
		alreadyEntered = new ArrayList<Character>();
		init(word, alreadyEntered);
	}

	/**
	 * Main game loop with timer. Manage victory and loss conditions
	 * 
	 * @param word
	 * @param alreadyEntered
	 */
	public void init(String word, ArrayList<Character> alreadyEntered) {
		displayWord(word, alreadyEntered);
		// set up timer, 30 seconds, 1000 milliseconds delay
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				// debugging - countdown timer
				System.out.println(setInterval());
			} 
		}, 1000, 1000);

		while (!allGuessed(word, alreadyEntered) && !lost()) {
				inputLetter(word, alreadyEntered);
		}
		if (allGuessed(word, alreadyEntered)) {
			System.out.println("WIN!");
			timer.cancel();
		}
	}

	// countdown in intervals of 1 second.
	private static final int setInterval() {
		if(interval == 1) {
			timer.cancel();
		}
		return --interval;
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
	 *            The arraylist of letters entered so far
	 * @return
	 */
	private boolean checkAlreadyEntered(char c, ArrayList<Character> entered) {
		return entered.indexOf(c) >= 0;
	}

	/**
	 * Display a word.
	 * 
	 * @param word
	 *            The word to display
	 * @param entered
	 *            The array of letters entered so far
	 */
	private void displayWord(String word, ArrayList<Character> entered) {
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			if (checkAlreadyEntered(letter, entered))
				// debugging
				// if encountered before, display
				System.out.print(letter);
			else
				System.out.print('_');
			guessed = false;
		}
		System.out.println();

	}

	private void inputLetter(String word, ArrayList<Character> entered) {
		char userIn = input.next().toLowerCase().charAt(0);
		if (Character.isLetter(userIn)) {
			if (checkAlreadyEntered(userIn, entered)) {
				System.out.println("Already entered: " + entered.toString());
			} else {
				// user's char hasn't been encountered before
				entered.add(userIn);
				displayWord(word, entered);
				if (!isInWord(userIn, word)) {
					attempts++;
				}
			}
		} else {
			System.out.println("Not a valid char.");
		}
	}

	private boolean lost() {
		if (getAttempts() > PERMITTED_ATTEMPTS) {
			System.out.println("Game over.");
			timer.cancel();
			return true;
		} else {
			return false;
		}
	}

	private int getAttempts() {
		return attempts;
	}

	private ArrayList<Character> getAlreadyEntered() {
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

	// check if a given char is in a word
	private boolean isInWord(char c, String word) {
		return word.indexOf(c) >= 0;
	}

	// check if the word has been guessed by using the list of characters
	// entered so far
	private boolean allGuessed(String word, ArrayList<Character> entered) {
		return entered.containsAll(word.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
	}


}
