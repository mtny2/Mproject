package Server;

import java.io.*;
import java.util.Properties;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        Properties prop = new Properties();

        int []tmp = new InputStreamReader(inFromClient);
        BufferedWriter toClient = new BufferedWriter(new PrintWriter(outToClient));

        String clientCommand;
        try {
            while (fromClient != null && !(clientCommand = fromClient.readLine()).equals("exit")) {
                Thread.sleep(5000);
                toClient.write(new StringBuilder(clientCommand).reverse().toString() + "\n");
                toClient.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}