package game.ui.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.player.Player;
import game.core.player.effect.PlayerEffect;
import game.core.world.World;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


public class Effect{

	private Image img;
	private int DURATION ;
    private Instant activatedAt;
    private long activeFor ;
    private int x ;
    private int y;
    private WordGenerator wg;
    private Player player;
    private PlayerEffect pe;
    

	// effect id
	public Effect(Player player) throws SlickException {
		this.img = img.getScaledCopy(50, 50);
		pe= player.status.getEffects();
		
		wg=new WordGenerator();
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

	public void render(Graphics g) throws SlickException {
		if(isActive()){
			g.drawImage(this.img, x, y);
			g.setColor(Color.red);
			wg.draw(g, activeFor+"", x, y+this.img.getHeight(), false, 0.15f);
			
		}
	}

}
