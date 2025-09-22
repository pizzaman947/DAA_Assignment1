package assignment.algo;

import assignment.metrics.Metrics;
import assignment.util.ArrayUtils;
import java.util.Arrays;

public class DeterministicSelect {
    // returns k-th smallest (0-based) in array (does not modify caller array)
    public static int select(int[] array, int k, Metrics m) {
        if (array == null || k < 0 || k >= array.length) throw new IllegalArgumentException();
        int[] a = Arrays.copyOf(array, array.length);
        return selectInPlace(a, 0, a.length - 1, k, m);
    }

    private static int selectInPlace(int[] a, int lo, int hi, int k, Metrics m) {
        while (true) {
            if (lo == hi) return a[lo];
            int pivotIndex = medianOfMedians(a, lo, hi, m);
            int pivot = a[pivotIndex];
            pivotIndex = partition(a, lo, hi, pivot, m);
            if (k == pivotIndex) return a[k];
            else if (k < pivotIndex) hi = pivotIndex - 1;
            else lo = pivotIndex + 1;
        }
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        int numMedians = 0;
        for (int i = lo; i <= hi; i += 5) {
            int subHi = Math.min(i + 4, hi);
            insertionSort(a, i, subHi, m);
            int medianIndex = i + (subHi - i) / 2;
            ArrayUtils.swap(a, lo + numMedians, medianIndex, m);
            numMedians++;
        }
        // recurse on medians
        int mid = lo + (numMedians - 1) / 2;
        if (numMedians == 1) return lo;
        return selectInPlace(a, lo, lo + numMedians - 1, mid, m);
    }

    private static int partition(int[] a, int lo, int hi, int pivot, Metrics m) {
        int i = lo;
        for (int j = lo; j <= hi; j++) {
            m.comparisons++;
            if (a[j] < pivot) {
                ArrayUtils.swap(a, i, j, m);
                i++;
            }
        }
        // place pivot at i by finding one occurrence
        for (int j = lo; j <= hi; j++) {
            if (a[j] == pivot) {
                ArrayUtils.swap(a, i, j, m);
                break;
            }
        }
        return i;
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
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
