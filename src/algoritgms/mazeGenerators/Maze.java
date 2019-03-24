package algoritgms.mazeGenerators;

public class Maze {

    public int rows;
    public int colums;
    public int maze[][];


    public Maze (int arr[][],int rows,int colums){

        int newMaze[][] = new int[rows][colums];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colums; j++) {

                this.maze[i][j] =arr[i][j] ;
            }
        }
        this.maze=newMaze;
    }
}
