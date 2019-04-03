package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Solve with Depth First Search Algorithm
 * Extends ASearchingAlgorithm and use Stack as LinkedList
 * Solve method - return solution object
 * use ArrayList(astate) "neighbors"
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    //Constructor
    public DepthFirstSearch() {
        super();
        this.name = "DepthFirstSearch";
        this.numberOfNodes = 0;
    }

    /**
     * First we use array of that reset to false, if we visit him change to true,
     * every node that we visit number of nods become bigger.
     * every node add the a ligel neighbors to array list
     * Algorithm work until we find the goal state,
     * The algorithm work like stack - LIFO.
     */

    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        Solution solution;
        ArrayList<AState> MyNeighbours;
        domain.ResetVisit();
        Stack<AState> pathSteps = new Stack<>();
        pathSteps.add(domain.getStartState());
        domain.changeVisitTrue(domain.getStartState());
        numberOfNodes++;
        while (pathSteps.size() != 0) {
            AState temp = pathSteps.pop();
            MyNeighbours = domain.getAllPossibleStates(temp);
            for (int i = 0; i < MyNeighbours.size(); i++) {
                if (!domain.isVisited(MyNeighbours.get(i))) {
                    numberOfNodes++;
                    domain.changeVisitTrue(MyNeighbours.get(i));
                    MyNeighbours.get(i).predecessor = temp;
                    pathSteps.add(MyNeighbours.get(i));
                }
                if (MyNeighbours.get(i).equals(domain.getGoalState())) {
                    MyNeighbours.get(i).predecessor = temp;
                    solution = finalSolution(MyNeighbours.get(i));
                    domain.ResetVisit();
                    return solution;
                }
            }
        }
        return null;
    }
}