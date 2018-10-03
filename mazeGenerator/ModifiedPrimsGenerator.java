package mazeGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import maze.Cell;
import maze.Maze;


/**
 * This class is part of mazeGenerator package which implements MazeGenerator interface.
 * And is used to implement Modified Prims Algorithm. 
 * @author Zaid & Swapnil
 *
 */

public class ModifiedPrimsGenerator implements MazeGenerator {

	Cell selectedCell;
	Cell frontierCell;
	/** unVisited ArrayList is use to store unVisited Cell*/
	ArrayList<Cell> unVisited = new ArrayList<Cell>();
	/** Set of Visited Cell*/
	Set<Cell> visited = new HashSet<Cell>();
	/** Set of frontier Cell*/
	Set<Cell> frontier = new HashSet<Cell>();
	/** randomCell object to select randomCell in a maze*/
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
		// TODO Auto-generated method stub

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


	} // end of generateMaze()

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
	 * buildMaze method generate the required maze using Modified Prims technique.
	 * ******************************************************************************************
	 * 1: Select a random cell from unVisited Cell ArrayList.
	 * 2: Add selectedCell to visited set.
	 * 3: Call addNeighboringCell() for selectedCell 
	 * 4: While unvisited cell exist in a frontier set  
	 * 4.1   Select randomCell from frontier set 
	 * 4.2   For all the possible neighbors of a frontier cell
	 *    4.2.1 if neighboring Cell exists 
	 *    4.2.2 if neighboring cell is adjacent to frontier cell
	 *      4.2.2.1 Remove the wall in between
	 *      4.2.2.2 Remove the frontier cell from frontier set
	 *      4.2.2.3 Add the frontier cell to visited set
	 *      4.2.2.4 Add neighbor of frontier cell to frontier set
	 * ******************************************************************************************
	 * @param maze
	 */
	public void buildMaze(Maze maze)	{

		//Select a random Cell from unVisited ArrayList
		selectedCell = unVisited.get(randomCell.nextInt(unVisited.size()));
		// add selectedCell to visited set
		visited.add(selectedCell); 
		//add all the neighbors of selectedCell to frontier set using addNeighboring()
		addNeighboringCell(selectedCell); 

		// unless no cell exist in frontier set 
		while(frontier.size() != 0) {
			//Pick a random frontier cell from frontier set using getRandomCell() 
			int index = new Random().nextInt(frontier.size());
			frontierCell = getRandomCell(index); 

			//For all the neighboring cells
			for(int i = 0; i < maze.NUM_DIR; i++ ) {
				//if neighboring cell exist 
				if(frontierCell.neigh[i] != null ) {
					//if neighboring cell is adjacent to frontierCell
					if(visited.contains(frontierCell.neigh[i])) {
						//remove the wall in between
						frontierCell.wall[i].present = false;
						//remove the frontierCell from frontier set
						frontier.remove(frontierCell);
						//add frontierCell to visited set
						visited.add(frontierCell); 
						//add all the neighbors of frontierCell to frontier set using addNeighboring()
						addNeighboringCell(frontierCell);
						break;
					}
				}
			}
		}
	}// end of buildMaze()


	/**
	 * This method is used to pick a frontierCell from frontier set.
	 * ******************************************************************************************
	 * @param index
	 * @return frontierCell at ith index from frontier set
	 */
	public Cell getRandomCell(int index){
		Iterator<Cell> iter = frontier.iterator();
		for (int i = 0; i < index; i++) {
			iter.next();
		}
		return iter.next();

	}//end of getRandomCell()


	/**
	 * This method is used to add all the neighboring cell to frontier set.
	 * ******************************************************************************************
	 * 1: If neighboring Cell exist and neighboring cell not in the visited set 
	 *   1.1: Add neighboring cell to frontier set
	 * ******************************************************************************************  
	 * @param cell
	 */
	public void addNeighboringCell(Cell cell) {
		for(int i=0;i<6;i++) {
			if(cell.neigh[i] != null && (!visited.contains(cell.neigh[i]))) {	
				frontier.add(cell.neigh[i]); 		

			}
		}
	}// end of addNeighboringCell()



} // end of class ModifiedPrimsGenerator







