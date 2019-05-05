package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;

import java.io.*;
import java.util.Properties;
/**
 * server strategy GenerateMaze class implements IServerStrategy
 * Get size of maze, create maze and than compress and sent to client
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            // get maze size
            toClient.flush();
            int[] mazeSize = (int[])fromClient.readObject();
            //create maze
            IMazeGenerator im= new MyMazeGenerator();
            Maze maze = im.generate(mazeSize[0],mazeSize[1]);
            //compress the maze and send him to the client
            OutputStream ops = new MyCompressorOutputStream(outToClient);
            ops.write(maze.toByteArray());
            toClient.writeObject(ops);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}