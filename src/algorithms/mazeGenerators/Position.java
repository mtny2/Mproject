package algorithms.mazeGenerators;

/**
 * Position Class, my location
 *
 * @param column
 * @param row
 */
public class Position {

    private int row;
    private int column;

    //Constructor
    public Position(int row, int column) {
        if (row < 0)
            row = 0;
        if (column < 0)
            column = 0;
        this.row = row;
        this.column = column;
    }

    public int getRowIndex() {
        return row;
    }
    public int getColumnIndex() {
        return column;
    }
    public String toString() {
        return "{" + row + "," + column + "}";
    }
}