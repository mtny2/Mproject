package algorithms.search;

import java.util.ArrayList;

/**
 * Solution class
 * returns array with all solution steps
 *
 * @param solution - array list of astate,
 */
public class Solution {
    private ArrayList<AState> solution;

    //Constructor
    public Solution() {
        solution = new ArrayList<AState>();
    }


    public void addState(AState newState) {
        if (newState != null)
            solution.add(newState);
    }

    public ArrayList<AState> getSolutionPath() {
        return solution;
    }
}