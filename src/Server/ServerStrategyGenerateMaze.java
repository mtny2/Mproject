package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;


import java.io.*;
import java.util.Properties;

/**
 * server strategy GenerateMaze class implements IServerStrategy
 * Get size of maze, create maze and than compress and sent to client
 */

public class ServerStrategyGenerateMaze implements IServerStrategy {
    private static String pathResources = "resources/config.properties";

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ByteArrayOutputStream toClientArray = new ByteArrayOutputStream();
            OutputStream ops = new MyCompressorOutputStream(toClientArray);
            toClient.flush();
            Properties properties = new Properties();
            File configFile = new File(pathResources);
            String generatorStr;
            IMazeGenerator generatorMaze;
            //if properties file empty, and has not been run yet
          //  if (configFile.length() == 0)
                Server.Configurations.config();

                InputStream input = new FileInputStream(pathResources);
                // load a properties file
                properties.load(input);
                //check the generator type from config file.
                generatorStr = properties.getProperty("MazeGenerator"); //get algorithm type from config file
                if (generatorStr.equals("SimpleMazeGenerator"))
                    generatorMaze = new SimpleMazeGenerator();
                else if (generatorStr.equals("EmptyMazeGenerator"))
                    generatorMaze = new EmptyMazeGenerator();
                else
                    generatorMaze = new MyMazeGenerator();
            //} else {
               // generatorMaze = new MyMazeGenerator(); //MyMazeGenerator - default

            //get maze size
            int[] mazeSize = (int[]) fromClient.readObject();
            int row = mazeSize[0];
            int col = mazeSize[1];
            // create maze and make him byte array and then compress the maze.
            Maze returnToClient = generatorMaze.generate(row, col);
            byte[] ReturnMaze = returnToClient.toByteArray();
            ops.write(ReturnMaze);
            //write back to client
            toClient.writeObject(toClientArray.toByteArray());
            ops.flush();
            toClient.flush();
        } catch (IOException |
                ClassNotFoundException |

                NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
