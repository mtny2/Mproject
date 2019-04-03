package algorithms.search;

/**
 * Interface class to solve problem with different algorithms.
 */
public interface ISearchingAlgorithm {
    Solution finalSolution(AState state);

    Solution solve(ISearchable domain);

    String getName();

    int getNumberOfNodesEvaluated();
}
