package game.core.minigame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.newdawn.slick.Input;

/**
 * WIP 20/02 !!!!
 * Eventually this is going to become a testing class.
 * 
 * @author iichr
 */
public class MiniGameHangman_Old {

	private String[] dict = { "replace", "with", "dictionary", "ofatleast", "words" };
	String word;
	private int attempts = 0;
	private int PERMITTED_ATTEMPTS;
	private boolean guessed = false;
	private ArrayList<Character> alreadyEntered;
	private char[] wordAsChars;
	
	private Scanner input = new Scanner(System.in);

	public MiniGameHangman_Old() {
		word = pickWord(dict);
		wordAsChars = word2chars(word);
		alreadyEntered = new ArrayList<Character>();
		setDifficulty();
		
		while(!allGuessed(word, alreadyEntered)) {
			inputLetter(word, alreadyEntered);
		}
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
		if(Character.isLetter(userIn)) {
			if(checkAlreadyEntered(userIn, entered)) {
				// prompt again
			} else {
				// it's not been encountered before
				entered.add(userIn);
					if(isInWord(userIn, wordAsChars)) {
						displayWord(word, entered);
					} else {
						attempts++;
						displayWord(word, entered);
					}
			}
		} else {
			System.out.println("Not a valid char.");
		}	
	}

	private boolean lost() {
		if (getAttempts() >= PERMITTED_ATTEMPTS)
			return true;
		else
			return false;
	}

	private boolean won() {
		if (getAttempts() >= PERMITTED_ATTEMPTS) 
			return false;
		else
			return true;
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
	
	// Convert a string to an array of its chars.
	// REDUNDANT - replaced with collector
	private char[] word2chars(String word) {
		char[] charArray = word.toCharArray();
		return charArray;
	}
	
	// check if a given char is in a word char array
	private boolean isInWord(char c, char[] word) {
		for(char i: word) {
			if(c == i)
				return true;
				break;
		}
		return false;
	}
	
	private boolean allGuessed(String word, ArrayList<Character> entered) {
		return entered.containsAll(word.chars().mapToObj(c -> (char) c).collect(Collectors.toList()));
	}
}
