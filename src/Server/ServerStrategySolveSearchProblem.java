package Server;

//import java.io.InputStream;
//import java.io.OutputStream;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private static String pathResources = "resources/config.properties";

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            Properties properties = new Properties();
            InputStream input;
            File configFile = new File(pathResources);
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            Solution sol = null;
            // read maze from the client, and create a temp dir
            Maze mazeToClient = (Maze) fromClient.readObject();
            String dir = System.getProperty("java.io.tmpdir");
            String mazeName = mazeToClient.toString();
            byte tempByteArray[] = mazeToClient.toByteArray();
            String hashName = "mazeSol-" + mazeName.substring(31);

            File SolFileCreate = new File(dir, hashName);

            //create file-maze name, and if exist take the solve else solve
            File mazeFileCreate = new File(dir, mazeName);

            File dirTemp = new File(dir);
            File[] list = dirTemp.listFiles(new MyFilter());
            boolean areMazesEquals = false, solReturned=false;
            if (list != null) {
                //if(!areMazesEquals)
                for (File filMaze : list) {
                    if(!solReturned)
                    if (filMaze.getName().contains("Maze")) {
                        byte savedMazeBytes[];
                        // try {
                        //read maze from file
                        InputStream in = new MyDecompressorInputStream(new FileInputStream(filMaze));
                        //  byte m[] =maze.toByteArray();
                        // int x=m.length;
                        savedMazeBytes = new byte[tempByteArray.length];
                        in.read(savedMazeBytes);
                        in.close();
                        areMazesEquals = Arrays.equals(savedMazeBytes, tempByteArray);
                        if (areMazesEquals) {
                            for (File filSol : list) {
                                if (filSol.getName().equals("mazeSol-"+filMaze.getName().substring(31))) {
                                    FileInputStream fileInput = new FileInputStream(filSol);
                                    ObjectInputStream FileToReturn = new ObjectInputStream(fileInput);
                                    sol = (Solution) FileToReturn.readObject();
                                    FileToReturn.close();
                                    solReturned=true;
                                    break;
                                }
                            }
                        }
                    }
                }

            } if(!areMazesEquals || list==null) {
                String algSearch;
                SearchableMaze searchableMaze = new SearchableMaze(mazeToClient); // create a new searchable maze
                ASearchingAlgorithm algorithmSolve;
                if (configFile.length() == 0)  //if properties file empty, and has not been run yet
                    Server.Configurations.config();

                input = new FileInputStream(pathResources);
                // load a properties file
                properties.load(input);
                algSearch = properties.getProperty("MazeAlgorithmSearch"); //get algorithm type from config file
                //get from user the type of maze from prop file
                if (algSearch.equals("DepthFirstSearch"))
                    algorithmSolve = new DepthFirstSearch();
                else if (algSearch.equals("BestFirstSearch"))
                    algorithmSolve = new BestFirstSearch();
                else if (algSearch.equals("BreadthFirstSearch"))
                    algorithmSolve = new BreadthFirstSearch();
                else
                    algorithmSolve = new BreadthFirstSearch();
                sol = algorithmSolve.solve(searchableMaze);
                //Create "maze" file in the folder.
                FileOutputStream fileOut = new FileOutputStream(SolFileCreate);
                ObjectOutputStream objectReturn = new ObjectOutputStream(fileOut);
                objectReturn.writeObject(sol);
                objectReturn.flush();

                OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazeFileCreate));
                out.write(tempByteArray);
                out.flush();
                out.close();


            }
            toClient.writeObject(sol);
            toClient.close();

        } catch (IOException |
                ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    class MyFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith("algorithms");
        }
    }



}
