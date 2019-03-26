package algoritgms.mazeGenerators;

public interface IMazeGenerator {

    algorithms.mazeGenerators.Maze generate(int rows, int columns);

    long measureAlgorithmTimeMillis(int rows, int columns);

}
