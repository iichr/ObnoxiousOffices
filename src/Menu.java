
import java.util.Date;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu implements GameState {
	private MenuButton playButton, optionsButton, rulesButton, exitButton;
	private String mouseCoords = "No input yet!";

	public Menu(int state) {

	}

	@Override
	public void enter(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return Vals.MENU_STATE;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(false);
		
		Image play = new Image("./res/play.png");
		Image playR = new Image("./res/playR.png");
		playButton = new MenuButton(250.0f, 100.0f, 200, 50, play, playR);
		
		Image options = new Image("./res/options.png");
		Image optionsR = new Image("./res/optionsR.png");
		optionsButton = new MenuButton(250.0f, 200.0f, 200, 50, options, optionsR);
		
		Image rules = new Image("./res/rules.png");
		Image rulesR = new Image("./res/rulesR.png");
		rulesButton = new MenuButton(250.0f, 300.0f, 200, 50, rules, rulesR);
		
		Image exit = new Image("./res/exit.png");
		Image exitR = new Image("./res/exitR.png");
		exitButton = new MenuButton(250.0f, 400.0f, 200, 50, exit, exitR);
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {

	}

	/*
	 * The main board of the menu screen
	 */
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// debugging
		g.drawString(mouseCoords, 10, 50);
		g.drawString(new Date().toString(), 450, 30);
		
		//draw buttons
		playButton.render(g);
		optionsButton.render(g);
		rulesButton.render(g);
		exitButton.render(g);
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		Input input = gc.getInput();
		
		int mouseX = Mouse.getX();
		int mouseY = gc.getHeight() - Mouse.getY();
		mouseCoords = mouseX + " ," + mouseY;
		
		//set button properties
		playButton.onClick(gc, game, mouseX, mouseY, Vals.PLAY_STATE);
		optionsButton.onClick(gc, game, mouseX, mouseY, Vals.OPTIONS_STATE);	
		rulesButton.onClick(gc, game, mouseX, mouseY, Vals.RULES_STATE);	
		exitButton.onClick(gc, game, mouseX, mouseY, Vals.EXIT);	
	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAcceptingInput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setInput(Input arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int arg0, char arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerButtonPressed(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerButtonReleased(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDownPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerDownReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerLeftPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerLeftReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerRightPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerRightReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerUpPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void controllerUpReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

}