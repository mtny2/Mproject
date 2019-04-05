package algorithms.search;

import java.util.ArrayList;

/**
 * solution class
 * returns array with all solution steps
 */

public class Solution {
    private ArrayList<AState> solution;


    public Solution() {
        solution = new ArrayList<>();
    }


    public void addState(AState newState) {
        if (newState != null)
            solution.add(newState);
    }

    public ArrayList<AState> getSolutionPath() {
        return solution;
    }
}