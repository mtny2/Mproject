package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Server {
    private static String pathResources = "resources/config.properties";
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;


    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }


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
            InputStream input;
            File file = new File(pathResources);
            int cores = 0;
            if (file.length() != 0) { //if properties file empty, and hasn't been run yet
                input = new FileInputStream(pathResources);

                prop.load(input); // load a properties file
                cores = Integer.parseInt(prop.getProperty("numberCores")); //get number of cores from config file
            } else Server.Configurations.config();
            if (cores <= 0)
                cores = 2;//default
            int threadPoolSize = Runtime.getRuntime().availableProcessors() * cores;
            ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            threadPool.setCorePoolSize(threadPoolSize);


            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept(); // blocking call
                    threadPool.submit(new Thread(() -> {
                        handleClient(clientSocket);
                    }));
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
            System.out.println(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void stop() {
        stop = true;
    }

    public static class Configurations {
        public static void config() {
            OutputStream ops = null;
            InputStream ins;
            try {
                ins = Server.class.getClassLoader().getResourceAsStream(pathResources);//load the file if exist
                Properties prop = new Properties();
                if (ins == null) {
                    ops = new FileOutputStream(pathResources);
                    prop.setProperty("MazeGenerator", "EmptyMazeGenerator");//generate algo
                    prop.setProperty("numberCores", "2");//num of cores
                    prop.setProperty("MazeAlgorithmSearch", "BreadthFirstSearch");
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
