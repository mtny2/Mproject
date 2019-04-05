package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    /**
     * Generate a zero maze(empty maze)
     *
     */
    private Maze MyMaze;

    public Maze generate(int rows, int columns) {
        MyMaze = new Maze(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                MyMaze.changeCellValue(i, j, "0");
            }
        }
        Position start = new Position(0, 0);
        MyMaze.setStartPosition(start);
        Position end = new Position(rows-1, columns-1);
        MyMaze.setGoalPosition(end);
        return MyMaze;

    }
}