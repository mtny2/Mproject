package algoritgms.mazeGenerators;

import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {
    @Override
    public Maze generator(int rows, int columns) {
        Random random = new Random();
        //Random Starting Location.
        int x = random.nextInt(rows);
        int y = random.nextInt(columns);

    }
}
