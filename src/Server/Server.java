package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Server class
 * ServerStrategy, Start, HandleClient, Stop.
 */

public class Server {
    private static String pathResources = "./resources/config.properties";
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
    }

    // Create new thread.
    public void start() {
        new Thread(() -> {
            serverStrategy();
            // start is over write of run.
        }).start();
    }

    // General properties of the server.
    private void serverStrategy() {
        try {
            // Connection
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            Properties properties = new Properties();
            InputStream input;
            // load a properties file and get the details
            Configurations.config();
            input = new FileInputStream(pathResources);
            properties.load(input);
            int cores = Integer.parseInt(properties.getProperty("numberCores"));
            //default cores is 2
            if (cores <= 0)
                cores = 2;
            // use Thread Pool
            // pool size =number of available processors in my pc * number of cores
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
                }
            }
            // shutdown the thread pool.
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
}