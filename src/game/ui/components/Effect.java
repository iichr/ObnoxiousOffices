package game.ui.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import game.core.player.Player;
import game.core.player.effect.PlayerEffect;
import game.core.player.effect.PlayerEffectCoffeeBuzz;
import game.core.player.effect.PlayerEffectSleeping;
import game.ui.interfaces.ImageLocations;
import game.ui.interfaces.Vals;

public class Effect {
	private long activeFor;
	private float tileWidth;
	private float tileHeight;
	private float padding;

	private Image img;
	private Image timerBarBase;
	private Image timerBarFull;
	private HashMap<Class<? extends PlayerEffect>,Image> imgs;
	private List<PlayerEffect> pe = new ArrayList<PlayerEffect>();

	// effect id
	public Effect(float tileWidth, float tileHeight) throws SlickException {
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.padding = tileWidth / 2;

		timerBarBase = new Image(ImageLocations.ACTION_BAR_BASE, false, Image.FILTER_NEAREST);
		timerBarFull = new Image(ImageLocations.ACTION_BAR_FULL, false, Image.FILTER_NEAREST);
		
		imgs = new HashMap<Class<? extends PlayerEffect>, Image>();
		imgs.put(PlayerEffectCoffeeBuzz.class, new Image(ImageLocations.COFFEE_EFFECT, false, Image.FILTER_NEAREST));
		imgs.put(PlayerEffectSleeping.class, new Image(ImageLocations.SLEEP_EFFECT, false, Image.FILTER_NEAREST));
	}

	public void updateEffects(Player player) {
		pe = player.status.getEffects();
	}

	public void render(Graphics g) throws SlickException {
		float width = 4 * tileWidth/3;
		float height = 2* tileHeight/3;
		float x = Vals.SCREEN_WIDTH - tileWidth - width;
		float y = tileHeight + height;

		for (PlayerEffect e : pe) {
			if (!e.ended()) {
				activeFor = e.getDuration() - e.getElapsed();
				
				//draw image
				imgs.get(e.getClass()).draw(x, y, width, height);
				
				//draw timer bars
				timerBarBase.draw(x, y + height + padding / 10, width, height / 8);
				timerBarFull.draw(x, y + height + padding / 10, x + width * activeFor / e.getDuration(), y + height + padding / 10 + height / 8,
						0, 0, timerBarFull.getWidth() * activeFor / e.getDuration(), timerBarFull.getHeight());
			}
			y = y + height + padding;
		}
	}

}
