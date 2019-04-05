package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{
    /**
     * Abstract Class implements IMazeGenerator
     * generate - return maze: every class that implements IMazeGenerator actualize different
     * measureAlgorithmTimeMillis - measure the time that the algorithm runs in milliseconds
     */
    public abstract Maze generate(int rows, int columns);

    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long StartTime = System.currentTimeMillis(); //start time
        if (rows < 2)
            rows = 10;
        if (columns < 2)
            columns = 10;
        generate(rows, columns);
        long StopTime = System.currentTimeMillis(); //end time
        return StopTime - StartTime; //return the difference between the times
    }
}

