package algorithms.mazeGenerators;
/**
 * Abstract Class actualize IMazeGenerator
 * generate - return maze: evrey class that implements IMazeGenerator actualize difrrent
 * measureAlgorithmTimeMillis - only here its actualize.
 */
public abstract class AMazeGenerator implements IMazeGenerator{

    public abstract Maze generate(int rows, int columns);

    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long StartTime = System.currentTimeMillis(); //start time
        generate(rows, columns); //generate new maze
        long StopTime = System.currentTimeMillis(); //end time
        return StopTime - StartTime; //return the difference between the times
    }
}

