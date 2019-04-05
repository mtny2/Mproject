package algorithms.search;

/**
 * Interface to solve problem with different algorithms.
 */

public interface ISearchingAlgorithm {

    Solution finalSolution(AState state);

    Solution solve(ISearchable domain);

    String getName();

    int getNumberOfNodesEvaluated();
}
