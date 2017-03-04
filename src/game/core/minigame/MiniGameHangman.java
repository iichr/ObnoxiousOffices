package game.core.minigame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import org.newdawn.slick.Color;
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
	private ArrayList<Character> alreadyEntered;
	private int attempts = 0;
	private int PERMITTED_ATTEMPTS = 5;

//	private static Timer timer;
//	private static int interval = 30;

	public MiniGameHangman() throws SlickException {
		word = pickWord(setDifficulty());
		alreadyEntered = new ArrayList<Character>();
		//init();
	}

//	/**
//	 * Main game loop with timer. Manage victory and loss conditions
//	 */
//	public void init() {
//		displayWord();
//		// set up timer, 30 seconds, 1000 milliseconds delay
//		timer = new Timer();
//		timer.scheduleAtFixedRate(new TimerTask() {
//			public void run() {
//				// debugging - countdown timer
//				System.out.println(setInterval());
//			}
//		}, 1000, 1000);
//
//		while (!allGuessed() && !lost()) {
//			inputLetter();
//		}
//		if (allGuessed()) {
//			System.out.println("WIN!");
//			timer.cancel();
//		}
//	}

	
	// countdown in intervals of 1 second.
//	private static final int setInterval() {
//		if (interval == 1) {
//			timer.cancel();
//		}
//		return --interval;
//	}

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
	 *            The letter to check
	 * @return
	 */
	public boolean checkAlreadyEntered(char c) {
		return alreadyEntered.indexOf(c) >= 0;
	}

	/**
	 * Display a word.
	 * 
	 * @return TODO
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
		//System.out.println(word);
		return new String(displayed);
	}

	public void inputLetter(char userIn) {
		if (checkAlreadyEntered(userIn)) {
			System.out.println("Already entered: " + alreadyEntered.toString());
		} else {
			// user's char hasn't been encountered before
			alreadyEntered.add(userIn);
			displayWord();
			if (!isInWord(userIn)) {
				attempts++;
			}
		}
	}

	public boolean lost() {
		if (getAttempts() > PERMITTED_ATTEMPTS) {
			System.out.println("Game over.");
			//timer.cancel();
			return true;
		} else {
			return false;
		}
	}

	private int getAttempts() {
		return attempts;
	}

	public ArrayList<Character> getAlreadyEntered() {
		return alreadyEntered;
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

	// check if the word has been guessed by using the list of characters
	// entered so far
	public boolean allGuessed() {
		return alreadyEntered.containsAll(word.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.black);
		// temporarily use default background
		background.draw(x, y, width, height);

		// TODO determine height, width, scaling
		// initially draw the empty string
		wg.drawCenter(g, displayWord(), x + width / 2, y + height / 2 - height / 6, true, scale / 3);
		if(!alreadyEntered.isEmpty()) {
			wg.drawCenter(g, alreadyEntered.toString(), x + width / 2, y + height / 2, true, scale / 5);
		}
		wg.drawCenter(g, PERMITTED_ATTEMPTS - getAttempts() + " attempts left" , x + width / 2, y + height / 2 + height/6, true, scale / 4);
		
		
	}
}
