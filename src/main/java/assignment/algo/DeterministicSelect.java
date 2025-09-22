package assignment.algo;

import assignment.metrics.Metrics;
import assignment.util.ArrayUtils;
import java.util.Arrays;

public class DeterministicSelect {

    public static int select(int[] array, int k, Metrics m) {
        if (array == null || k < 0 || k >= array.length) {
            throw new IllegalArgumentException();
        }
        int[] a = Arrays.copyOf(array, array.length);
        return selectInPlace(a, 0, a.length - 1, k, m);
    }

    private static int selectInPlace(int[] a, int lo, int hi, int k, Metrics m) {
        while (lo < hi) {
            int pivotValue = medianOfMedians(a, lo, hi, m);
            int pivotFinalIndex = partition(a, lo, hi, pivotValue, m);

            if (k == pivotFinalIndex) {
                return a[k];
            } else if (k < pivotFinalIndex) {
                hi = pivotFinalIndex - 1;
            } else {
                lo = pivotFinalIndex + 1;
            }
        }
        return a[lo];
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertionSort(a, lo, hi, m);
            return a[lo + n / 2];
        }

        int numMedians = 0;
        for (int i = lo; i <= hi; i += 5) {
            int subHi = Math.min(i + 4, hi);
            insertionSort(a, i, subHi, m);
            int medianIndex = i + (subHi - i) / 2;
            ArrayUtils.swap(a, lo + numMedians, medianIndex, m);
            numMedians++;
        }

        return selectInPlace(a, lo, lo + numMedians - 1, lo + (numMedians - 1) / 2, m);
    }

    private static int partition(int[] a, int lo, int hi, int pivot, Metrics m) {
        int pivotIndex = -1;
        for (int i = lo; i <= hi; i++) {
            if (a[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }
        ArrayUtils.swap(a, pivotIndex, hi, m);

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

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.comparisons++;
                if (a[j] <= key) {
                    break;
                }
                a[j + 1] = a[j];
                j--;
                m.swaps++;
            }
            a[j + 1] = key;
        }
    }
}