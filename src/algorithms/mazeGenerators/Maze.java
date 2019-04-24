package algorithms.mazeGenerators;

public class Maze {
    /**
     * Maze Class actualize
     *
     * @param string[][] - myMaze : A maze is represented by a two-dimensional array
     * @param Position   - startPosition : A start position of the maze
     * @param Position   - goalPosition : A last position of the maze (the end)
     *
     */
    private int[][] myMaze;
    private Position startPosition;
    private Position goalPosition;


    public Maze(int row, int column) {
        if (row < 2)
            row = 10;
        if (column < 2)
            column = 10;
        myMaze = new int[row][column];
        startPosition = new Position(0,0); //default start = (0,0)
    }

    /**
     * Constructor
     *
     *Get byte array and build a maze
     */
   public Maze(byte[] byteMaze) {
       int k = 0;
       int [] tmp=new int[8];
      for (; k < 8; k++){
           if (byteMaze[k] < 0)
               tmp[k]=byteMaze[k] + 256;
           else tmp[k]=byteMaze[k];}
       int rows = tmp[0] * 256 + tmp[1];
       int cols = tmp[2] * 256 + tmp[3];
       myMaze = new int[rows][cols];
       for (int i = 0; i < rows; i++) {
           for (int j = 0; j < cols; j++) {
               myMaze[i][j] = (int) byteMaze[k];
               k++;
           }
       }
       setStartPosition(new Position(0, 0));
       int GoalPosRow = tmp[4] * 256 + tmp[5];
       int GoalPosCol = tmp[6] * 256 + tmp[7];
       setGoalPosition(new Position(GoalPosRow, GoalPosCol));
   }

    public Position getStartPosition()
    {
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

    public void changeCellValue(int row, int column, int value) {
        if (value == 0 || value == 1)
            if (row < numOfRows() && column < numOfColumns() && row >= 0 && column >= 0)
                myMaze[row][column] = value;
    }


    public int getCellValue(int row, int column) {

        return myMaze[row][column];
    }


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


    /**
     --------- Part B ----------
     *
     * Converts this maze to byte array.
     *
     */

    public byte[] toByteArray() {

        int rows = numOfRows();
        int rowsNeeded = rows / 256;
        int rowsModulo = rows % 256;

        int cols = numOfColumns();
        int colsNeeded = cols / 256;
        int colsModulo = cols % 256;

        int goalPosRow = getGoalPosition().getRowIndex();
        int goalRowsNeeded = goalPosRow / 256;
        int goalRowsModulo = goalPosRow % 256;

        int goalPosCol = getGoalPosition().getColumnIndex();
        int goalColsNeeded = goalPosCol / 256;
        int goalColsModulo = goalPosCol % 256;

        //first 8 indexes are for details of maze
        byte[] mazeAsByteArray = new byte[rows * cols + 8];
        mazeAsByteArray[0] = (byte) rowsNeeded;
        mazeAsByteArray[1] = (byte) rowsModulo;
        mazeAsByteArray[2] = (byte) colsNeeded;
        mazeAsByteArray[3] = (byte) colsModulo;
        mazeAsByteArray[4] = (byte) goalRowsNeeded;
        mazeAsByteArray[5] = (byte) goalRowsModulo;
        mazeAsByteArray[6] = (byte) goalColsNeeded;
        mazeAsByteArray[7] = (byte) goalColsModulo;
        int k = 8;
        for (int i = 0; i < myMaze.length; i++)
            for (int j = 0; j < myMaze[0].length; j++) {
                mazeAsByteArray[k] = (byte) myMaze[i][j];
                k++;
            }
        return mazeAsByteArray;
    }




}
