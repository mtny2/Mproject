package algorithms.search;
import java.util.Comparator;
import java.util.PriorityQueue;

    public class BestFirstSearch extends BreadthFirstSearch {
        /**
         * Solve with Best First Search Algorithm
         * Extends BreadthFirstSearch and use Priority Queue
         * PriorityQueue compare cost of move -  cost of diagonals cost 1.5 and regular move 1.
         */

        //Constructor
        public BestFirstSearch() {
            super("BestFirstSearch", new PriorityQueue<>(Comparator.comparingDouble(AState::getCost)));
        }
}