package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Solve with Breadth First Search Algorithm
 * Extends ASearchingAlgorithm and use Queue as LinkedList
 * Contains additional constructor for expansion
 * Solve method - return solution object
 * use ArrayList(astate) "neighbors"
 */

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected  Queue<AState> pathSteps;

    //Constructor
    public BreadthFirstSearch() {
        super();
        this.name = "BreadthFirstSearch";
        this.numberOfNodes = 0 ;
        this.pathSteps = new LinkedList<>();
    }
    //Constructor with params
    public BreadthFirstSearch(String name, Queue<AState>pathSteps, int numberOfNodes) {
        super();
        this.name = name;
        this.numberOfNodes = 0 ;
        this.pathSteps = pathSteps;
    }
    /**
     *First we use array of that reset to false, if we visit him change to true,
     * every node that we visit number of nods become bigger.
     * every node add the a ligel neighbors to array list
     * Algorithm work until we find the goal state,
     * The algorithm work like queue - FIFO.
     */
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        ArrayList<AState> neighbors;
        Solution solution;
        domain.ResetVisit();
        pathSteps.add(domain.getStartState());
        domain.changeVisitTrue(domain.getStartState());
        numberOfNodes++;
        while (pathSteps.size() > 0) {
            AState temp = pathSteps.poll();
            neighbors = domain.getAllPossibleStates(temp);
            for (int i=0;i<neighbors.size();i++) {
                if (domain.isVisited(neighbors.get(i))==false) {
                    numberOfNodes++;
                    domain.changeVisitTrue(neighbors.get(i));
                    neighbors.get(i).predecessor = temp;
                    pathSteps.add(neighbors.get(i));
                }
                if (neighbors.get(i).equals(domain.getGoalState())) {
                    neighbors.get(i).predecessor = temp;
                    solution = finalSolution(neighbors.get(i));
                    return solution;
                }
            }
        }
        return null;
    }
}