package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * server strategy SolveSearchProblem class implements IServerStrategy
 * Get a problem from the client and use searchAlgorithm to solve him
 */
public class ServerStrategySolveSearchProblem  implements IServerStrategy {

    static final Charset UTF_8 = Charset.forName("UTF-8");


    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            String dir = System.getProperty("java.io.tmpdir");
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            Object readObject = fromClient.readObject();
            if (readObject == null || !(readObject instanceof Maze))return;
            // make a dir
            Maze mazeFromClient = (Maze)readObject;
            Solution solution = null;
            int count = 0;
            //search if the maze exists
            byte[] byteMazeFromClient = mazeFromClient.toByteArray();
            boolean findSol = false;
            int i;
            //Serach if there is a solution
            for (i=0;  findSol==false && i < count ; i++) {
                Path fileLocation = Paths.get(dir  + "/maze" + i + ".txt");
                byte[] byteFile = Files.readAllBytes(fileLocation);
                findSol = Arrays.equals(byteMazeFromClient, byteFile);
            }
            //if exists - return solution
            if (findSol==true) {
                solution = returnSolution(i, dir);
            }
            //write a sol
            else {
                Path file = Paths.get(dir + "/maze" + count + ".txt");
                Files.write(file, mazeFromClient.toByteArray());
                SearchableMaze searchableMaze = new SearchableMaze(mazeFromClient);
                ASearchingAlgorithm searcher =;
                Solution solve = searcher.solve(searchableMaze);
                writeSolution(solve, dir, count);
                count++;
            }
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.writeObject(solution);
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Solution returnSolution(int i, String dir) throws IOException {
        FileReader fileReader = null;
            fileReader = new FileReader(dir + "/solution" + i + ".txt");

        fileReader.close();
        return new Solution();
    }

    private void writeSolution(Solution sol, String dir, int count)  {
        ArrayList<String> list = new ArrayList<>();
        Path path = Paths.get(dir + "/solution" + count + ".txt");
        ArrayList<AState> solList = sol.getSolutionPath();
        for (int i = 0; i < solList.size(); i++) {
            list.add((solList.get(i)).toString());
        }
        try {
            Files.write(path, list, UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}