package game.AI;

import game.core.player.Player;
import game.core.world.World;

public interface Logic {
	
	public void reactToPlayerDrink();
	
	public void reactToPlayerWork();
	
	public void reactToPlayerHack();
	
	public void gainEnergy();
	
	public void findCoffeeMachine(World w, Player p, int i, int j);
	
	public void findBed(World w, Player p, int i, int j);
	
	public void goToCoffeeMachine();
	
	public void goToBed();
}
