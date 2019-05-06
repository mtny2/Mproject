package Server;

//import java.io.InputStream;
//import java.io.OutputStream;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        Properties properties = new Properties();
        InputStream input;
        File configFile = new File("resources/config.properties"); //try to get config file
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            Solution sol;
            // read maze from the client, and create a temp dir
            Maze returnToClient = (Maze) fromClient.readObject();
            String dir = System.getProperty("java.io.tmpdir");
            String mazeName = returnToClient.toString();
            byte tempByteArray[] = returnToClient.toByteArray();
            String firstBinaryLine = "";
            // ....
            for (int k = 8; k < 100; k++)
                if (k >= tempByteArray.length)
                    break;
                else
                    firstBinaryLine += tempByteArray[k];
            mazeName = mazeName + "-" + firstBinaryLine;
            //create file-maze name, and if exist take the solve else solve
            File file = new File(dir, mazeName);
            if (file.exists()) {
                FileInputStream fileInput = new FileInputStream(file);
                ObjectInputStream FileToReturn = new ObjectInputStream(fileInput);
                sol = (Solution) FileToReturn.readObject();
                FileToReturn.close();
            } else {
                Server.Configurations.config();
                String AlgorithmSearch = "BreadthFirstSearch"; //default
                SearchableMaze searchableMaze = new SearchableMaze(returnToClient); // create a new searchable maze
                ASearchingAlgorithm algorithmSolve;
                if (configFile.length() != 0) { //if properties file empty, and has not been run yet
                    input = new FileInputStream("resources/config.properties");
                    // load a properties file
                    properties.load(input);
                    AlgorithmSearch = properties.getProperty("MazeAlgorithmSearch"); //get algorithm type from config file
                }
                //get from user the type of maze from prop file
                if (AlgorithmSearch == null)
                    AlgorithmSearch = "BreadthFirstSearch";
                //solve maze
                if (AlgorithmSearch.equals("DepthFirstSearch"))
                    algorithmSolve = new DepthFirstSearch();
                else if (AlgorithmSearch.equals("BestFirstSearch"))
                    algorithmSolve = new BestFirstSearch();
                else if (AlgorithmSearch.equals("BreadthFirstSearch"))
                    algorithmSolve = new BreadthFirstSearch();
                else
                    algorithmSolve = new BreadthFirstSearch();
                sol = algorithmSolve.solve(searchableMaze);

                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream objectReturn = new ObjectOutputStream(fileOut);

                objectReturn.writeObject(sol);
                objectReturn.flush();

            }
            toClient.writeObject(sol);
            toClient.close();

        } catch (IOException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
