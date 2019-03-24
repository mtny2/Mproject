package algoritgms.mazeGenerators;


public  abstract  class AMazeGenerator implements IMazeGenerator {

    @Override
    public long measureAlgorithmTimeMillis(int rows, int columns) {
       long timeStart =  System.currentTimeMillis();
       generator(rows, columns);
       long timeEnd = System.currentTimeMillis();
       return  timeEnd-timeStart;
    }
}
