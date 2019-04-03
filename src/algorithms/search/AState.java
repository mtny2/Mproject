package algorithms.search;

/**
 * AState - abstract class to describe a state in current problem
 *
 * @param AState predecessor - the edge that come from.
 * @param double cost - cost of diagonals cost 1.5 and regular move 1.
 * containing 3 methods getCost,setCost, toString.
 */

public abstract class AState {
    AState predecessor;
    private double cost;

    //Constructor
    AState() {
        predecessor = null;
        this.cost = 0;
    }

    public double getCost() {
        return this.cost;
    }
    public void setCost(double costI) {
        this.cost = costI;
    }
    public abstract String toString();
}
