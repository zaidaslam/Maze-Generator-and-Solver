package mazeSolver;

import java.util.ArrayList;

import maze.Cell;
import maze.Maze;


/**
 * This class is part of mazeSolver package which implements MazeSolver interface.
 * And is used to implement Wall Follower Solver Algorithm. 
 * @author Zaid & Swapnil
 *
 */
public class WallFollowerSolver implements MazeSolver {

	/** direction ArrayList is use to store all the possible directions*/
	ArrayList<Integer> direction = new ArrayList<Integer>();
	/** visitedCell ArrayList is use to store visitedCell*/
	ArrayList<Cell> visitedCell = new ArrayList<Cell>();
	/** cellsVisited is use to keep track of all cells being visited*/
	int cellsVisited = 0;
	/** solved is use to keep track solver status*/
	boolean solved = false;


	/* (non-Javadoc)
	 * @see mazeSolver.MazeSolver#solveMaze(maze.Maze)
	 *  
	 * ******************************************************************************************
	 * 1: set entrance cell
	 * 2: set exitCell
	 * 3: set initial direction
	 * 4: while exitCell is not visited 
	 * 	  4.1: for all the neighboring cell
	 * 		   4.1.1 if(neighboring cell exists && no wall is present in between
	 * 				 4.1.1.1 assign neighboring cell to selectedCell
	 * 				 4.1.1.2 if(selectedCell is not being visited)
	 * 						 4.1.1.2.1 add selectedCell to visited arrayList
	 * 						 4.1.1.2.1 increase cellsVisited count by one
	 * 				 4.1.1.3 if(selectedCell is a tunnel cell)
	 * 				 		 4.1.1.3.1 Assign tunnel end to selectedCell
	 * 						 4.1.1.3.2 increase cellsVisited count by one
	 * 						 4.1.1.3.3 if(selectedCell is not visited add it to visited arrayList)
	 * 	  4.2 invoke rotate()
	 * 5: if(exitCell is visited call isSolved() returning true
	 * ******************************************************************************************
	 */
	@Override
	public void solveMaze(Maze maze) {
		// TODO Auto-generated method stub
		//Assign maze entrance to selectedCell
		Cell selectedCell = maze.entrance;
		cellsVisited++;
		//Assign maze exit to exitCell
		Cell exitCell =  maze.exit;
		//Assign initial direction to direction ArrayList
		direction.add(maze.WEST); // West
		direction.add(maze.NORTHWEST); // North/NorthWest
		direction.add(maze.NORTHEAST); // NorthEast
		direction.add(maze.EAST); // East
		direction.add(maze.SOUTHEAST); // South/SouthEast	
		direction.add(maze.SOUTHWEST); // SouthWest

		// Until exitCell is visited 
		while(selectedCell != exitCell) {
			int j = 0;
			// for all the neighbors 
			for(int i = 0; i < direction.size();i++) {
				// Neighboring cell exist && no wall is present in between
				if(selectedCell.neigh[direction.get(i)]!= null && (!(selectedCell.wall[direction.get(i)].present))) {
					//Assign Neighboring cell to selectedCell
					selectedCell = selectedCell.neigh[direction.get(i)];
					maze.drawFtPrt(selectedCell);
					//if selectedCell is not visited before 
					if(!visitedCell.contains(selectedCell)) {
						//Add selectedCell to visitedCell ArrayList
						visitedCell.add(selectedCell);
						cellsVisited++;
					}
					// if selectedCell is a tunnel cell
					if(selectedCell.tunnelTo!=null){   
						// Assign end of the tunnel to selectedCell
						selectedCell=selectedCell.tunnelTo;
						maze.drawFtPrt(selectedCell);
						cellsVisited++;
						//if selectedCell is not visited before 
						if(!visitedCell.contains(selectedCell)) {
							//Add selectedCell to visitedCell ArrayList
							visitedCell.add(selectedCell);
						}
					}
					//Assign direction index of neighboring cell 
					j = i;
					break;
				}		
			}
			//start of rotate method
			rotate(j,maze);
		}
		// if exitCell is visited invoke isSolved Method returning true
		if(selectedCell.equals(exitCell))	{			
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
	 * ******************************************************************************************
	 * 1: Based on direction of neighboring cell re-populated direction ArrayList.
	 * ******************************************************************************************
	 * @param i direction index of neighboring Cell
	 * @param maze
	 */
	public void rotate(int i,Maze maze) {
		//if the direction of the neighboring cell is East re-populate direction ArrayList accordingly.
		if(direction.get(i) == maze.EAST) {

			direction.removeAll(direction);
			direction.add(maze.NORTHWEST);
			direction.add(maze.NORTHEAST);
			direction.add(maze.EAST);
			direction.add(maze.SOUTHEAST);
			direction.add(maze.SOUTHWEST);	
			direction.add(maze.WEST);
			return;
		}
		//if the direction of the neighboring cell is SouthEast re-populate direction ArrayList accordingly.
		else if(direction.get(i) == maze.SOUTHEAST ) {

			direction.removeAll(direction);
			direction.add(maze.NORTHEAST);
			direction.add(maze.EAST);
			direction.add(maze.SOUTHEAST);
			direction.add(maze.SOUTHWEST);
			direction.add(maze.WEST);	
			direction.add(maze.NORTHWEST);
			return;

		}
		//if the direction of the neighboring cell is SOUTHWEST re-populate direction ArrayList accordingly.
		else if(direction.get(i) == maze.SOUTHWEST ) {

			direction.removeAll(direction);
			direction.add(maze.EAST);
			direction.add(maze.SOUTHEAST);
			direction.add(maze.SOUTHWEST);
			direction.add(maze.WEST);	
			direction.add(maze.NORTHWEST);
			direction.add(maze.NORTHEAST);
			return;
		}
		//if the direction of the neighboring cell is WEST re-populate direction ArrayList accordingly.
		else if(direction.get(i) == maze.WEST ) {

			direction.removeAll(direction);
			direction.add(maze.SOUTHEAST);
			direction.add(maze.SOUTHWEST);
			direction.add(maze.WEST);	
			direction.add(maze.NORTHWEST);
			direction.add(maze.NORTHEAST);
			direction.add(maze.EAST);
			return;
		}
		//if the direction of the neighboring cell is NORTHWEST re-populate direction ArrayList accordingly.
		else if(direction.get(i) == maze.NORTHWEST ) {

			direction.removeAll(direction);	
			direction.add(maze.SOUTHWEST);
			direction.add(maze.WEST);	
			direction.add(maze.NORTHWEST);
			direction.add(maze.NORTHEAST);
			direction.add(maze.EAST);
			direction.add(maze.SOUTHEAST);
			return;
		}
		//if the direction of the neighboring cell is NORTHEAST re-populate direction ArrayList accordingly.
		else if(direction.get(i) == maze.NORTHEAST ) {

			direction.removeAll(direction);
			direction.add(maze.WEST);	
			direction.add(maze.NORTHWEST);
			direction.add(maze.NORTHEAST);
			direction.add(maze.EAST);
			direction.add(maze.SOUTHEAST);
			direction.add(maze.SOUTHWEST);
			return;
		}
	} // end of rotate()



} // end of class WallFollowerSolver
