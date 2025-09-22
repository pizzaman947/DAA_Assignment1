package assignment.util;

import java.util.concurrent.ThreadLocalRandom;
import assignment.metrics.Metrics;

public final class ArrayUtils {
    private ArrayUtils() {}

    public static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        if (m != null) m.swaps++;
    }

    public static void shuffle(int[] a) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = a.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
    }
}
