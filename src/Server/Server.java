package Server;

import Server.IServerStrategy;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    //  private static final Logger LOG = LogManager.getLogger(); //Log4j2

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    //  public Server(int port, int listeningInterval, Server.ServerStrategySolveSearchProblem serverStrategySolveSearchProblem) {
    // }

    public void start() {
        new Thread(() -> {
            serverStrategy();
        }).start();
    }

    private void serverStrategy() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            Properties prop = new Properties();
            InputStream input = null;
            File file = new File("config.properties");
            int cores = 0;
            if (file.length() != 0) { //if properties file empty, and hasn't been run yet
                input = new FileInputStream("config.properties");
               // input.
                prop.load(input); // load a properties file
                cores = Integer.parseInt(prop.getProperty("numberCores")); //get number of cores from config file
            } else Server.Configurations.config();
            ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            threadPool.setCorePoolSize(Runtime.getRuntime().availableProcessors() * cores);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    threadPool.submit(new Thread(() -> {
                        handleClient(clientSocket);}));
                } catch (SocketTimeoutException e) {
                    e.getStackTrace();
                    stop();
                }
            }
            serverSocket.close();
            threadPool.shutdown();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            //  LOG.info(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            //   LOG.error("IOException", e);
        }
    }

    public void stop() {
        stop = true;
    }

    public static class Configurations {
        public static void config() {
            OutputStream ops = null;
            InputStream ins = null;
            try {
                ins = Server.class.getClassLoader().getResourceAsStream("resources/config.properties");//load the file if exist
                Properties prop = new Properties();
                if (ins == null) {
                    ops = new FileOutputStream("resources/config.properties");

                    prop.setProperty("MazeGenerator","MyMazeGenerator");//generate algo
                    prop.getProperty("NumOfCores","2");//num of cores
                    prop.setProperty("MazeAlgorithm","BestFirstSearch");

                    prop.store(ops, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (ops != null) {
                    try {
                        ops.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }


    }
}
