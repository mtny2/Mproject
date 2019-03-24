package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int i, int i1) {
        return 0;
    }

    @Override
    public Maze generate(int i, int i1) {
        return null;
    }
}
