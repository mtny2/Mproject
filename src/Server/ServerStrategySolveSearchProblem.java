package Server;

import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.ISearchable;
import algorithms.search.Solution;

import java.io.*;
/**
 * server strategy SolveSearchProblem class implements IServerStrategy
 * Get a problem from the client and use searchAlgorithm to solve him
 */
public class ServerStrategySolveSearchProblem implements IServerStrategy {
    private ASearchingAlgorithm ASearchsalgorithm;

    @Override
    public void serverStrategy(InputStream inputStream, OutputStream outputStream) {
        ISearchable problem = getProblemFromClient(inputStream);
        ASearchingAlgorithm searchAlgorithm = new BreadthFirstSearch();
        Solution solution = searchAlgorithm.solve(problem);
        setSolution(outputStream, solution);
    }
    // Get problem from the client.
    private ISearchable getProblemFromClient(InputStream inputStream) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inputStream);
            ISearchable problem = (ISearchable) fromClient.readObject();
            fromClient.close();
            return problem;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    //Set solution to problem
    private void setSolution(OutputStream outputStream,Solution solution){
        try {
            ObjectOutputStream toClient = new ObjectOutputStream(outputStream);
            toClient.writeObject(solution);
            toClient.flush();
            toClient.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}

