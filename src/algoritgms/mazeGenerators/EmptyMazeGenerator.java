package algoritgms.mazeGenerators;


public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    Maze generator(int rows, int columns) {
        int maze[][] = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                maze[i][j] = 0;
            }
        }
        Maze mazeObj = new Maze(maze,rows,columns);
        return mazeObj;
    }
}