package algorithms.search;

import java.util.ArrayList;

/**
 * Interface class to use different kind of problems
 */
public interface ISearchable {
    AState getStartState();

    AState getGoalState();

    ArrayList<AState> getAllPossibleStates(AState s);

    boolean isVisited(AState visit);

    void changeVisitTrue(AState visit);

    void ResetVisit();
}