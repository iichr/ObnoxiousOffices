package game.ui.interfaces;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * An interface for all shared values in the UI.
 * 
 * @author iichr
 * 
 */

public interface Vals {
	public static final String GAME_NAME = "Obnoxious Offices";

	// SCREEN SIZES
	Dimension screenRes = Toolkit.getDefaultToolkit().getScreenSize();
	public static final int SCREEN_WIDTH = (int) screenRes.getWidth();
	public static final int SCREEN_HEIGHT = (int) screenRes.getHeight();

	// STATE IDs
	public static final int EXIT = -1;	
	public static final int MENU_STATE = 0;
	public static final int PLAY_STATE = 1;
	public static final int OPTIONS_STATE = 2;
	public static final int RULES_STATE = 3;
	public static final int CHARACTER_SELECT_STATE = 4;

	// BUTTON SIZES
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 50;

	// The coords of the screen centre for a Menu Button
	public static final int BUTTON_ALIGN_CENTRE_W = SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2;
	public static final int BUTTON_ALIGN_CENTRE_H = SCREEN_HEIGHT / 2 - BUTTON_HEIGHT / 2;
}
