package game.core.minigame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.ui.overlay.PopUpOverlay;

/**
 * A hangman mini-game
 * 
 * @author iichr
 */

public class MiniGameHangman extends PopUpOverlay {

	private String word;
	private int attempts = 0;
	private int PERMITTED_ATTEMPTS = 5;
	private boolean guessed = false;
	private ArrayList<Character> alreadyEntered;

	private Scanner input = new Scanner(System.in);
	private static Timer timer;
	private static int interval = 30;

	public MiniGameHangman() throws SlickException {
		super();
		word = pickWord(setDifficulty());
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
		if (interval == 1) {
			timer.cancel();
		}
		return --interval;
	}

	private ArrayList<String> setDifficulty() {
		// TODO set PERMITTED_ATTEMPTS
		ArrayList<String> dictionary = new ArrayList<String>();
		String str;
		BufferedReader in = null;
		try {
			try {
				in = new BufferedReader(new FileReader("data/long-words.txt"));
				while ((str = in.readLine()) != null) {
					dictionary.add(str);
				}
			} finally {
				in.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dictionary;
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
	 * Display a string representation of a word with blanks in place of
	 * characters not yet guessed.
	 * 
	 * @param word
	 *            The word to display
	 * @param entered
	 *            The array of letters entered so far
	 * @return The string representation to be displayed.
	 */
	private String displayWord(String word, ArrayList<Character> entered) {
		char[] displayed = new char[word.length()];

		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			if (checkAlreadyEntered(letter, entered))
				// if encountered before, display
				displayed[i] = letter;
			else
				// guessed = false;
				displayed[i] = '_';
		}
		return new String(displayed);
	}

	private void inputLetter(String word, ArrayList<Character> entered) {
		char userIn = input.next().toLowerCase().charAt(0);
		if (Character.isLetter(userIn)) {
			if (checkAlreadyEntered(userIn, entered)) {
				System.out.println("Already entered: " + entered.toString());
			} else {
				// user's char hasn't been encountered before, add to entered
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
	private String pickWord(ArrayList<String> dict) {
		Random randGenerator = new Random();
		int i = randGenerator.nextInt(dict.size());
		word = dict.get(i);

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

	/*****
	 * RENDERING
	 *****/

	@Override
	public void render(Graphics g) {
		// temporarily use default background
		background.draw(x, y, width, height);
		
		// TODO determine height, width, scaling
		// initially draw the empty string
		wg.drawCenter(g, displayWord(word, alreadyEntered), x + width / 2, y + height / 2 - height/6, true, scale / 3);
		
	}
}
