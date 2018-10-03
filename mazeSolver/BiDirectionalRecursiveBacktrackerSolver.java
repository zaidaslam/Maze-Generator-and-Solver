package mazeSolver;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import maze.Cell;
import maze.Maze;


/**
 * This class is part of mazeSolver package which implements MazeSolver interface.
 * And is used to implement BiDirectional recursive backtracking Algorithm. 
 * @author Zaid & Swapnil
 *
 */

public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {
	/** solved is use to keep track solver status*/
	boolean solved = false;
	Cell selectedCell;
	/** unVisitedEntrance contains unvisited cell for entrance DFS*/
	ArrayList<Cell> unVisitedEntrance = new ArrayList<Cell>();
	/** unVisitedExit contains unvisited cell for exit DFS*/
	ArrayList<Cell> unVisitedExit = new ArrayList<Cell>();
	/** entranceHalfPath contains visited cell path for entrance DFS*/
	ArrayList<Cell> entranceHalfPath = new ArrayList<Cell>();
	/** exitHalfPath contains visited cell path for exit DFS*/
	ArrayList<Cell> exitHalfPath = new ArrayList<Cell>();
	/** fullPath contains path of visited cell from entrance to exit which is produced by merging entranceHalfPath and exitHalfPath*/
	ArrayList<Cell> fullPath = new ArrayList<Cell>();
	/** randomCell object to select randomCell in a maze*/
	Random randomCell  = new Random();
	/** entranceStack contains visited cell for entranceDFS*/
	Stack<Cell> entranceStack = new Stack<Cell>();
	/** exitStack contains visited cell for exitDFS*/
	Stack<Cell> exitStack = new Stack<Cell>();
	/** cellsVisited is use to keep track of all cells being visited*/
	int cellsVisited = 0;
	
	/** To keep the track of meetingPoint entranceDFS and exitDFS */
	boolean meetingPointFound = false;
	
	/* (non-Javadoc)
	 * @see mazeSolver.MazeSolver#solveMaze(maze.Maze)
	 *  
	 * ******************************************************************************************
	 * 1: Populate the unVisited ArrayList depending upon the maze type using Switch statement.
	 * 2: set entrance cell & add it to entranceHalfPath
	 * 3: set exitCell & add it to exitHalfPath
	 * 4: While meetingPoint not found
	 * 	  4.1: Call depthFirstSearch for entrancehalfPath and assign value to entranceCell
	 *    4.2: Call depthFirstSearch for exithalfPath and assign value to exitCell
	 * 5: Reverse the exitHalfPath so that exitCell goes to end
	 * 6: Call mergePath() for entranceHalfPath 
	 * 7: Call mergePath() for exitHalfPath and assign returning value to lastCell
	 * 8: if lastCell of fullPath is equal to mazeExit invoke isSolved() returning true
	 * ******************************************************************************************
	 */
	@Override
	public void solveMaze(Maze maze) {
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
		//Set entranceCell to maze entrance
		Cell entranceCell = maze.entrance;
		// Add entranceCell to entranceHalfPath
		entranceHalfPath.add(entranceCell);
		
		//Set exitCell to maze exit
		Cell exitCell =  maze.exit;
		// Add exitCell to exitHalfPath
		exitHalfPath.add(exitCell);
			
		// until meetingPoint not found
		while(!meetingPointFound) {
			
			// Call depthFirstSearch() for entranceHalfPath and assign value to entranceCell
			entranceCell = depthFirstSearch(entranceCell,maze,unVisitedEntrance,entranceStack,entranceHalfPath,exitHalfPath);
			// Call depthFirstSearch() for exitHalfPath and assign value to exitCell
			exitCell = depthFirstSearch(exitCell,maze,unVisitedExit,exitStack,exitHalfPath,entranceHalfPath);
		}
		// reverse the exitHalfPath so that exitCell goes to end
		 Collections.reverse(exitHalfPath);
		 // call mergePath for entranceHalfPath
		 mergePath(maze,entranceHalfPath);
		 // call mergePath for exitHalfPath and returning value to lastCell
		 Cell lastCell = mergePath(maze,exitHalfPath);
	 
	// if lastCell of the fullPath is maze exit invoke isSolved() returning true	 
	if(lastCell.equals(maze.exit))	{			
	 solved = true; 
	 isSolved();
	}
	
	} // end of solveMaze()


	@Override
	public boolean isSolved() {
		// TODO Auto-generated method stub
		return solved;
	} // end if isSolved()

	@Override
	public int cellsExplored() {
		// TODO Auto-generated method stub
		return cellsVisited;
	} // end of cellsExplored()
	
	
	
	/**
	 * ***************************************************************************************************
	 * 1: For each neighbor of selected cell
	 *   1.1: if neighbor exist && no wall in between && neighboring cell is not visited before
	 *     1.1.1: Put selectedCell into stack
	 *     1.1.2: Assign neighboring cell to selectedCell
	 *     1.1.3: remove selectedCell from unVisited ArrayList
	 *     1.1.4: Add selectedCell to entranceHalfPath or exitHalfPath ArrayList
	 *     1.1.5: if selectedCell is a tunnel cell assign tunnel end to selectedCell 
	 *       1.1.5.1: Add selectedCell to path ArrayList & remove selectedCell from unVisited ArrayList
	 * 2: If meetingPoint found return true 
	 * 3: If no unVisited neighbor cell exist 
	 *   3.1: BackTrack to previously visited cell 
	 * ***************************************************************************************************
	 * @param selectedCell
	 * @param maze
	 * @param unVisitedCell ArrayList of unVisited Cell for entranceHalfPath or exitHalfPath
	 * @param stack of previously visited cell for entranceHalfPath and exitHalfPath
	 * @param path ArrayList of carved path cell for entrancehalfPath or exitHalfPath 
	 * @param secondPath ArrayList of carved path cell for exitHalfPath or entrancehalfPath  
	 * @return current visited cell of entranceHalfPath or exitHalfPath
	 */
	public Cell depthFirstSearch(Cell selectedCell,Maze maze,ArrayList<Cell> unVisitedCell,Stack<Cell> stack,ArrayList<Cell> path,ArrayList<Cell> secondPath) {
		
			boolean flag = true;
			// for all the neighboring cell
			for(int i=0;i<maze.NUM_DIR;i++){
				//Neighboring cell exists && no wall in between && neighboring cell is not visited before
				if(selectedCell.neigh[i] != null && (!(selectedCell.wall[i].present)) && unVisitedCell.contains(selectedCell.neigh[i])){
					// Push the selected cell into the stack
					stack.push(selectedCell);
					// assign neighboring cell to selectedCell
					selectedCell = selectedCell.neigh[i];
					// remove selectedCell from unVisited ArrayList
					unVisitedCell.remove(selectedCell);
					// Add selectedCell to path
					path.add(selectedCell);
					// if selectedCell is a tunnel cell
					if(selectedCell.tunnelTo!=null){     
						//Assign end of the tunnel to selectedCell
						 selectedCell=selectedCell.tunnelTo;
						 //Add selectedCell to path
						 path.add(selectedCell);
						 // remove selectedCell for unVisitedCell
						 unVisitedCell.remove(selectedCell);
					 }
					flag = false;
					break;
				}
			}
			// if meetingPoint found return true
			if(secondPath.contains(selectedCell)) {
				meetingPointFound = true;
			}
			// if no unvisited neighboring cell exist
			if(flag) {
			// BackTrack to previously visited cell	
			if(!(stack.isEmpty()))  {
				selectedCell = stack.pop();
		}
			
			}
		return selectedCell;
	} // end of depthFirstSearch()
	
	/**
	 * **************************************************************************************************
	 * 1: Populate the unVisitedEntrance & unVisitedExit ArrayList with all the Cell in the hex maze.
	 * **************************************************************************************************
	 * @param maze
	 * @return unVisited ArrayList of Cell in hex maze
	 */
	public ArrayList<Cell> hexArrayList(Maze maze) {
		for (int i = 0; i < maze.sizeR; i++){
			for (int j = (i + 1) / 2; j < maze.sizeC + (i + 1) / 2; j++) {
				selectedCell = maze.map[i][j];
				unVisitedEntrance.add(selectedCell);	
				unVisitedExit.add(selectedCell);
			}
			}
		return unVisitedEntrance;
	}// end of hexArrayList()

	/**
	 * **************************************************************************************************
	 * 1: Populate the unVisitedEntrance & unVisitedExit ArrayList with all the Cell in the normal maze.
	 * **************************************************************************************************
	 * @param maze
	 * @return unVisited ArrayList of Cell in normal maze
	 */
	public ArrayList<Cell> normalArrayList(Maze maze) {
		for (int i = 0; i < maze.sizeR; i++){
			for (int j = 0; j < maze.sizeC; j++) {
				selectedCell = maze.map[i][j];
				unVisitedEntrance.add(selectedCell);	
				unVisitedExit.add(selectedCell);
			}
			}	
		return unVisitedEntrance;
	}// end of normalArrayList()

    /**
     * **************************************************************************************************
	 * 1: Merge the entranceHalfPath or exitHalfPath into fullPath.
	 * **************************************************************************************************
     * @param maze 
     * @param path pass entranceHalfPath or exitHalfPath to merge
     * @return lastCell of the fullPath
     */
    public Cell mergePath(Maze maze,ArrayList<Cell> path) {
    	Cell cell = null;
    	for(int i =  0; i<path.size();i++) {
    		cell = path.get(i);
    		if(!fullPath.contains(cell)) {
    			fullPath.add(cell);
    			maze.drawFtPrt(cell);
    			// Keep the track of cells visited
    			cellsVisited++;
    		}
    	}
    	return cell;
    }// end of mergePath()
    
} // end of class BiDirectionalRecursiveBackTrackerSolver
