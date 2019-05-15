package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;

/**
 * server strategy SolveSearchProblem class implements IServerStrategy
 * Read the maze from the client, and get the solution if excite from temp dir
 */

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private static String pathResources = "./resources/config.properties";

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            // Connections
            Properties properties = new Properties();
            InputStream input;
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            Solution solution = null;
            Maze mazeToClient = (Maze) fromClient.readObject();
            String dir = System.getProperty("java.io.tmpdir");
            String mazeName = mazeToClient.toString();
            byte tempByteArray[] = mazeToClient.toByteArray();
            String hashName = "mazeSol-" + mazeName.substring(31);
            File SolFileCreate = new File(dir, hashName);
            //create file-maze name, and if exist take the solve else solve
            File mazeFileCreate = new File(dir, mazeName);
            File dirTemp = new File(dir);
            // use filter method.
            File[] list = dirTemp.listFiles(new MyFilter());
            boolean areMazesEquals = false, solutionReturned = false;
            // over all the files at the dir(after the filter)
            if (list != null)
                for (File filMaze : list)
                    if (!solutionReturned)
                        if (filMaze.getName().contains("Maze")) {
                            byte savedMazeBytes[];
                            //read maze from file
                            InputStream in = new MyDecompressorInputStream(new FileInputStream(filMaze));
                            savedMazeBytes = new byte[tempByteArray.length];
                            in.read(savedMazeBytes);
                            in.close();
                            areMazesEquals = Arrays.equals(savedMazeBytes, tempByteArray);
                            if (areMazesEquals)
                                for (File filSol : list)
                                    if (filSol.getName().equals("mazeSol-" + filMaze.getName().substring(31))) {
                                        FileInputStream fileInput = new FileInputStream(filSol);
                                        ObjectInputStream FileToReturn = new ObjectInputStream(fileInput);
                                        solution = (Solution) FileToReturn.readObject();
                                        FileToReturn.close();
                                        solutionReturned = true;
                                        break;
                                    }
                        }
            if (!areMazesEquals || list == null || !solutionReturned) {
                String algSearch;
                SearchableMaze searchableMaze = new SearchableMaze(mazeToClient); // create a new searchable maze
                ASearchingAlgorithm algorithmSolve;
                Configurations.config();

                input = new FileInputStream(pathResources);
                // load a properties file and get the search algorithm type
                properties.load(input);
                algSearch = properties.getProperty("MazeAlgorithmSearch");
                if (algSearch.equals("DepthFirstSearch"))
                    algorithmSolve = new DepthFirstSearch();
                else if (algSearch.equals("BestFirstSearch"))
                    algorithmSolve = new BestFirstSearch();
                else if (algSearch.equals("BreadthFirstSearch"))
                    algorithmSolve = new BreadthFirstSearch();
                else
                    algorithmSolve = new BreadthFirstSearch();// default search algorithm type - "BreadthFirstSearch"
                solution = algorithmSolve.solve(searchableMaze);
                //Create "maze" file in the dir.
                FileOutputStream fileOut = new FileOutputStream(SolFileCreate);
                ObjectOutputStream objectReturn = new ObjectOutputStream(fileOut);
                objectReturn.writeObject(solution);
                objectReturn.flush();
                mazeToDir(mazeFileCreate, tempByteArray);
            }
            toClient.writeObject(solution);
            toClient.close();

            //Exceptions
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // synchronized make sure that every time run only one thread.
    // save the maze at dir.
    public synchronized void mazeToDir(File f, byte[] b) {
        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(f));
            out.write(b);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MyFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return name.contains("maze");
        }
    }

}
