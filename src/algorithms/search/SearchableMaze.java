package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.ArrayList;

/**
 * Class to get info of maze
 *
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

    public void setGoalState(AState x) {
        if (x != null && x instanceof MazeState) //make sure aState is a MazeState
            goal = (MazeState) x;
    }


    /**
     * get all diagonals cells that are legal to move
     */
    private ArrayList<AState> getAllDiagonals(int x, int y) {
        ArrayList<AState> temp = new ArrayList<>();
        MazeState tempM;
        if (inBounds(x - 1, y - 1) && visitedCell[x - 1][y - 1] == false && maze.getCellValue(x - 1, y - 1) == 0)
            if (visitedCell[x - 1][y] == false || visitedCell[x][y - 1] == false)
                if (maze.getCellValue(x - 1, y) == 0 || maze.getCellValue(x, y - 1) == 0) {
                    tempM = new MazeState(x - 1, y - 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (inBounds(x + 1, y + 1) && visitedCell[x + 1][y + 1] == false && maze.getCellValue(x + 1, y + 1) == 0)
            if (visitedCell[x + 1][y] == false || visitedCell[x][y + 1] == false)
                if (maze.getCellValue(x, y + 1) == 0 || maze.getCellValue(x + 1, y) == 0) {
                    tempM = new MazeState(x + 1, y + 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (inBounds(x + 1, y - 1) && visitedCell[x + 1][y - 1] == false && maze.getCellValue(x + 1, y - 1) == 0)
            if (visitedCell[x][y - 1] == false || visitedCell[x + 1][y] == false)
                if (maze.getCellValue(x, y - 1) == 0 || maze.getCellValue(x + 1, y) == 0) {
                    tempM = new MazeState(x + 1, y - 1);
                    tempM.setCost(1.5);
                    temp.add(tempM);
                }
        if (inBounds(x - 1, y + 1) && visitedCell[x - 1][y + 1] == false && maze.getCellValue(x - 1, y + 1) == 0) {
            if (inBounds(x - 1, y) && visitedCell[x - 1][y] == false && maze.getCellValue(x - 1, y) == 0 || inBounds(x, y + 1) && visitedCell[x][y + 1] == false && maze.getCellValue(x, y + 1) == 0) {
                tempM = new MazeState(x - 1, y - 1);
                tempM.setCost(1.5);
                temp.add(tempM);
            }
        }
        return temp;
    }

    /**
     * get all location AState can move to in maze (up\down\left\right)
     */
    public ArrayList<AState> getAllPossibleStates(AState s) {
        ArrayList<AState> temp = new ArrayList<>(); //array to keep possible states
        ArrayList<AState> tempD; //array to keep Diagonal states
        MazeState mazestate;
        if (s != null && s instanceof MazeState) //make sure State is a MazeState
        {
            mazestate = ((MazeState) s);
            int x = mazestate.getRow();
            int y = mazestate.getCol();
            MazeState TempAdd;
            TempAdd = CheckLegal(x - 1, y); //check if legal to add
            if (TempAdd != null)
                temp.add(TempAdd);
            TempAdd = CheckLegal(x + 1, y);
            if (TempAdd != null)
                temp.add(TempAdd);
            TempAdd = CheckLegal(x, y - 1);
            if (TempAdd != null)
                temp.add(TempAdd);
            TempAdd = CheckLegal(x, y + 1);
            if (TempAdd != null)
                temp.add(TempAdd);
            tempD = getAllDiagonals(x, y); //get all diagonal states
            for (int i = 0; i < tempD.size(); i++)
                temp.add(tempD.get(i)); //add them to array
        }
        return temp;
    }

    /**
     * check if state is legal or not
     */
    private MazeState CheckLegal(int x, int y) {
        MazeState tempM;
        if (inBounds(x, y))
            if (maze.getCellValue(x, y) == 0) {
                tempM = new MazeState(x, y);
                tempM.setCost(1);
                return tempM;
            }
        return null;
    }

    /**
     * check if aState has been visitedCell in the past
     */
    public boolean isVisited(AState visit) {
        if (visit != null && ((MazeState) visit).getRow() < maze.numOfRows() && ((MazeState) visit).getCol() < maze.numOfColumns() && ((MazeState) visit).getRow() >= 0 && ((MazeState) visit).getCol() >= 0) {
            boolean x = visitedCell[((MazeState) visit).getRow()][((MazeState) visit).getCol()];
            return x;
        } else
            return false;
    }

    /**
     * change visit to positive
     */
    public void changeVisitTrue(AState visit) {
        if (visit != null && inBounds(((MazeState) visit).getRow(), ((MazeState) visit).getCol()) == true)
            visitedCell[((MazeState) visit).getRow()][((MazeState) visit).getCol()] = true;
    }

    /**
     * reset all visitedCell field to false
     */
    public void ResetVisit() {
        for (int i = 0; i < maze.numOfRows(); i++)
            for (int j = 0; j < maze.numOfColumns(); j++)
                visitedCell[i][j] = false;
    }

    private boolean inBounds(int row, int column) {
        if (row < 0 || column < 0 || row >= maze.numOfRows() || column >= maze.numOfColumns())
            return false;
        if (visitedCell[row][column] == false)
            return true;
        return false;
    }

}
