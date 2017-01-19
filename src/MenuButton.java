import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.*;

/**
 * Used to create a new labeled button for the menu.
 * Label is centered to the middle of the button.
 * @author iichr
 *
 */
public class MenuButton extends Rectangle {
	
	private static final long serialVersionUID = -4269697269366852233L;
	private String buttonText;
	
	/** 
	 * Create a new menu button.
	 * @param x The x coord of the top left of the button
	 * @param y The y coord of the top right of the button
	 * @param width The button's width
	 * @param height The button's height
	 * @param text The text to be displayed at the centre of the button.
	 */
	public MenuButton(float x, float y, float width, float height, String text) {
		super(x, y, width, height);
		buttonText = text;
	}
	
	/**
	 * Renders the button to the graphics context.
	 * Uses the label's width and height for proper centring.
	 * @param g The graphics context to render to
	 * @param strWidth The width of the label
	 * @param strHeight The height of the label
	 */
	public void render(Graphics g, int strWidth, int strHeight) {
		//g.setColor(Color.lightGray);
		g.fill(this);
		g.drawString(buttonText, (this.x + this.width/2) - (strWidth/2), (this.y + this.height/2) - (strHeight/2));
	}
	
	/**
	 * Check if certain coords fall within the boundaries of the button.
	 * @param x The x coord to check
	 * @param y The y coord to check
	 * @return Whether (x,y) is inside the button.
	 */
	public boolean inRange(float x, float y) {
		return this.contains(x, y);
	}
	
	/**
	 * Update method for the button - enter a new state on button click.
	 * @param input
	 * @param game
	 * @param mouseX The x coord of the mouse cursor
	 * @param mouseY The y coord of the mouse cursor
	 * @param stateID The new state which to enter.
	 */
	public void onClick(Input input, StateBasedGame game, int mouseX, int mouseY, int stateID) {
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			if(inRange((float)mouseX, (float)mouseY)) {
				game.enterState(stateID);
			}
		}
	}
	
	/**
	 * Get button label. Used to extract string properties during font rendering.
	 * @return The label of the button.
	 */
	public String getString() {
		return buttonText;
	}
}
