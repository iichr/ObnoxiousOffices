package game.ui.buttons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import game.ui.interfaces.Vals;

/**
 * Used to create a new labelled button for the menu. Label is centred to the
 * middle of the button.
 *
 */
public class MenuButton extends Button {

	public MenuButton(float x, float y, float width, float height, Image normal, Image alternate) {
		super(x, y, width, height, normal, alternate);
	}
    private int CURRENT = 0;
    private long lastInput = System.currentTimeMillis();
    //An array of buttons that follows the order of menu
    private int buttons[] = new int[]{  Vals.CHARACTER_SELECT_STATE,
                                        Vals.OPTIONS_STATE,
                                        Vals.RULES_STATE,
                                        Vals.EXIT
                                    };

    private static final long serialVersionUID = -6073162297979548251L;
	/**
	 * Update method for the button - enter a new state on button click.
	 * 
	 * @param gc
	 *            The game container
	 * @param game
	 *            The game
	 * @param mouseX
	 *            The x coord of the mouse cursor
	 * @param mouseY
	 *            The y coord of the mouse cursor
	 * @param stateID
	 *            The new state to enter.
	 */
	public void update(GameContainer gc, StateBasedGame game, float mouseX, float mouseY, int stateID) {
		Input input = gc.getInput();

		if (inRange(mouseX, mouseY)) {
			button = select;
			if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
				if (stateID == Vals.EXIT) {
					gc.exit();
				} else {
					game.enterState(stateID);
				}
			}
		} else {
			button = unselect;
		}
	}

	/** Only use this update method for keyboard controlled menu buttons
     *
     * */
	public void update(GameContainer gc,StateBasedGame game){
        Input input = gc.getInput();
        if (System.currentTimeMillis() > lastInput + Vals.INPUT_INTERVAL) {
            if (input.isKeyPressed(Vals.DOWN)) {
                if (CURRENT == (buttons.length-1)) {
                    CURRENT = 0;
                    button=select;
                } else {
                    CURRENT += 1;
                    button=unselect;

                }

            }

            if (input.isKeyPressed(Vals.UP)) {
                if (CURRENT == 0) {
                    CURRENT = (buttons.length-1);
                    button=select;
                } else {
                    CURRENT -= 1;
                    button=unselect;
                }

            }
            if (input.isKeyPressed(Vals.ENTER)) {
                switch (buttons[CURRENT]) {
                    case Vals.CHARACTER_SELECT_STATE:
                        game.enterState(Vals.CHARACTER_SELECT_STATE);
                        break;
                    case Vals.OPTIONS_STATE:
                        game.enterState(Vals.OPTIONS_STATE);
                        break;
                    case Vals.RULES_STATE:
                        game.enterState(Vals.RULES_STATE);
                        break;
                    case Vals.EXIT:
                        gc.exit();
                        break;
                }
            }
            lastInput = System.currentTimeMillis();
        }
    }


}
