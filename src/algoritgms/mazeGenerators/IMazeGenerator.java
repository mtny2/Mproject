package algoritgms.mazeGenerators;

public interface IMazeGenerator {

    Maze generator (int rows, int columns);
    long measureAlgorithmTimeMillis(int rows, int columns);
}
