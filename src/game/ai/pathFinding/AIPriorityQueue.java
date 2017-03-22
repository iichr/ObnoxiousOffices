package game.ai.pathFinding;

import java.io.Serializable;
import java.util.PriorityQueue;

/**
 * Created by samtebbs on 21/03/2017.
 */
public class AIPriorityQueue extends PriorityQueue<Cell> implements Serializable {
   
	private static final long serialVersionUID = 1L;

	public AIPriorityQueue() {

        super((c1, c2) -> c1.fCost < c2.fCost ? -1 : c1.fCost > c2.fCost ? 1 : 0);
    }
}
