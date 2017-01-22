/**
 * An interface for all shared values in the UI.
 * 
 * @author iichr
 * 
 */

public interface Vals {
	public static final String GAME_NAME = "Obnoxious Offices";

	// SCREEN SIZES
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;

	// STATE IDs
	public static final int MENU_STATE = 0;
	public static final int PLAY_STATE = 1;
	public static final int OPTIONS_STATE = 2;
	public static final int RULES_STATE = 3;
	public static final int EXIT = 4;

	// BUTTON SIZES
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 50;

	// The coords of the screen centre for a Menu Button
	public static final int BUTTON_ALIGN_CENTRE_W = SCREEN_WIDTH / 2 - BUTTON_WIDTH / 2;
	public static final int BUTTON_ALIGN_CENTRE_H = SCREEN_HEIGHT / 2 - BUTTON_HEIGHT / 2;
}