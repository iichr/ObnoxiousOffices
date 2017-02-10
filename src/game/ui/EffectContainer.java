package game.ui;

import game.ui.interfaces.Vals;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Raymond on 5/2/17.
 */
public class EffectContainer {

	private Image img;
	private int DURATION ;
    private Instant activatedAt;
    private long activeFor ;
    
	// effect id
	public EffectContainer(Image img, int duration) {
		DURATION = duration;
		this.img = img.getScaledCopy(50, 50);
			
	}
	
    public void activate() {
        activatedAt = Instant.now().plusSeconds(DURATION);  
    }

    public boolean isActive() {
    	if(activatedAt==null){
    		return false;
    	}else{
    		activeFor = ChronoUnit.SECONDS.between(Instant.now(), activatedAt);
    		return activeFor >= 0 && activeFor <= DURATION;
    	}
       
    }
	// TODO
	/*
	 * Draw the image(done) -> flash the image when less than 5 seconds Show the
	 * time left (s) << has negative number *** effects are always on player's
	 * screen <- tick show effects vertically
	 */
	public void render(Graphics g) throws SlickException {
		g.setColor(Color.black);
		if(isActive()){
			g.drawImage(this.img, Vals.SCREEN_WIDTH - 100, Vals.SCREEN_HEIGHT - Vals.SCREEN_HEIGHT / 5 * 4);
			g.setColor(Color.black);
			g.drawString( activeFor + " s", Vals.SCREEN_WIDTH - 100,
					Vals.SCREEN_HEIGHT - Vals.SCREEN_HEIGHT / 5 * 4 + 50);
			
		}
		g.drawString("isActive : " + (isActive() ? "YES" : "NO"), Vals.SCREEN_WIDTH - 150,
				Vals.SCREEN_HEIGHT - Vals.SCREEN_HEIGHT / 5 * 4 + 100);
	}

}
