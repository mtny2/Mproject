package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{

    public abstract Maze generate(int rows, int columns);

    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long StartTime = System.currentTimeMillis(); //start time
       // if (rows < 2)
       //     rows = 10;
       // if (columns < 2)
       //     columns = 10;
        generate(rows, columns); //generate new maze
        long StopTime = System.currentTimeMillis(); //end time
        return StopTime - StartTime; //return the difference between the times
    }
}

