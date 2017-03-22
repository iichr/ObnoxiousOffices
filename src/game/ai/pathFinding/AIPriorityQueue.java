package game.ai.pathFinding;

import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by samtebbs on 21/03/2017.
 */
public class AIPriorityQueue extends PriorityQueue<Cell> implements Serializable {

	private static final long serialVersionUID = 1L;

	public AIPriorityQueue() {
		super(new Comparator<Cell>() {

			@Override
			public int compare(Cell o1, Cell o2) {
				return o1.fCost < o2.fCost ? -1 : o1.fCost > o2.fCost ? 1 : 0;
			}
		});
	}

}
