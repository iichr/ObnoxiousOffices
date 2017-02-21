package game.ui;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class MusicBox extends Music {

	private Music music;

	public MusicBox(String music) throws SlickException {
		super(music);
		this.music = new Music(music);
	}

	public void play() {
		music.play();
	}

	public void loop() {
		music.loop();
	}

	public void slient() {
		music.setVolume(0);
	}

	public void loud() {
		music.setVolume(1);
	}
	public Music getMusic(){
		return music;
	}

}
