package game.ui.player;

import org.newdawn.slick.Input;

import game.core.event.Events;
import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeCharacter;
import game.core.input.InputTypeInteraction;
import game.core.input.InputTypeMovement;
import game.core.input.InteractionType;
import game.core.input.MovementType;
import game.ui.components.ChatBox;

public class Controls {
	private final int MOVE_UP = Input.KEY_W;
	private final int MOVE_DOWN = Input.KEY_S;
	private final int MOVE_LEFT = Input.KEY_A;
	private final int MOVE_RIGHT = Input.KEY_D;

	private final int INTERACT = Input.KEY_E;

	private final int SELECTOR_UP = Input.KEY_UP;
	private final int SELECTOR_DOWN = Input.KEY_DOWN;
	private final int SELECTOR_BACK = Input.KEY_LEFT;

	private final int TOGGLE_OPTIONS = Input.KEY_ESCAPE;
	private final int TOGGLE_OVERVIEW = Input.KEY_TAB;
	private final int TOGGLE_CHAT = Input.KEY_LALT;

	private final int PONG_UP = Input.KEY_W;
	private final int PONG_DOWN = Input.KEY_S;

	/**
	 * Starts movement presses for core game
	 * 
	 * @param heldKey
	 *            The current movement key held
	 * @param key
	 *            The key being pressed
	 * @return heldKey The movement key to set
	 */
	public int coreMoveStart(int heldKey, int key) {
		if (key == MOVE_UP || key == MOVE_DOWN || key == MOVE_LEFT || key == MOVE_RIGHT) {
			return key;
		}
		return heldKey;
	}

	/**
	 * Finishes movement presses for core game
	 * 
	 * @param heldKey
	 *            The current movement key held
	 * @param key
	 *            The key being pressed
	 * @return heldKey The movement key to set
	 */
	public int coreMoveFinish(int heldKey, int key) {
		if (key == MOVE_UP || key == MOVE_DOWN || key == MOVE_LEFT || key == MOVE_RIGHT) {
			return -1;
		}
		return heldKey;
	}

	/**
	 * Manages input whilst using the action selector
	 * 
	 * @param actionSelector
	 *            The action selector object
	 * @param localPlayerName
	 *            The name of the local player
	 * @param key
	 *            The key being pressed
	 * @return The modified action selector object
	 */
	public ActionSelector selectorInput(ActionSelector actionSelector, String localPlayerName, int key) {
		if (key == INTERACT) {
			actionSelector.manageSelection(localPlayerName);
		} else if (key == SELECTOR_UP) {
			actionSelector.changeSelection(1);
		} else if (key == SELECTOR_DOWN) {
			actionSelector.changeSelection(-1);
		} else if (key == SELECTOR_BACK) {
			actionSelector.setHack(false);
		}
		return actionSelector;
	}

	/**
	 * Manages general interactions
	 * 
	 * @param localPlayerName
	 *            The name of the local player
	 * @param key
	 *            The key being pressed
	 */
	public void interaction(String localPlayerName, int key) {
		if (key == INTERACT) {
			Events.trigger(new PlayerInputEvent(new InputTypeInteraction(InteractionType.OTHER), localPlayerName));
		}
	}

	/**
	 * Toggles displaying the options menu based on input
	 * 
	 * @param options
	 *            The current options boolean
	 * @param key
	 *            The key being pressed
	 * @return The modified options boolean
	 */
	public boolean toggleOptions(boolean options, int key) {
		if (key == TOGGLE_OPTIONS) {
			return !options;
		}
		return options;
	}

	/**
	 * Toggles typing in the chat box based on input
	 * 
	 * @param cb
	 *            The current chat box
	 * @param key
	 *            The key being pressed
	 * @return The modified chat box
	 */
	public ChatBox toggleChat(ChatBox cb, int key) {
		if (key == TOGGLE_CHAT) {
			cb.toggleFocus();
		}
		return cb;
	}

	/**
	 * Opens the player overview based on input
	 * 
	 * @param showOverview
	 *            The current showOverview boolean
	 * @param key
	 *            The key being pressed
	 * @return The modified showOverview boolean
	 */
	public boolean openOverview(boolean showOverview, int key) {
		if (key == TOGGLE_OVERVIEW) {
			return true;
		}
		return showOverview;
	}

	/**
	 * Closes the player overview based on input
	 * 
	 * @param showOverview
	 *            The current showOverview boolean
	 * @param key
	 *            The key being pressed
	 * @return The modified showOverview boolean
	 */
	public boolean closeOverview(boolean showOverview, int key) {
		if (key == TOGGLE_OVERVIEW) {
			return false;
		}
		return showOverview;
	}

	/**
	 * Manages input for pong minigame
	 * 
	 * @param localPlayerName
	 *            The name of the local player
	 * @param key
	 *            The key being pressed
	 */
	public void pong(String localPlayerName, int key) {
		if (key == PONG_UP) {
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_UP), localPlayerName));
		} else if (key == PONG_DOWN) {
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_DOWN), localPlayerName));
		}
	}

	/**
	 * Manages input for hangman minigame
	 * 
	 * @param localPlayerName
	 *            The name of the local player
	 * @param c
	 *            The character being input
	 */
	public void hangman(String localPlayerName, char c) {
		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			Events.trigger(new PlayerInputEvent(new InputTypeCharacter(c), localPlayerName));
		}
	}
}
