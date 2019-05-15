package Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Configurations class
 *Contains the settings that the user wants to work with them.
 * For example  which algorithm being use in search .
 */


public class Configurations {

    private static String pathResources = "./resources/config.properties";


    public static void config() {
            OutputStream ops = null;
            InputStream ins;
            try {
                //load the file if exist in resources.
                //if not exist, create the file.
                ins = Server.class.getClassLoader().getResourceAsStream(pathResources);
                if (ins == null) {
                    Properties prop = new Properties();
                    ops = new FileOutputStream(pathResources);
                    prop.setProperty("MazeGenerator", "MyMazeGenerator");//generate algorithm
                    prop.setProperty("numberCores", "2");//number of cores
                    prop.setProperty("MazeAlgorithmSearch", "BreadthFirstSearch"); // search algorithm
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
