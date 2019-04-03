package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

/**
 *SearchableMaze implements ISearchable
 * @param Maze : maze - the maze we solve.
 * @param MazeState : start & goal.
 * @param boolean :visitedCell - array of boolean
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;
    private MazeState start;
    private MazeState goal;
    private boolean[][] visitedCell;


    public SearchableMaze(Maze m) {
        if (m != null) {
            maze = m;
            start = new MazeState(m.getStartPosition().getRowIndex(), m.getStartPosition().getColumnIndex());
            goal = new MazeState(m.getGoalPosition().getRowIndex(), m.getGoalPosition().getColumnIndex());
            visitedCell = new boolean[m.numOfRows()][m.numOfColumns()];
        }
    }

    public AState getStartState() {
        return start;
    }
    public AState getGoalState() {
        return goal;
    }


    /**
     * Get All Possible States
     * get all location AState can move to in maze (up\down\left\right)
     * @param tempDiagonal - array to keep Diagonal states
     * @param temp - array to keep possible states
     * * check 3 things:
     * (1)
     *
     */
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> temp = new ArrayList<>();
        ArrayList<AState> tempDiagonal;
        MazeState mazestate;
        if (s != null && s instanceof MazeState) //make sure State is a MazeState
        {
            mazestate = ((MazeState) s);
            int row = mazestate.getRow();
            int col = mazestate.getCol();
            MazeState tempAdd;
            tempAdd = CheckLegal(row - 1, col); //check if legal to add
            if (tempAdd != null)
                temp.add(tempAdd);
            tempAdd = CheckLegal(row + 1, col);
            if (tempAdd != null)
                temp.add(tempAdd);
            tempAdd = CheckLegal(row, col - 1);
            if (tempAdd != null)
                temp.add(tempAdd);
            tempAdd = CheckLegal(row, col + 1);
            if (tempAdd != null)
                temp.add(tempAdd);
            tempDiagonal = getAllDiagonals(row, col);
            for (int i = 0; i < tempDiagonal.size(); i++)
                temp.add(tempDiagonal.get(i));
        }
        return temp;
    }

    /**
     * Get All Diagonals
     * get all diagonals cells that are legal to move
     * cost of diagonals = 1.5
     * check 3 things:
     * (1)in Bounds of array.
     * (2)if i visit in this cells.
     * (3)if its not block(0)
     * @param tempM : maze state that save legal step.
     *  return ArrayList <AState>.
     */
    private ArrayList<AState> getAllDiagonals(int row, int col) {
        ArrayList<AState> temp = new ArrayList<>();
        MazeState tempM;
        if (inBounds(row - 1, col - 1) && visitedCell[row - 1][col - 1] == false && maze.getCellValue(row - 1, col - 1) == "0")
            if (visitedCell[row - 1][col] == false || visitedCell[row][col - 1] == false)
                if (maze.getCellValue(row - 1, col) == "0" || maze.getCellValue(row, col - 1) == "0") {
                    tempM = new MazeState(row - 1, col - 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (inBounds(row + 1, col + 1) && visitedCell[row + 1][col + 1] == false && maze.getCellValue(row + 1, col + 1) == "0")
            if (visitedCell[row + 1][col] == false || visitedCell[row][col + 1] == false)
                if (maze.getCellValue(row, col + 1) == "0" || maze.getCellValue(row + 1, col) == "0") {
                    tempM = new MazeState(row + 1, col + 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (inBounds(row + 1, col - 1) && visitedCell[row + 1][col - 1] == false && maze.getCellValue(row + 1, col - 1) == "0")
            if (visitedCell[row][col - 1] == false || visitedCell[row + 1][col] == false)
                if (maze.getCellValue(row, col - 1) == "0" || maze.getCellValue(row + 1, col) == "0") {
                    tempM = new MazeState(row + 1, col - 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (inBounds(row - 1, col + 1) && visitedCell[row - 1][col + 1] == false && maze.getCellValue(row - 1, col + 1) == "0") {
            if (inBounds(row - 1, col) && visitedCell[row - 1][col] == false && maze.getCellValue(row - 1, col) == "0" || inBounds(row, col + 1) && visitedCell[row][col + 1] == false && maze.getCellValue(row, col + 1) == "0") {
                tempM = new MazeState(row - 1, col - 1);
                tempM.setCost(1.5);
                temp.add(tempM);
            }
        }
        return temp;
    }



    /**
     * CheckLegal
     * check if state is legal or not
     * (1) inBounds.
     * (2) if not block(1).
     * cost - 1.
     *
     */
    private MazeState CheckLegal(int row, int col) {
        MazeState tempM;
        if (inBounds(row, col))
            if (maze.getCellValue(row, col) == "0") {
                tempM = new MazeState(row, col);
                tempM.setCost(1);
                return tempM;
            }
        return null;
    }

    /**
     * check if aState has been visitedCell in the past
     * get Astet
     */
    public boolean isVisited(AState visit) {
        if (visit != null && ((MazeState) visit).getRow() < maze.numOfRows() && ((MazeState) visit).getCol() < maze.numOfColumns() && ((MazeState) visit).getRow() >= 0 && ((MazeState) visit).getCol() >= 0) {
            boolean bool = visitedCell[((MazeState) visit).getRow()][((MazeState) visit).getCol()];
            return bool;
        } else
            return false;
    }

    /**
     * Change Visit True
     * get Astate
     */
    public void changeVisitTrue(AState visit) {
        if (visit != null && inBounds(((MazeState) visit).getRow(), ((MazeState) visit).getCol()) == true)
            visitedCell[((MazeState) visit).getRow()][((MazeState) visit).getCol()] = true;
    }

    /**
     * ResetVisit:
     * reset all array cell to false
     */
    public void ResetVisit() {
        for (int i = 0; i < maze.numOfRows(); i++)
            for (int j = 0; j < maze.numOfColumns(); j++)
                visitedCell[i][j] = false;
    }
    /**
     * In Bounds:
     * check if in the bounds of array
     */
    private boolean inBounds(int row, int column) {
        if (row < 0 || column < 0 || row >= maze.numOfRows() || column >= maze.numOfColumns())
            return false;
        if (visitedCell[row][column] == false)
            return true;
        return false;
    }
}