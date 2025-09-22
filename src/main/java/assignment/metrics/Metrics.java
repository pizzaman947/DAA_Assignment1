package assignment.metrics;

public class Metrics {
    public long comparisons = 0;
    public long swaps = 0;
    public long allocations = 0;
    private int currentDepth = 0;
    public int maxDepth = 0;

    public synchronized void enter() {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
    }

    public synchronized void exit() {
        if (currentDepth > 0) currentDepth--;
    }

    public synchronized void reset() {
        comparisons = 0;
        swaps = 0;
        allocations = 0;
        currentDepth = 0;
        maxDepth = 0;
    }

    public synchronized void incrementComparisons() {
        comparisons++;
    }

    public synchronized void incrementSwaps() {
        swaps++;
    }

    public synchronized void incrementAllocations() {
        allocations++;
    }
}
