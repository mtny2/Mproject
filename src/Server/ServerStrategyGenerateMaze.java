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
    private static String pathResources = "./resources/config.properties";

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            // Connections
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ByteArrayOutputStream toClientArray = new ByteArrayOutputStream();
            OutputStream ops = new MyCompressorOutputStream(toClientArray);
            toClient.flush();
            Properties properties = new Properties();
            String generatorStr;
            IMazeGenerator generatorMaze;
            // load a properties file and get the generator type
            Configurations.config();
            InputStream input = new FileInputStream(pathResources);
            properties.load(input);
            generatorStr = properties.getProperty("MazeGenerator");
            if (generatorStr.equals("SimpleMazeGenerator"))
                generatorMaze = new SimpleMazeGenerator();
            else if (generatorStr.equals("EmptyMazeGenerator"))
                generatorMaze = new EmptyMazeGenerator();
            else
                generatorMaze = new MyMazeGenerator();// default maze generator is "MyMazeGenerator"
            //get maze size
            int[] mazeSize = (int[]) fromClient.readObject();
            int rows = mazeSize[0];
            int columns = mazeSize[1];
            Maze returnToClient = generatorMaze.generate(rows, columns);
            byte[] ReturnMaze = returnToClient.toByteArray();
            ops.write(ReturnMaze);
            toClient.writeObject(toClientArray.toByteArray());
            // Flushes the output stream and forces any buffered output bytes to be written out.
            ops.flush();
            toClient.flush();
        //Exceptions
        } catch (IOException | ClassNotFoundException |NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
