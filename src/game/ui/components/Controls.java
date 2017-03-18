package game.ui.components;

import java.util.HashMap;

import org.newdawn.slick.Input;

import game.core.event.Events;
import game.core.event.player.PlayerInputEvent;
import game.core.input.InputTypeCharacter;
import game.core.input.InputTypeInteraction;
import game.core.input.InputTypeMovement;
import game.core.input.InteractionType;
import game.core.input.MovementType;
import game.ui.player.ActionSelector;

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
	
	public HashMap<String, String> allControls;
	
	public Controls(){
		allControls = new HashMap<String, String>();
		
		allControls.put("Move Up", Input.getKeyName(MOVE_UP));
		allControls.put("Move Down", Input.getKeyName(MOVE_DOWN));
		allControls.put("Move Left", Input.getKeyName(MOVE_LEFT));
		allControls.put("Move Right", Input.getKeyName(MOVE_RIGHT));
		
		allControls.put("Interact", Input.getKeyName(INTERACT));
		
		allControls.put("Selector Up", Input.getKeyName(SELECTOR_UP));
		allControls.put("Selector Down", Input.getKeyName(SELECTOR_DOWN));
		allControls.put("Selector Back", Input.getKeyName(SELECTOR_BACK));
		
		allControls.put("Show Hide Options Menu", Input.getKeyName(TOGGLE_OPTIONS));
		allControls.put("Show Hide Player Overview", Input.getKeyName(TOGGLE_OVERVIEW));
		allControls.put("Show Hide Chat", Input.getKeyName(TOGGLE_CHAT));
		
		allControls.put("Move Up in Pong", Input.getKeyName(PONG_UP));
		allControls.put("Move Down in Pong", Input.getKeyName(PONG_DOWN));
	}
	
	/**
	 * Manages the movement to make based on the key currently held by the
	 * player
	 * 
	 * @param actionSelector
	 *            The action selector object
	 * @param localPlayerName
	 *            The name of the local player
	 * @param heldKey
	 *            The key currently being held
	 * @return
	 */
	public ActionSelector manageMovement(ActionSelector actionSelector, String localPlayerName, int heldKey) {
		if (heldKey == MOVE_UP) {
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_UP), localPlayerName));
			actionSelector.setHack(false);
			actionSelector.setAction(0);
		} else if (heldKey == MOVE_DOWN) {
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_DOWN), localPlayerName));
			actionSelector.setHack(false);
			actionSelector.setAction(0);
		} else if (heldKey == MOVE_RIGHT) {
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_RIGHT), localPlayerName));
			actionSelector.setHack(false);
			actionSelector.setAction(0);
		} else if (heldKey == MOVE_LEFT) {
			Events.trigger(new PlayerInputEvent(new InputTypeMovement(MovementType.MOVE_LEFT), localPlayerName));
			actionSelector.setHack(false);
			actionSelector.setAction(0);
		}
		return actionSelector;
	}

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
		if (heldKey == key && (key == MOVE_UP || key == MOVE_DOWN || key == MOVE_LEFT || key == MOVE_RIGHT)) {
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
			actionSelector.setAction(1);
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
			Events.trigger(new PlayerInputEvent(new InputTypeInteraction(new InteractionType.InteractionTypeOther()), localPlayerName));
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

	public int pongMoveStart(int heldKey, int key) {
		if (key == MOVE_UP || key == MOVE_DOWN) {
			return key;
		}
		return heldKey;
	}

	public int pongMoveFinish(int heldKey, int key) {
		if (heldKey == key && (key == MOVE_UP || key == MOVE_DOWN)) {
			return -1;
		}
		return heldKey;
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
