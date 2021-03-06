
package game.ai.pathFinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

import game.ai.AIPlayer;
import game.core.world.World;
import game.core.world.tile.Tile;
import game.core.world.tile.type.TileType;
import game.core.world.tile.type.TileTypeComputer;

/**
 * Implementation of the A* algorithm for path finding. Also it finds all coffee
 * machines and sofas on a given map, as well as the players chair.
 */

public class PathFinding implements Runnable, Serializable {

	// make eclipse happy
	private static final long serialVersionUID = 1L;

	final int gVertHorizCost = 10; // the cost of making a horizontal/vertical
									// move

	// list of tiles to be explored
	PriorityQueue<Cell> open = new PriorityQueue<Cell>();
	boolean closed[][]; // array of tiles that are already explored

	World world;
	AIPlayer player;
	
	// coordinates for our start point
	int startI;
	int startJ; 
	//coordinates of the closes coffee machine and sofa
	int coffeeI;
	int coffeeJ;
	int sofaI;
	int sofaJ;
	
	ArrayList<Pair<Integer, Integer>> coffees = new ArrayList<Pair<Integer, Integer>>();
	ArrayList<Pair<Integer, Integer>> sofas = new ArrayList<Pair<Integer, Integer>>();

	String toGo; // input "cm" for Coffee Machine or "s" for sofa, "c" for chair

	// constructor
	public PathFinding(World w, AIPlayer p, String s) {
		world = w;
		player = p;
		toGo = s;
	}

	int colLength = World.world.ySize; // j
	int rowLength = World.world.xSize; // i

	// the grid that is going to be used for the A*
	Cell[][] grid = new Cell[rowLength][colLength];

	// create the arraylist of cells
	private ArrayList<Pair<Integer, Integer>> path = new ArrayList<Pair<Integer, Integer>>();

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
	 * player coffee machine and sofa saving their i, j coordinates
	 * 
	 * @param world
	 *            the world that is going to be made into a grid
	 */
	void worldToCell() {
		for (int i = 0; i < rowLength; i++) {
			for (int j = 0; j < colLength; j++) {

				grid[i][j] = new Cell(i, j);
				Tile tile = World.world.getTile(i, j, 0); 
				// if you can walk over a tile, calculate the
				// heuristic function, else make it a null
				if (tile.type.canWalkOver())
					grid[i][j].hCost = calcHeuristic(i, j);
				else
					grid[i][j].hCost = 1000;

				// if the current tile is a coffee machine, and is closer than
				// the previous one, save coordinates
				if (tile.type.equals(TileType.COFFEE_MACHINE)) {
					grid[i][j].hCost = calcHeuristic(i, j);
					coffees.add(new Pair<Integer, Integer>(i, j));
				}

				// if the current tile is a sofa, and is closer than the
				// previous
				// one, save coordinates
				if (tile.type.equals(TileType.SOFA)) {
					grid[i][j].hCost = calcHeuristic(i, j);
					sofas.add(new Pair<Integer, Integer>(i, j));
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
		if (closed[c.i][c.j]) 
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

		// add the starting location to the open list
		open.add(grid[startI][startJ]);

		// loop through the cells
		while (true) {
			current = open.poll(); // pops the head of the queue

			if (current == null)
				break;
			closed[current.i][current.j] = true; 

			if (current.equals(grid[goalI][goalJ])) 
				return;

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
		ArrayList<Pair<Integer, Integer>> pathToObj = new ArrayList<Pair<Integer, Integer>>();
		// make an arraylist from the path
		// the first two digits are i, j coords of the final state
		// the array is goal -> start state
		Cell current = grid[goalI][goalJ];

		// loop through the parents of the cell
		while (current.parent != null) {
			pathToObj.add(new Pair<Integer, Integer>(current.i, current.j));
			current = current.parent;
		}
		return pathToObj;
	}

	/**
	 * When a player is disconnected, they need to go to their chair. This
	 * methods finds the path to it.
	 * 
	 * @param ai
	 *            the ai player that needs to do the moving
	 * @param x
	 *            x coord of the chair
	 * @param y
	 *            y coord of the chair
	 * @return the path from the current location of the ai to the chair
	 */
	public ArrayList<Pair<Integer, Integer>> pathToChair(AIPlayer ai, Integer x, Integer y) {
		startCell(ai.getLocation().coords.x, ai.getLocation().coords.y);
		worldToCell();
		AStar(x, y);
		ArrayList<Pair<Integer, Integer>> pathChair = path(x, y);
		Collections.reverse(pathChair);
		return pathChair;
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
		// create an ArrayList to store the current best path
		ArrayList<Pair<Integer, Integer>> currentPath = new ArrayList<Pair<Integer, Integer>>();
		// store the best size
		int size = 1000;

		// set the starting point
		startCell(player.getLocation().coords.x, player.getLocation().coords.y);

		// create a grid of cells from the world
		worldToCell();

		if (toGo == "cm") {
			// go through the list of coffee machines
			for (int i = 0; i < coffees.size(); i++) {
				// get the i, j coordinates of the coffee machine
				coffeeI = coffees.get(i).getL();
				coffeeJ = coffees.get(i).getR();
				// run AStar on them
				AStar(coffeeI, coffeeJ);
				// get the path after AStar
				currentPath = path(coffeeI, coffeeJ);
				if (size > currentPath.size()) {
					path = currentPath; 
					size = currentPath.size(); 
				}
			}
		} else if (toGo == "s") {
			// create an ArrayList to store the current best path
			ArrayList<Pair<Integer, Integer>> currentPathB = new ArrayList<Pair<Integer, Integer>>();
			// store the best size
			int sizeB = 1000;
			// go through the list of sofas
			for (int i = 0; i < sofas.size(); i++) {
				// get the i, j coordinates of the sofa
				sofaI = sofas.get(i).getL();
				sofaJ = sofas.get(i).getR();
				// run AStar on them
				AStar(sofaI, sofaJ);
				// get the path after AStar
				currentPathB = path(sofaI, sofaJ);
				if (sizeB > currentPathB.size()) {
					path = currentPathB; 
					sizeB = currentPathB.size(); 
				}
			}
		} else {
			//find the path to the chair of the player that got replace by an AI player
			path = pathToChair(player, TileTypeComputer.getChair(player).location.coords.x,
					TileTypeComputer.getChair(player).location.coords.y);
		}
	}
}