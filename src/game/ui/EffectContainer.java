package game.ui;

import java.time.Duration;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.util.HashSet;
import java.util.Set;

import game.ui.interfaces.Vals;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
//import sun.plugin.dom.exception.InvalidStateException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Created by Raymond on 5/2/17.
 */
public class EffectContainer{

    private Instant current,end;
    private int interval = 5; // an interval time for 5 secs to test;
    private Image img;
    private boolean show = false;
    private int duration;

        // effect id
    public EffectContainer(Image img,int duration){
        this.duration= duration;
        this.img=img.getScaledCopy(50,50);
        end=Instant.now().plusSeconds(this.duration);
    }
    //TODO
    /*
    * Draw the image(done) -> flash the image when less than 5 seconds
    * Show the time left (s) << has negative number ***
    * effects are always on player's screen <- tick
    * show effects vertically
    * */
    public void render(Graphics g) throws SlickException{
        if(!show){}else
        {
            g.drawImage(this.img,Vals.SCREEN_WIDTH - 100 , Vals.SCREEN_HEIGHT - Vals.SCREEN_HEIGHT/5*4);
            g.drawString(Long.toString(ChronoUnit.SECONDS.between(Instant.now(),this.end)) + " s",Vals.SCREEN_WIDTH -100 , Vals.SCREEN_HEIGHT -Vals.SCREEN_HEIGHT/5*4 + 50);
            g.drawString("show : " + (show?"true":"false"),Vals.SCREEN_WIDTH -150 , Vals.SCREEN_HEIGHT -Vals.SCREEN_HEIGHT/5*4 + 100);
        }
    }

    public void update()throws SlickException{
        if(ChronoUnit.SECONDS.between(Instant.now(),end)==(long)0){
            show=false;
            this.current = Instant.now().plusSeconds(interval+1);
            this.end = current.plusSeconds(this.duration);
        }else {show=true;}

    }
    public void setCurrent(Instant current){
        this.current=current;
    }

}
