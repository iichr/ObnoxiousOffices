package game.core.minigame;

import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeCharacter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * A class to handle Hangman's game logic and communication.
 *
 * Created by samtebbs on 03/03/2017. Jointly modified by samtebbs and iichr on
 * 10/03/2017.
 */
public class MiniGameHangman extends MiniGame1Player {

    // Pick a random word from the dictionary;
    String word = pickWord(initDictionary());
    public static final String NUM_CHARS = "n", PROGRESS = "p", ENTERED = "e", WRONG = "w";
    // set maximum attempts to 5
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
        setVar(NUM_CHARS, word.length());
        setVar(PROGRESS, new String(makeCharArrayGreatAgain(word.length())));
        setVar(ENTERED, "");
        setVar(WRONG, 0);
    }

    // Load all words from the word list(dictionary) provided.
    private ArrayList<String> initDictionary() {
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
     * Pick a random word given an array of strings (a dictionary)
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

    /**
     * Used to display an initial word as underscores.
     *
     * @param len
     *            The word length
     * @return a word-long char array of underscores.
     */
    private char[] makeCharArrayGreatAgain(int len) {
        char[] memes = new char[len];
        for (int i = 0; i < memes.length; i++)
            memes[i] = '_';
        return memes;
    }

    @Override
    public void onInput(PlayerInputEvent event) {
        if (event.inputType instanceof InputTypeCharacter) {
            char ch = ((InputTypeCharacter) event.inputType).ch;
            String entered = (String) getVar(ENTERED);
            processCharacter(ch, entered);
        }
    }

    /**
     * Used to process a character and determine whether it is in the word, has
     * it already been entered, and updating game progress.
     *
     * @param ch
     *            The char input by the user
     * @param entered
     *            The chars already entered.
     */
    private void processCharacter(char ch, String entered) {
        // if the char hasn't been encountered before
        if (!entered.contains(ch + "")) {
            // store the indexes where the char is located at
            List<Integer> indexes = IntStream.range(0, word.length()).filter(i -> word.charAt(i) == ch).boxed()
                    .collect(Collectors.toList());
            if (indexes.size() > 0) {
                char[] progress = ((String) getVar(PROGRESS)).toCharArray();
                indexes.forEach(i -> progress[i] = ch);
                // word to be displayed has that char visible
                setVar(PROGRESS, new String(progress));
            } else addVar(WRONG, 1);

            setVar(ENTERED, entered + ch);
            // HANDLE WIN/LOSS CONDITIONS
            // if there are no more attempts remaining => AI wins
            if (getAttemptsLeft() <= 0) addVar(AI_SCORE, 1);
                // if word is guessed => Player wins.
            else if (word.equals(getVar(PROGRESS))) setStat(getPlayers().get(0), SCORE, MAX_SCORE);
        }
    }

    public String getDisplayWord() {
        return (String)getVar(PROGRESS);
    }

    public String getEntered() {
        return (String) getVar(ENTERED);
    }

    public int getAttemptsLeft() {
        return MAX_WRONG - getIntVar(WRONG);
    }

}
