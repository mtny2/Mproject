package algorithms.mazeGenerators;

import java.util.*;

/**
 * Class that generate a maze by using Randomized Prims algorithm
 */
public class MyMazeGenerator extends AMazeGenerator {

    private Maze myMaze;
    private Position[][] positionCells;
    private List<Position> set;

    public MyMazeGenerator() {
        set = new ArrayList<>();
    }

    /**
     * generate a maze with all 1's
     */

    public Maze generate(int rows, int columns) {
        if (rows < 2)
            rows = 10;
        if (columns < 2)
            columns = 10;
        myMaze = new Maze(rows, columns);
        positionCells = new Position[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                positionCells[i][j] = new Position(i, j);
        //change all values to 1
        for (int i = 0; i < myMaze.numOfRows(); i++)
            for (int j = 0; j < myMaze.numOfColumns(); j++)
                myMaze.changeCellValue(i, j, 1);
        set.add(myMaze.getStartPosition()); //add start position to array
        Position currentPosition;
        Random random = new Random();
        int index;
        while (set.size() > 0) {
            index=random.nextInt(set.size());
            currentPosition = set.get(index);//from candidates list
            if (currentPosition != null && numOfPathNeighbours(currentPosition) <= 1) {//checks if currently position can be changed to 0 and has maximum of one 0 neighbour
                myMaze.changeCellValue(currentPosition.getRowIndex(), currentPosition.getColumnIndex(), 0);
                addCandidatesToSet(currentPosition);//neighbours are set
            }
            set.remove(currentPosition);
        }
        setRandomGoalPosition();
        return myMaze;
    }


    /**
     * returns array list of neighbours of a position
     *
     */

    private List<Position> myNeighbours(Position position) {
        if (position == null)
            return null;
        List<Position> neighbours;
        neighbours = new ArrayList<>();
        //check if can go up down left or right if so, add to list
        //Down
        if (isValid(position.getRowIndex() - 1, position.getColumnIndex()))
            neighbours.add(positionCells[position.getRowIndex() - 1][position.getColumnIndex()]);
        //Up
        if (isValid(position.getRowIndex() + 1, position.getColumnIndex()))
            neighbours.add(positionCells[position.getRowIndex() + 1][position.getColumnIndex()]);
        //Left
        if (isValid(position.getRowIndex(), position.getColumnIndex() - 1))
            neighbours.add(positionCells[position.getRowIndex()][position.getColumnIndex() - 1]);
        //Right
        if (isValid(position.getRowIndex(), position.getColumnIndex() + 1))
            neighbours.add(positionCells[position.getRowIndex()][position.getColumnIndex() + 1]);
        return neighbours; //return list
    }

    /**
     * add to candidates position's neighbours who might become a path
     *
     */

    private void addCandidatesToSet(Position position) {
        if (position != null) {
            List<Position> neighbours;
            neighbours = myNeighbours(position);
            for (int i = 0; i < neighbours.size(); i++)//check if the add cell has maximum of one 0 neighbour
                if (myMaze.getCellValue(neighbours.get(i).getRowIndex(), neighbours.get(i).getColumnIndex()) == 1 && numOfPathNeighbours(neighbours.get(i)) <= 1)
                    set.add(neighbours.get(i)); //only legal neighbours and that have 1
        }
    }

    /**
     * returns number of neighbours which are already on the path
     *
     */

    private int numOfPathNeighbours(Position position) {
        if (position == null)
            return 0;
        int count = 0;
        //if neighbour legal
        //Down
        if (isValid(position.getRowIndex() - 1, position.getColumnIndex()) &&
                myMaze.getCellValue(position.getRowIndex() - 1, position.getColumnIndex()) == 0)
            count++;
        //Up
        if (isValid(position.getRowIndex() + 1, position.getColumnIndex()) &&
                myMaze.getCellValue(position.getRowIndex() + 1, position.getColumnIndex()) == 0)
            count++;

        //Right
        if (isValid(position.getRowIndex(), position.getColumnIndex() + 1) &&
                myMaze.getCellValue(position.getRowIndex(), position.getColumnIndex() + 1) == 0)
            count++;
        //Left
        if (isValid(position.getRowIndex(), position.getColumnIndex() - 1) &&
                myMaze.getCellValue(position.getRowIndex(), position.getColumnIndex() - 1) == 0)
            count++;

        return count;
    }

    /**
     * checks if the position is on maze's bounds
     *
     */

    private boolean isValid(int row, int column) {
        if (row < 0 || row >= myMaze.numOfRows()) // check if out of bound
            return false;
        return column >= 0 && column < myMaze.numOfColumns();
    }


    /**
     * set a random goal position in the last raw or last columns
     */
    private void setRandomGoalPosition() {
        int x;
        boolean found = false;
        while (!found) {
            x = (Math.random() < 0.5) ? 0 : 1;
            if (x == 1) {
                x = (int) (Math.random() * myMaze.numOfColumns());
                if (myMaze.getCellValue(myMaze.numOfRows() - 1, x) == 0) {
                    myMaze.setGoalPosition(new Position(myMaze.numOfRows() - 1, x));
                    found = true;
                }
            } else {
                x = (int) (Math.random() * myMaze.numOfRows());
                if (myMaze.getCellValue(x, myMaze.numOfColumns() - 1) == 0) {
                    myMaze.setGoalPosition(new Position(x, myMaze.numOfColumns() - 1));
                    found = true;
                }
            }
        }
    }
}
