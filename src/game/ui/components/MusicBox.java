package game.ui.components;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import game.ui.interfaces.MusicLocations;

public class MusicBox {

	private GameContainer gc;
	private float currentSVolume;
	private float currentMVolume;
	private Sound pressed;

	public MusicBox(GameContainer gc) throws SlickException {
		this.gc=gc;
		currentSVolume=gc.getSoundVolume();
		currentMVolume=gc.getMusicVolume();
		pressed = new Sound(MusicLocations.PRESSED);
		
	}

	/**
	 * Changes the sound volume down
	 * 
	 * @param gc
	 *            The game container
	 */
	public void changeSVolumeL(GameContainer gc) {

		if ((int) (currentSVolume * 100) == 0) {
			gc.setSoundVolume(0.0f);
		} else {
			gc.setSoundVolume(currentSVolume -= 0.25f);
		}
	}

	/**
	 * Changes the sound volume up
	 * 
	 * @param gc
	 *            The game container
	 */
	public void changeSVolumeR(GameContainer gc) {
		if ((int) (currentSVolume * 100) == 100) {
			gc.setSoundVolume(1.0f);
		} else {
			gc.setSoundVolume(currentSVolume += 0.25f);
		}
	}
	/**
	 * Changes the sound volume down
	 * 
	 * @param gc
	 *            The game container
	 */
	public void changeMVolumeL(GameContainer gc) {

		if ((int) (currentMVolume * 100) == 0) {
			gc.setMusicVolume(0.0f);
		} else {
			gc.setMusicVolume(currentMVolume -= 0.25f);
		}
	}

	/**
	 * Changes the sound volume up
	 * 
	 * @param gc
	 *            The game container
	 */
	public void changeMVolumeR(GameContainer gc) {
		if ((int) (currentMVolume * 100) == 100) {
			gc.setMusicVolume(1.0f);
		} else {
			gc.setMusicVolume(currentMVolume += 0.25f);
		}
	}
	
	public void playPressed(){
		pressed.play();
	}


}
