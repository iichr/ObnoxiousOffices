package game.core.minigame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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

public class MiniGameHangmanChris extends PopUpOverlay {

	private String word;
	private ArrayList<Character> alreadyEntered;
	private int attempts = 0;
	private int PERMITTED_ATTEMPTS = 5;

	// private static Timer timer;
	// private static int interval = 30;

	public MiniGameHangmanChris() throws SlickException {
		word = pickWord(setDifficulty());
		alreadyEntered = new ArrayList<Character>();
		// init();
	}

	// loads words from wordlist provided.
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dictionary;
	}

	/**
	 * Check whether a letter has already been input by the user
	 * 
	 * @param c
	 *            The letter to check
	 * @return
	 */
	public boolean checkAlreadyEntered(char c) {
		return alreadyEntered.indexOf(c) >= 0;
	}

	/**
	 * Display a word.
	 * 
	 * @return the word to be guessed; Blanks in place of missing chars.
	 */
	public String displayWord() {
		char[] displayed = new char[word.length()];
		for (int i = 0; i < word.length(); i++) {
			char letter = word.charAt(i);
			if (checkAlreadyEntered(letter))
				displayed[i] = letter;
			else
				displayed[i] = '_';
		}
		// System.out.println(word);
		return new String(displayed);
	}

	public void inputLetter(char userIn) {
		if (checkAlreadyEntered(userIn)) {
		} else {
			// user's char not encountered before
			alreadyEntered.add(userIn);
			displayWord();
			if (!isInWord(userIn)) {
				attempts++;
			}
			getAttempts();
		}
	}

	public boolean lost() {
		if (PERMITTED_ATTEMPTS == getAttempts()) {
			System.out.println("Hangman - Game over.");
			// timer.cancel();
			return true;
		} else {
			return false;
		}
	}

	private int getAttempts() {
		return attempts;
	}

	private String getAlreadyEntered(ArrayList<Character> entered) {
		// Null pointer when using delimiters or brackets! 
		// Possibly owing to lack of , /COMMA/ character in the rendering ???! 
		String str = entered.stream().map(e -> e.toString()).collect(Collectors.joining());
		return str;
	}

	public void addToAlreadyEntered(char c) {
		alreadyEntered.add(c);
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
	private boolean isInWord(char c) {
		return word.indexOf(c) >= 0;
	}

	// check whether word has been guessed
	public boolean allGuessed() {
		if (alreadyEntered.containsAll(word.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))) {
			System.out.println("Hangman - You won!");
			return true;
		}
		return false;
	}

	
	@Override
	public void render(Graphics g) {
		// temporarily use the default background
		background.draw(x, y, width, height);

		wg.drawCenter(g, displayWord(), x + width / 2, y + height / 2 - height / 6, true, scale / 3);
		wg.drawCenter(g, "GUESSED    " + getAlreadyEntered(alreadyEntered), x + width / 2, y + height / 2, true, scale / 3);
		wg.drawCenter(g, PERMITTED_ATTEMPTS - getAttempts() + " attempts left", x + width / 2,
				y + height / 2 + height / 6, true, scale / 4);
	}
}
