package assignment.algo;

import assignment.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {
    @Test
    public void testRandom() {
        Random rnd = new Random(2);
        int n = 1000;
        int[] a = rnd.ints(n, 0, n*10).toArray();
        Metrics m = new Metrics();
        new QuickSort().sort(a, m);
        for (int i = 1; i < n; i++) assertTrue(a[i-1] <= a[i]);
    }
}
