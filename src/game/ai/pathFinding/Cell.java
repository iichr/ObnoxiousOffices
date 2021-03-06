package game.ai.pathFinding;

import java.io.Serializable;

/**
 * the class is going to be used to store heuristic value, g cost final value,
 * coordinates and the parent
 */
public class Cell implements Serializable, Comparable<Cell> {

	private static final long serialVersionUID = 1L;

	int hCost = 0; // heuristic cost
	int fCost = 0; // total cost, f = g + h

	int i, j; // coordinates of the cell
	Cell parent; // previous cell

	// constructor
	Cell(int i, int j) {
		this.i = i;
		this.j = j;
	}

	@Override
	public int compareTo(Cell o) {
		return this.fCost < o.fCost ? -1 : this.fCost > o.fCost ? 1 : 0;
	}
}
