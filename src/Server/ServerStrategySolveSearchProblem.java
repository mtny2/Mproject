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
        //  Properties prop = new Properties(); // new Properties type
        //  InputStream input = null;
        //    File fileCheck = new File("config.properties"); //try to get config file
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            Solution solve;
            Maze returnToClient = (Maze) fromClient.readObject(); // read maze from client
            String tempDirectoryPath = System.getProperty("java.io.tmpdir"); //create a temp direct
            String fileName = returnToClient.toString(); //name of maze
            byte tempByteArray[] = returnToClient.toByteArray();
            String firstBinaryLine = "";
            for (int k = 8; k < 100; k++)
                if (k >= tempByteArray.length)
                    break;
                else
                    firstBinaryLine += tempByteArray[k];
            fileName = fileName + "-" + firstBinaryLine;

            File file = new File(tempDirectoryPath, fileName); //create file from maze name
            if (file.exists()) { //if it exist , solve gets the maze data
                FileInputStream fileInput = new FileInputStream(file);
                ObjectInputStream FileToReturn = new ObjectInputStream(fileInput);
                solve = (Solution) FileToReturn.readObject();
                FileToReturn.close();
            } else {
                //Server.Configurations.config(); //create config file if not exists
                //     String AlgorithmSearch = "BreadthFirstSearch"; //default
                SearchableMaze searchableMaze = new SearchableMaze(returnToClient); // create a new searchable maze
                ASearchingAlgorithm Type;
                //   if (fileCheck.length() != 0) { //if properties file empty, and has not been run yet
                //       input = new FileInputStream("config.properties");
                //       // load a properties file
                //       prop.load(input);
                //       AlgorithmSearch = prop.getProperty("MazeAlgorithmSearch"); //get algorithm type from config file
                //   }
                //get from user the type of maze from prop file
                //    if (AlgorithmSearch == null)
                //          AlgorithmSearch="BreadthFirstSearch";
                //solve maze
                //        if (AlgorithmSearch.equals("DepthFirstSearch"))
                //        Type = new DepthFirstSearch();
                //    else if (AlgorithmSearch.equals("BestFirstSearch"))
                //     Type = new BestFirstSearch();
                //   else
                Type = new BreadthFirstSearch();
                solve = Type.solve(searchableMaze);

                FileOutputStream fileOut = new FileOutputStream(file);
                ObjectOutputStream objectReturn = new ObjectOutputStream(fileOut);

                objectReturn.writeObject(solve);
                objectReturn.flush();


            }
            toClient.writeObject(solve);
            toClient.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
