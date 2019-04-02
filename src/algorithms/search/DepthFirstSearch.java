package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {
    /**
     * Solve with Depth First Search Algorithm
     */

    public DepthFirstSearch() {
        super(); 
        this.name = "DepthFirstSearch";
        this.numberOfNodes = 0;
    }
   
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