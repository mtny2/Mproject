package algorithms.search;

import java.util.Stack;

/**
 * ASearchingAlgorithm implements ISearchingAlgorithm
 * containing 3 method getName,getNumberOfNodesEvaluated,finalSolution
 * finalSolution - return the path to solution from start position to goal position
 * @param name - name of algorithm
 * @numberOfNodes - number of nodes that algorithm visit
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    String name;
    int numberOfNodes = 0;

    // get name of algorithm.
    public String getName() {
        return name;
    }

    //get number of nodes we needed to solve problem
    public int getNumberOfNodesEvaluated() {
        return numberOfNodes;
    }

    //to change the order in the stack from the first step to the end
    public Solution finalSolution(AState state) {
        Stack<AState> finalPath = new Stack<>();
        Solution solution = new Solution();
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