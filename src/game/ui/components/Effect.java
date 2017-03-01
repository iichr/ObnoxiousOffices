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
import java.util.ArrayList;
import java.util.List;


public class Effect{

	private Image img;
	private int DURATION ;
    private Instant activatedAt;
    private long activeFor ;
    private int x ;
    private int y;
    private WordGenerator wg =new WordGenerator();;
    private List<PlayerEffect> pe = new ArrayList<PlayerEffect>();
    

	// effect id
	public Effect(Player player) throws SlickException {
		this.img = new Image("/res/sprites/tiles/coffee.png").getScaledCopy(50, 50);
		pe= player.status.getEffects();		
		
	}
	
        //activatedAt = Instant.now().plusSeconds(DURATION);  

    	/*if(activatedAt==null){
    		return false;
    	}else{
    		activeFor = ChronoUnit.SECONDS.between(Instant.now(), activatedAt);
    		return activeFor >= 1 && activeFor <= DURATION;
    	}*/


	public void render(Graphics g) throws SlickException {
		pe.forEach((e)->{			
			if(!e.ended()){
				activeFor=e.getDuration()-e.getElapsed();
				g.drawImage(this.img, x, y);
				g.setColor(Color.red);
				wg.draw(g, activeFor+"", x, y+this.img.getHeight(), false, 0.15f);
				g.drawString("isEnded"+e.ended() != null?"Yes":"No",x,y+this.img.getHeight()+50);
			}

		});
		
	}

}
