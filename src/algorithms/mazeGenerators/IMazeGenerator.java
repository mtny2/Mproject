package algorithms.mazeGenerators;
/**
 * Interface Class methods that need  actualize
 * generate - return maze: crate a new maze.
 * measureAlgorithmTimeMillis - return @param long - time to create a new maze.
 */
public interface IMazeGenerator {

    Maze generate(int rows, int columns); // create a maze
    long measureAlgorithmTimeMillis(int rows, int columns);// return time to create

}
