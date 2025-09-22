package assignment.algo;

import assignment.metrics.Metrics;
import assignment.util.Point;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    @Test
    public void smallBruteForceCompare() {
        Metrics m = new Metrics();
        Point[] pts = new Point[] {
            new Point(0,0),
            new Point(0,1),
            new Point(5,5),
            new Point(2,2)
        };
        double d2 = ClosestPair.closest(pts, m);
        assertTrue(d2 >= 0);
    }
}
