package mazeGenerator;


import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import maze.Cell;
import maze.Maze;
import maze.Wall;
import maze.NormalMaze;


/**
 * This class is part of mazeGenerator package which implements MazeGenerator interface.
 * And is used to implement Recursive Back Tracker Algorithm. 
 * @author Zaid & Swapnil
 *
 */

public class RecursiveBacktrackerGenerator implements MazeGenerator {

	/** selectedCell variable*/
	Cell selectedCell;
	/** unVisited ArrayList is use to store unVisited Cell*/
	ArrayList<Cell> unVisited = new ArrayList<Cell>();
	/** randomCell object to select randomCell in a maze*/
	Random randomCell  = new Random();
	/** stack to store visited cell and to implement recursive back track*/
	Stack<Cell> stack = new Stack<Cell>();



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
			//Maze type tunnel
		case 1:
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
	 * buildMaze method generate the required maze using recursive back tracking technique.
	 * ******************************************************************************************
	 * 1: Select a random cell from unVisited Cell ArrayList.
	 * 2: Remove selectedCell from unVisited ArrayList.
	 * 3: While unVisited ArrayList contains unVisited cell do 
	 * 3.1   For all the neighboring cell i.e. maze.NUM_DIR
	 *    3.1.1	if (neighboring cell exists && selectedCell is in unVisited ArrayList)
	 *    	3.1.1.1	Push the selectedCell into the stack 
	 *      3.1.1.2 Remove the wall between selectedCell and neighboring cell
	 *      3.1.1.3 Assign Neighboring Cell to selectedCell 
	 *      3.1.1.4 if (selectedCell is a tunnelCell)
	 *        3.1.1.4.1 Assign end of the tunnel to selectedCell
	 *        3.1.1.4.2 Remove the selectedCell from unVisited ArrayList
	 * 3.2 if no unVisited neighboring cell exist pop a cell from the stack
	 * 3.3 Else select a random Cell
	 * ******************************************************************************************
	 * @param maze
	 */
	public void buildMaze(Maze maze)	{
		//Select Random Cell from unVisited Cells
		selectedCell = unVisited.get(randomCell.nextInt(unVisited.size()));
		//Remove Selected Cell from unVisited Cells
		unVisited.remove(selectedCell);

		//Unless all the cells have been visited
		while(unVisited.size() != 0) {
			boolean flag = true;
			//For all the neighbor of selectedCell
			for(int i=0;i<maze.NUM_DIR;i++){
				//Neighboring cell exist && unVisited
				if(selectedCell.neigh[i] != null && unVisited.contains(selectedCell.neigh[i]) ){
					//Push the selectedCell into the Stack 
					stack.push(selectedCell);
					//remove the wall in between
					selectedCell.wall[i].present = false;
					//Assign Neighboring Cell to selectedCell
					selectedCell = selectedCell.neigh[i];
					//remove the selectedCell from unVisited Cells
					unVisited.remove(selectedCell);
					//if Selected Cell is a tunnel Cell 
					if(selectedCell.tunnelTo!=null){    
						//Assign end of the tunnel to selected Cell
						selectedCell=selectedCell.tunnelTo;
						//remove the selectedCell from unVisited Cells
						unVisited.remove(selectedCell);
					}
					//Since unVisited neighbor exist so no Back Tracking
					flag = false;
					//Since unvisited neighbor exist break
					break;
				}
			}
			//if no unVisited neighbor exist 
			if(flag) {
				//if visited cell stack is not empty
				if(!(stack.isEmpty()))  {
					//backTrack to previous cell
					selectedCell = stack.pop();
				}
				else {
					//select random cell from unVisited cells
					selectedCell = unVisited.get(randomCell.nextInt(unVisited.size()));
					//remove the selected cell from unVisited Cell
					unVisited.remove(selectedCell);

				}
			}

		}
	}//end of buildMaze()

} // end of class RecursiveBacktrackerGenerator


