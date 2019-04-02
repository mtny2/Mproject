package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    public BreadthFirstSearch() {
        super();
        this.name = "BreadthFirstSearch";
        this.numberOfNodes = 0;
    }
    /**
     * Solve with BFS algorithm
     *
     */
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        ArrayList<AState> neighbors;
        Solution solution;
        domain.ResetVisit();
        Queue<AState> pathSteps = new LinkedList<>();
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