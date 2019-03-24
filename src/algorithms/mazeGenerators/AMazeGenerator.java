package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator{

    public abstract Maze generate(int rows, int columns);

    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long StartTime = System.currentTimeMillis(); //get start time
        if (rows < 2)
            rows = 10;
        if (columns < 2)
            columns = 10;
        generate(rows, columns); //generate maze
        long StopTime = System.currentTimeMillis(); //get end time
        return StopTime - StartTime; //return the difference
    }
}

