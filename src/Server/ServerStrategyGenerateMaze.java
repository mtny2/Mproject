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

            ByteArrayOutputStream toClientArray = new ByteArrayOutputStream();
            OutputStream ops = new MyCompressorOutputStream(toClientArray);

            toClient.flush();

            Properties prop = new Properties();
            File fileCheck = new File("config.properties");

            //  String mazeType = "MyMazeGenerator"; //default
            IMazeGenerator Type;
            if (fileCheck.length() != 0) { //if properties file empty, and hasnt been run yet
                InputStream input = new FileInputStream("config.properties");
                // load a properties file
                prop.load(input); //load config file to prop
                //   mazeType = prop.getProperty("MazeGenerator"); //get algorithm type from config file
                //   if (mazeType==null)
                //      mazeType="MyMazeGenerator";
                //    if (mazeType.equals("SimpleMazeGenerator"))
                //   Type = new SimpleMazeGenerator();
                //   else
                Type = new MyMazeGenerator();
            } else {
                Server.Configurations.config();
                Type = new MyMazeGenerator(); //default
            }
            int[] mazeProp = (int[]) fromClient.readObject(); //get maze size , 2 size array
            Maze returnToClient = Type.generate(mazeProp[0], mazeProp[1]); // create maze
            byte[] ReturnMaze = returnToClient.toByteArray(); //make it byte array
            //compress the maze
            ops.write(ReturnMaze);
            //write back to user
            toClient.writeObject(toClientArray.toByteArray());
            ops.flush();
            toClient.flush();


        } catch (IOException | ClassNotFoundException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
