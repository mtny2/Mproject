package algorithms.search;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class BestFirstSearch extends ASearchingAlgorithm {

    /**
     * Solve with Best First Search Algorithm
     *
     */
    public BestFirstSearch() {
        super();
        this.name = "BestFirstSearch";
        this.numberOfNodes = 0;
    }


    public Solution solve(ISearchable domain) {
        if (domain == null)
            return null;
        domain.ResetVisit();
        PriorityQueue<AState> pathSteps = new PriorityQueue<>(this::compareStates); //create a priorityQueue consider the cost of the states
        pathSteps.add(domain.getStartState());
        domain.changeVisitTrue(domain.getStartState());
        Solution solution;
        ArrayList<AState> MyNeighbours;
             while (pathSteps.size() != 0) {
                 AState temp = pathSteps.poll();
               // if (domain.getGoalState().equals(temp)) {
               //     domain.setGoalState(temp);
               //     solution = finalSolution(domain.getGoalState());
               //     return solution;
               // }
                 MyNeighbours = domain.getAllPossibleStates(temp);
                 for (int i = 0; i < MyNeighbours.size(); i++) {
                     if (!domain.isVisited(MyNeighbours.get(i))) {
                         MyNeighbours.get(i).predecessor = temp;
                         numberOfNodes++;
                         if (MyNeighbours.get(i).equals(domain.getGoalState())) {
                             domain.setGoalState(MyNeighbours.get(i));
                             solution = finalSolution(domain.getGoalState());
                             return solution;
                         }
                         domain.changeVisitTrue(MyNeighbours.get(i));
                         pathSteps.add(MyNeighbours.get(i));
                     }
                 }
             }
             return null;
         }


    /**
     * Compare between to aState according to cost
     *
     */
    private int compareStates(AState o1, AState o2) {
        if (o1.getCost() > o2.getCost())
            return 1;
        if (o2.getCost() > o1.getCost())
            return -1;
        else
            return 0;
    }
}