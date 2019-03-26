package algoritgms.mazeGenerators;


public class EmptyMazeGenerator extends AMazeGenerator {

    @Override
    Maze generator(int rows, int columns) {
        int maze[][] = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                maze[i][j] = 0;
            }
        }
        Maze mazeObj = new Maze(maze,rows,columns);
        return mazeObj;
    }
}


//package algorithms.mazeGenerators;
//
//public class EmptyMazeGenerator extends AMazeGenerator {
//
//    private Maze MyMaze;
//
//    public Maze generate(int rows, int columns) {
//        //  if (rows < 2) //if rows <2 then create default size of 10
//        //      rows = 10;
//        //  if (columns < 2) //if columns <2 then create default size of 10
//        //      columns = 10;
//        MyMaze = new Maze(rows, columns);
//        int MazeValue;
//        String ValueStr = " ";
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                if (i == 0 || i == rows - 1 || j == 0 || j == columns - 1) {
//                    if (ValueStr == " " || ValueStr=="0")
//                        MyMaze.changeCellValue(i, j, ValueStr);
//                } else {
//                    ValueStr = (Math.random() < 0.5) ? "0" : "1";
//                    if (ValueStr == "0")
//                        MyMaze.changeCellValue(i, j, ValueStr);//fill maze randomly with 0's and 1's
//                    else {
//                        ValueStr = " ";
//                        MyMaze.changeCellValue(i, j, ValueStr);
//                    }
//                }
//            }
//        }
//
//        return MyMaze;
//    }
//}