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
 * Created by samtebbs on 03/03/2017.
 */
public class MiniGameHangman extends MiniGame1Player {

    String word = pickWord(setDifficulty());
    public static final String NUM_CHARS = "n", PROGRESS = "p", ENTERED = "e", WRONG = "w";
    public static final int MAX_WRONG = 5;

    public MiniGameHangman(String player) {
        super(player);
        setVar(NUM_CHARS, word.length());
        setVar(PROGRESS, makeCharArrayGreatAgain(word.length()));
        setVar(ENTERED, "");
        setVar(WRONG, 0);
    }

    private String pickWord(ArrayList<String> dict) {
        Random randGenerator = new Random();
        int i = randGenerator.nextInt(dict.size());
        word = dict.get(i);
        return word;
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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    private char[] makeCharArrayGreatAgain(int len) {
        char[] memes = new char[len];
        for (int i = 0; i < memes.length; i++) memes[i] = '_';
        return memes;
    }

    @Override
    public void onInput(PlayerInputEvent event) {
        if(event.inputType instanceof InputTypeCharacter) {
            char ch = ((InputTypeCharacter) event.inputType).ch;
            String entered = (String) getVar(ENTERED);
            processCharacter(ch, entered);
        }
    }

    private void processCharacter(char ch, String entered) {
        if(!Arrays.asList(entered.toCharArray()).contains(ch)) {
            List<Integer> indexes = IntStream.range(0, word.length()).filter(i -> word.charAt(i) == ch).boxed().collect(Collectors.toList());
            if(indexes.size() > 0) {
                char[] progress = (char[]) getVar(PROGRESS);
                indexes.forEach(i -> progress[i] = ch);
                setVar(PROGRESS, progress);
            } else setVar(WRONG, (int) getVar(WRONG) + 1);
            setVar(ENTERED, entered + ch);
            if((int) getVar(WRONG) >= MAX_WRONG) addVar(AI_SCORE, 1);
            else if(word.equals(new String((char[]) getVar(PROGRESS)))) addStat(getPlayers().get(0), SCORE, 1);
        }
    }

    public String getDisplayWord() {
        return new String((char[]) getVar(PROGRESS));
    }

    public String getEntered() {
        return (String) getVar(ENTERED);
    }

    public int getAttemptsLeft() {
        return MAX_WRONG - getIntVar(WRONG);
    }

}
