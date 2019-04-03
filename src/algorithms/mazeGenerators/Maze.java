package algorithms.mazeGenerators;

/**
 * Maze Class actualize
 *
 * @param string[][] - myMaze : A maze is represented by a two-dimensional array
 * @param Position   - startPosition : A start position of the maze
 * @param Position   - goalPosition : A last position of the maze (the end).
 * Print method, changeCellValue method and set/get methods
 *
 */
public class Maze {

    private String[][] myMaze;
    private Position startPosition;
    private Position goalPosition;

    //Constructor
    public Maze(int row, int column) {
        myMaze = new String[row][column];
        startPosition = new Position(0, 0); //default start = (0,0)
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        if (startPosition.getRowIndex() < this.numOfRows() && startPosition.getColumnIndex() < this.numOfColumns())
            if (startPosition.getColumnIndex() >= 0 && startPosition.getRowIndex() >= 0)
                this.startPosition = startPosition;
    }
    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position goalPosition) {
        if (goalPosition.getRowIndex() < this.numOfRows() && goalPosition.getColumnIndex() < this.numOfColumns())
            if (goalPosition.getColumnIndex() >= 0 && goalPosition.getRowIndex() >= 0)
                this.goalPosition = goalPosition;
    }

    public int numOfRows() {
        return myMaze.length;
    }

    public int numOfColumns() {
        return myMaze[0].length;
    }

    public void changeCellValue(int row, int column, String value) {
        if (value == "0" || value == "1")
            if (row < numOfRows() && column < numOfColumns() && row >= 0 && column >= 0)
                myMaze[row][column] = value;
    }


    public String getCellValue(int row, int column) {
        return myMaze[row][column];
    }

// Print method
    public void print() {
        for (int i = 0; i < myMaze.length; i++) {
            System.out.println(" "); //go down at end of line of array
            for (int j = 0; j < myMaze[0].length; j++)
                if (i == startPosition.getRowIndex() && j == startPosition.getColumnIndex())
                    System.out.print(" S"); //start position
                else if (i == goalPosition.getRowIndex() && j == goalPosition.getColumnIndex())
                    System.out.print(" E"); //end position
                else
                    System.out.print(" " + myMaze[i][j]); //print if 0 or 1 and add space
        }
        System.out.println(" ");
    }
}
