import java.util.Date;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play implements GameState {
	String mouse = "No input yet!";
	float moveX = 300;
	float moveY = 150;
	Animation circle, staying, moving;
	int[] duration = { 200, 200 };
	boolean quit = false;

	public Play(int state) {

	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {

	}

	@Override
	public int getID() {

		return 1;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Image[] stay = { new Image("./res/circle.png"), new Image("./res/circle.png") };
		Image[] move = { new Image("./res/circle2.png"), new Image("./res/circle2.png") };

		staying = new Animation(stay, duration, false);
		moving = new Animation(move, duration, false);
		circle = staying;
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		g.drawString(mouse, 50, 50);
		circle.draw(moveX, moveY);
		g.drawString("Circle at:(" + moveX + "," + moveY + ")", 350, 50);
		g.drawString(new Date().toString(), 450, 30);
		if (quit == true) {
			g.drawString("Resume (R) Main Menu (M) Quit (Q)", 90, 150);
			if (quit == false) {
				g.clear();
			}

		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int xlocation = Mouse.getX();
		int ylocation = Mouse.getY();
		mouse = "Mouse postion at (" + xlocation + " , " + ylocation + ")";
		if (input.isKeyDown(Input.KEY_UP)) {
			circle = moving;
			moveY -= 0.5;
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			circle = moving;
			moveY += 0.5;
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			circle = moving;
			moveX -= 0.5;
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			circle = moving;
			moveX += 0.5;
		} else if (input.isKeyDown(Input.KEY_ESCAPE)) {
			quit = true;
		} else if (quit) {
			if (input.isKeyDown(Input.KEY_R)) {
				quit = false;
			}
			if (input.isKeyDown(Input.KEY_M)) {

				sbg.enterState(0);

			}
			if (input.isKeyDown(Input.KEY_Q)) {
				gc.exit();
			}
		} else {
			circle = staying;
		}

	}

	@Override
	public void mouseClicked(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void mouseDragged(int arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void mouseMoved(int arg0, int arg1, int arg2, int arg3) {

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
