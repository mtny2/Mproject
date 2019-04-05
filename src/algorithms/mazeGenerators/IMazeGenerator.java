package algorithms.mazeGenerators;

public interface IMazeGenerator {
    /**
     * Interface Class methods that need  actualize
     * generate - return maze: crate a new maze.
     * measureAlgorithmTimeMillis - return the time to create a new maze.
     */
    Maze generate(int rows, int columns);

    long measureAlgorithmTimeMillis(int rows, int columns);

}
