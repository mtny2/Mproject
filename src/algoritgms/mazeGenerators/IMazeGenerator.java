package algoritgms.mazeGenerators;

public interface IMazeGenerator {

    Maze generator (int row, int col);
    long measureAlgorithmTimeMillis(int row, int col);
}
