package game.ui;

import game.ui.interfaces.Vals;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class EffectContainer {

	private Image img;
	private int DURATION ;
    private Instant activatedAt;
    private long activeFor ;
    private int x ;
    private int y;

	// effect id
	public EffectContainer(Image img, int duration,int x, int y) {
		DURATION = duration;
		this.img = img.getScaledCopy(50, 50);
		this.x=x;
		this.y=y;
	}
	
    public void activate() {
        activatedAt = Instant.now().plusSeconds(DURATION);  
    }

    public boolean isActive() {
    	if(activatedAt==null){
    		return false;
    	}else{
    		activeFor = ChronoUnit.SECONDS.between(Instant.now(), activatedAt);
    		return activeFor >= 1 && activeFor <= DURATION;
    	}
       
    }
	// TODO
	/*
	 * Draw the image(done) -> flash the image when less than 5 seconds Show the
	 * time left (s) << has negative number *** effects are always on player's
	 * screen <- tick show effects vertically
	 */
	public void render(Graphics g) throws SlickException {
		g.setColor(Color.red);
		if(isActive()){
			g.drawImage(this.img, x, y);
			g.setColor(Color.red);
			g.drawString( activeFor + " s", Vals.SCREEN_WIDTH - 100,
					Vals.SCREEN_HEIGHT - Vals.SCREEN_HEIGHT / 5 * 4 + 50);
			
		}
		g.drawString("isActive : " + (isActive() ? "YES" : "NO"), Vals.SCREEN_WIDTH - 150,
				Vals.SCREEN_HEIGHT - Vals.SCREEN_HEIGHT / 5 * 4 + 100);
	}
}
