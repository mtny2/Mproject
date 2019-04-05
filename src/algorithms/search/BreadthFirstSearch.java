package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Solve with Breadth First Search Algorithm
 * Extends ASearchingAlgorithm and use Queue as LinkedList
 * Contains additional constructor for Best First Search algorithm
 * Solve method - return solution object
 *
 */

public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected Queue<AState> pathSteps;


    public BreadthFirstSearch() {
        super();
        pathSteps= new LinkedList<>();
        this.name = "BreadthFirstSearch";
        this.numberOfNodes = 0;
    }

    public BreadthFirstSearch(String name, PriorityQueue<AState> pathSteps) {
        super();
        this.name = name;
        this.numberOfNodes = 0 ;
        this.pathSteps = pathSteps;
    }
    /**
     *
     * First we use array of that reset to false, if we visit him change to true,
     * every node that we visit number of nods become bigger
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
            if (domain.getGoalState().equals(temp)) {
                domain.setGoalState(temp);
                solution = finalSolution(temp);
                return solution;
            }
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