package game.ai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import game.core.player.Player;
import game.core.world.World;
import game.core.world.tile.Tile;
import game.core.world.tile.TileType;

/**
 * Created on 28.01.2017
 * 
 * @author Atanas K. Harbaliev
 */

public class PathFinding implements Runnable, Serializable {

	//make eclipse happy
	private static final long serialVersionUID = 1L;

	// the object that is going to be used to store heuristic value, g cost
	// final value, coordinates and the parent
	class Cell implements Serializable {
		//make eclipse happy
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

		// @Override
		// public String toString() {
		// return "[" + this.i + ", " + this.j + "]";
		// }
	}

	final int gVertHorizCost = 10; // the cost of making a horizontal/vertical
									// move

	// list of tiles to be explored
	// change the comparator operation by comparing the final cost of two cells
	PriorityQueue<Cell> open = new PriorityQueue<>(new Comparator<Cell>() {
		@Override
		public int compare(Cell c1, Cell c2) {
			return c1.fCost < c2.fCost ? -1 : c1.fCost > c2.fCost ? 1 : 0;
		}
	});
	boolean closed[][]; // array of tiles that are already explored

	World world;
	Player player;

	int startI, startJ; // coordinates for our start point

	int coffeeI, coffeeJ, bedI, bedJ;

	String toGo; // input "cm" for Coffee Machine or "b" for sofa

	// constructor
	public PathFinding(World w, Player p, String s) {
		world = w;
		player = p;
		toGo = s;
	}

	// the size of the world
	int colLength = World.world.ySize; // j
	int rowLength = World.world.xSize; // i

	// the grid that is going to be used for the A*
	Cell[][] grid = new Cell[rowLength][colLength];

	// create the arraylist of cells
	ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();

	/**
	 * Calculate the heuristic /Manhattan distance/ using the coordinates of the
	 * starting cell and the goal coordinates
	 * 
	 * @param i
	 *            coordinate i of the goal cell
	 * @param j
	 *            coordinate j of the goal cell
	 */
	int calcHeuristic(int i, int j) {
		return Math.abs(startI - i) + Math.abs(startJ - j);
	}

	/**
	 * Creates a grid out of the map of the world, and locate the nearest to the
	 * player coffee machine and bed saving their i, j coordinates
	 * 
	 * @param world
	 *            the world that is going to be made into a grid
	 */
	void worldToCell() {
		int heurC = 1000;
		int heurB = 1000;
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < colLength; j++) {
				
				grid[i][j] = new Cell(i, j);
				Tile tile = World.world.getTile(i, j, 0); // get the tile at i,j
				// if you can walk over a tile, calculate the
				// heuristic function, else make it a null
				if (tile.type.canWalkOver())
					grid[i][j].hCost = calcHeuristic(i, j);
				else
					grid[i][j].hCost = 1000;

				// if the current tile is a coffee machine, and is closer than
				// the previous one, save coordinates
				if (tile.type == TileType.COFFEE_MACHINE && calcHeuristic(i, j) < heurC) {
					heurC = calcHeuristic(i, j);
					coffeeI = i;
					coffeeJ = j;
				}

				// if the current tile is a bed, and is closer than the previous
				// one, save coordinates
				if (tile.type == TileType.SOFA && calcHeuristic(i, j) < heurB) { 
					heurB = calcHeuristic(i, j);
					bedI = i;
					bedJ = j;
				}
			}
		}
	}

	/**
	 * Assign the start cell by giving its coordinates
	 * 
	 * @param i
	 *            the i coordinate
	 * @param j
	 *            the j coordinate
	 */
	void startCell(int i, int j) {
		startI = i;
		startJ = j;
	}

	/**
	 * After exploring a new tile, update the info to the adjacent ones
	 * 
	 * @param current
	 *            the current tile you are on
	 * @param c
	 *            the tile you are exploring
	 * @param cost
	 *            the cost of exploring the tile c
	 */
	void checkAndUpdateCost(Cell current, Cell c, int cost) {
		// if cell c is reachable and not yet explored, calculate the cost of
		// exploring it
		if (closed[c.i][c.j]) //c == null || 
			return;
		int cCost = c.hCost + cost;

		// if "c" is in open and the cost is less than it previously was from a
		// different path, update the cost and the parent
		boolean inOpen = open.contains(c);
		if (!inOpen || cCost < c.fCost) {
			c.fCost = cCost;
			c.parent = current;
			if (!inOpen)
				open.add(c);
		}
	}

	/**
	 * A* algorithm, works only with horizontal and vertical moves
	 * 
	 * @param goalI
	 *            the i coordinate of the goal cell
	 * @param goalJ
	 *            the j coordinate of the goal cell
	 */
	void AStar(int goalI, int goalJ) {

		Cell current;
		closed = new boolean[rowLength][colLength];

		// set the starting point
		startCell(player.getLocation().x, player.getLocation().y);

		// add the starting location to the open list
		open.add(grid[startI][startJ]);

		// loop through the cells
		while (true) {
			current = open.poll(); // pops the head of the queue

			if (current == null) // if the head of the queue is empty - break
				break;
			closed[current.i][current.j] = true; // put the current cell in the
													// explored list

			if (current.equals(grid[goalI][goalJ])) // if we have found the goal
				return; // state - return

			Cell c;

			// check and update all eight adjacent cells
			if (current.i - 1 >= 0) {
				c = grid[current.i - 1][current.j];
				checkAndUpdateCost(current, c, current.fCost + gVertHorizCost);
			}

			if (current.j - 1 >= 0) {
				c = grid[current.i][current.j - 1];
				checkAndUpdateCost(current, c, current.fCost + gVertHorizCost);
			}

			if (current.j + 1 < grid[0].length) {
				c = grid[current.i][current.j + 1];
				checkAndUpdateCost(current, c, current.fCost + gVertHorizCost);
			}

			if (current.i + 1 < grid.length) {
				c = grid[current.i + 1][current.j];
				checkAndUpdateCost(current, c, current.fCost + gVertHorizCost);
			}
		}
	}

	/**
	 * Creates and ArrayList of all the coordinates of all cells in the path to
	 * the goal state, from the start state
	 * 
	 * @param goalI
	 *            the i coordinate of the goal cell
	 * @param goalJ
	 *            the j coordinate of the goal cell
	 * @return ArrayList of integer. Take the frist two elements and you have
	 *         the i, coordinates of the first cell, then the second, then...
	 */
	public ArrayList<Pair<Integer, Integer>> path(int goalI, int goalJ) {

		// make an arraylist from the path
		// the first two digits are i, j coords of the final state
		// the array is goal -> start state
		Cell current = grid[goalI][goalJ];

		// loop through the parents of the cell
		while (current.parent != null) {
			path.add(new Pair<Integer, Integer>(current.i, current.j));
			current = current.parent;
		}
		return path;
	}

	/**
	 * Get method for the path
	 * 
	 * @return the path
	 */
	public ArrayList<Pair<Integer, Integer>> getPath() {
		return path;
	}

	@Override
	public void run() {
		
		// create a grid of cells from the world
		worldToCell();
		
		if (toGo == "cm") {
			AStar(coffeeI, coffeeJ);
			path(coffeeI, coffeeJ);
		} else {
			AStar(bedI, bedJ);
			path(bedI, bedJ);
		}
	}
}
