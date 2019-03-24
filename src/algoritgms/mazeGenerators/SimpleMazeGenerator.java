package algoritgms.mazeGenerators;



































public class SimpleMazeGenerator extends AMazeGenerator {
    private Maze myMaze;
    //make the all array 1= Black.
    public Maze generator(int rows, int columns) {
        int maze[][] = arrBloacks(rows, columns);
        //which index start.
        int randomSide = (int) (Math.random() * 4);
        int randomIndex = 0;

        if (randomSide == 1 || randomSide == 3) {
            randomIndex = (int) (Math.random() * rows);
            if (randomSide == 1) {
                maze[0][randomIndex] = '0';
                directionMaze(maze, rows, columns, 0, randomIndex);
            } else {
                maze[rows - 1][randomIndex] = '0';
                directionMaze(maze, rows, columns, rows - 1, randomIndex);
            }
        } else {
            randomIndex = (int) (Math.random() * columns);
            if (randomSide == 3) {
                maze[randomIndex][0] = '0';
                directionMaze(maze, rows, columns, randomIndex, 0);
            } else {
                maze[randomIndex][columns - 1] = '0';
                directionMaze(maze, rows, columns, randomIndex, columns - 1);
            }
        }
        return myMaze;
    }
    // }

    public boolean directionMaze(int arr[][], int maxRows, int maxColumns, int currentRow, int currentColumns) {
        boolean out = false;
        while (out == false) {
            int randomSide = (int) (Math.random() * 4);
            //Up
            if (randomSide == 1) {
                out = outOfbound(maxRows, maxColumns, currentRow - 1, currentColumns);
                if (out)
                    break;
                arr[currentRow - 1][currentColumns] = '0';
                directionMaze(arr, maxRows, maxColumns, currentRow - 1, currentColumns);
            } //Down
            if (randomSide == 3) {
                out = outOfbound(maxRows, maxColumns, currentRow + 1, currentColumns);
                if (out)
                    break;
                arr[currentRow + 1][currentColumns] = '0';
                directionMaze(arr, maxRows, maxColumns, currentRow + 1, currentColumns);
            }//Right
            if (randomSide == 2) {
                out = outOfbound(maxRows, maxColumns, currentRow, currentColumns + 1);
                if (out)
                    break;
                arr[currentRow][currentColumns + 1] = '0';
                directionMaze(arr, maxRows, maxColumns, currentRow, currentColumns + 1);
            }//Left
            if (randomSide == 4) {
                out = outOfbound(maxRows, maxColumns, currentRow, currentColumns - 1);
                if (out)
                    break;
                arr[currentRow][currentColumns + 1] = '0';
                directionMaze(arr, currentRow, currentColumns, currentRow, currentColumns - 1);
            }
        }
        return true;
    }

    // Check if out of bound, if yes finish the maze.
    public boolean outOfbound(int maxRows, int maxColumns, int currentRow, int currentColumns) {
        if (currentRow - 1 < 0 || currentRow + 1 == maxRows ||
                currentColumns - 1 < 0 || currentColumns + 1 == maxColumns)
            return true;
        return false;

    }

    //Make the all arr(maze) 1 = that mean block.
    public int[][] arrBloacks(int rows, int columns) {
        //Make 1 arr
        int maze[][] = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                maze[i][j] = 1;
            }
        }
    }


}