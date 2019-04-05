package algorithms.search;

import java.util.Stack;

/**
 * ASearchingAlgorithm implements ISearchingAlgorithm
 * containing 3 method getName,getNumberOfNodesEvaluated,finalSolution
 * finalSolution - return the path to solution from start position to goal position
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    String name;
    int numberOfNodes = 0;

    public String getName()
    {
        return name;
    }

    /**
     * get number of nodes we needed to solve problem
     */
    public int getNumberOfNodesEvaluated()
    {
        return numberOfNodes;
    }

    /**
     * to change the order in the stack from the first step to the end
     */
    public Solution finalSolution(AState state) {
        Solution solution = new Solution();
        Stack<AState> finalPath = new Stack<>();
        if (state != null) {
            while (state.predecessor != null) {
                finalPath.push(state);
                state = state.predecessor;
            }
            finalPath.push(state);
        }
        while (finalPath.size() != 0) {
            solution.addState(finalPath.pop());
        }
        return solution;
    }
}