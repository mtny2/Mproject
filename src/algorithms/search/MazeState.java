package algorithms.search;


/**
 * MazeState extends AState
 * Object the contains row and column
 * equals function - equable function to compare between mazeState's
 */
public class MazeState extends AState {
    private int row;
    private int col;

    //Constructor
    public MazeState(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    public String toString() {
        String x;
        x = row + "," + col;
        return x;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     *
     */
    public boolean equals(Object o) {
        //check that object is MazeState type
        if (!(o instanceof MazeState))
            return false;
        return ((MazeState) o).row == row && ((MazeState) o).col == col;
    }
}
