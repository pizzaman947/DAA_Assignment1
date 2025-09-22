package assignment.algo;

import assignment.metrics.Metrics;
import assignment.util.ArrayUtils;
import java.util.concurrent.ThreadLocalRandom;

public class QuickSort implements Sort {
    private static final int CUTOFF = 16;

    @Override
    public void sort(int[] array, Metrics m) {
        if (array == null || array.length <= 1) return;
        ArrayUtils.shuffle(array); // avoid adversarial
        quicksort(array, 0, array.length - 1, m);
    }

    private void quicksort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            if (hi - lo + 1 <= CUTOFF) {
                insertionSort(a, lo, hi, m);
                return;
            }
            m.enter();
            try {
                int pivotIndex = ThreadLocalRandom.current().nextInt(lo, hi + 1);
                int pivot = a[pivotIndex];
                ArrayUtils.swap(a, pivotIndex, hi, m);
                int p = partition(a, lo, hi, pivot, m);
                // recurse on smaller side
                int leftSize = p - lo;
                int rightSize = hi - p;
                if (leftSize < rightSize) {
                    quicksort(a, lo, p - 1, m);
                    lo = p + 1; // tail recurse on right
                } else {
                    quicksort(a, p + 1, hi, m);
                    hi = p - 1;
                }
            } finally {
                m.exit();
            }
        }
    }

    private int partition(int[] a, int lo, int hi, int pivot, Metrics m) {
        int i = lo;
        for (int j = lo; j < hi; j++) {
            m.comparisons++;
            if (a[j] < pivot) {
                ArrayUtils.swap(a, i, j, m);
                i++;
            }
        }
        ArrayUtils.swap(a, i, hi, m);
        return i;
    }

    private void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.comparisons++;
                if (a[j] <= key) break;
                a[j + 1] = a[j];
                j--;
                m.swaps++;
            }
            a[j + 1] = key;
        }
    }
}
