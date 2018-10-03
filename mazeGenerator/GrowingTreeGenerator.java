package mazeGenerator;

import java.util.*;

import maze.Maze;
import maze.Cell;

/**
 * This class is part of mazeGenerator package which implements MazeGenerator interface.
 * And is used to implement Growing Tree Algorithm.  
 * @author Zaid & Swapnil
 *
 */

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement as "usually pick the most recent cell, but occasionally pick a random cell"

	double threshold = 0.1;
	Cell selectedCell;
	Cell frontierCell;
	/** unVisited ArrayList is use to store unVisited Cell*/
	ArrayList<Cell> unVisited = new ArrayList<Cell>();
	/** Set of Visited Cell*/
	Set<Cell> visited = new HashSet<Cell>();
	/** randomCell object to select randomCell*/
	Random randomCell  = new Random();


	/* (non-Javadoc)
	 * @see mazeGenerator.MazeGenerator#generateMaze(maze.Maze)
	 *  /** 
	 * 
	 * ******************************************************************************************
	 * 1: Populate the unVisited ArrayList depending upon the maze type using Switch statement.
	 * 2: Call buildMaze() method to generate maze.
	 * ******************************************************************************************
	 */
	@Override
	public void generateMaze(Maze maze) {
		switch (maze.type) {
		//Maze type normal
		case 0:
			normalArrayList(maze);	
			break;
			//Maze type hex
		case 2:
			hexArrayList(maze);
			break;
		default:
			System.err.println("Invalid Maze Type.");		
		}
		//Start of buildMaze method
		buildMaze(maze);	
	}// end of generateMaze()


	/**
	 * ******************************************************************************************
	 * 1: Populate the unVisited ArrayList with all the Cell in the hex maze.
	 * ******************************************************************************************
	 * @param maze
	 * @return unVisited ArrayList of Cell in hex maze
	 */
	public ArrayList<Cell> hexArrayList(Maze maze) {
		for (int i = 0; i < maze.sizeR; i++){
			for (int j = (i + 1) / 2; j < maze.sizeC + (i + 1) / 2; j++) {

				selectedCell = maze.map[i][j];
				unVisited.add(selectedCell);

			}
		}
		return unVisited;
	}// end of hexArrayList()

	/**
	 * ******************************************************************************************
	 * 1: Populate the unVisited ArrayList with all the Cell in the normal maze.
	 * ******************************************************************************************
	 * @param maze
	 * @return unVisited ArrayList of Cell in normal maze
	 */
	public ArrayList<Cell> normalArrayList(Maze maze) {
		for (int i = 0; i < maze.sizeR; i++){
			for (int j = 0; j < maze.sizeC; j++) {

				selectedCell = maze.map[i][j];
				unVisited.add(selectedCell);

			}
		}

		return unVisited;
	}// end of normalArrayList() 

	/**
	 * buildMaze method generate the required maze using Growing Tree technique.
	 * ******************************************************************************************
	 * 1: Select a random cell from unVisited Cell ArrayList.
	 * 2: Add selectedCell to visited set.
	 * 3: Remove the selectedCell from unvisited ArrayList 
	 * 4: While cell exist in a visited set   
	 * 4.1   Pick a random cell from visited set 
	 * 4.2   For all the possible neighbors of a selectedCell
	 *    4.2.1 if neighboring Cell is unVisited 
	 *      4.2.1.1 Remove the wall in between
	 *      4.2.1.2 Add neighboring cell to visited set
	 *      4.2.1.3 Remove the neighboring cell from unvisited ArrayList
	 * 4.3   If no unVisited neighbor exist 
	 *    4.3.1 Remove the selectedCell from visited set
	 * ******************************************************************************************
	 * @param maze
	 */
	public void buildMaze(Maze maze)	{
		//select random cell from unVisited Cells
		selectedCell = unVisited.get(randomCell.nextInt(unVisited.size()));
		//Add selectedCell to visited set
		visited.add(selectedCell);
		//remove selectedCell from unVisited ArrayList
		unVisited.remove(selectedCell);

		// While cell exist in a visited set
		while(visited.size() != 0) {

			boolean flag = true;

			int index = new Random().nextInt(visited.size());
			//Pick a random Cell from visited set using getRandomCell() 
			selectedCell = getRandomCell(index); 

			//For all the neighbor of selectedCell
			for(int i = 0; i < maze.NUM_DIR; i++ ) {
				//if neighboring Cell exist	
				if(selectedCell.neigh[i] != null ) {
					//if neighboring cell is unvisited
					if(unVisited.contains(selectedCell.neigh[i])) {
						//remove the wall in between
						selectedCell.wall[i].present = false;
						//add neighboring cell to visited set
						visited.add(selectedCell.neigh[i]); 
						//remove the neighboring cell from unvisited ArrayList
						unVisited.remove(selectedCell.neigh[i]);
						flag = false;
						break;
					}
				}
			}
			// if no unvisited neighboring cell exist 
			if(flag) {
				//remove the selectedCell from visited set
				visited.remove(selectedCell);

			}
		}
	}// end of buildMaze()

	/**
	 * This method is used to pick a Cell from visited set.
	 * ******************************************************************************************
	 * @param index
	 * @return Cell at ith index from visited set
	 */
	public Cell getRandomCell(int index){
		Iterator<Cell> iter = visited.iterator();
		for (int i = 0; i < index; i++) {
			iter.next();
		}
		return iter.next();

	}//end of getRandomCell()

}
