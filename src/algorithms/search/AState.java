package algorithms.search;

/**
 * abstract class to describe a state in current problem
 */

public abstract class AState {
    private static int counter = 0;
    AState cameFrom;
    private int id;
    private double cost;

    AState() {
        this.id = counter;
        counter++;
        cameFrom = null;
        this.cost = 0;
    }

    public double getCost() {

        return this.cost;
    }

    public void setCost(double costI) {

        this.cost = costI;
    }

    public abstract String toString();

    public int hashCode() {

        return id;
    }
}