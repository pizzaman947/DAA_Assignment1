package assignment.algo;

import assignment.metrics.Metrics;

import java.util.Arrays;

public class DeterministicSelectTest {

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) throw new IllegalArgumentException("Array is empty");
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of range");
        return select(a, 0, a.length - 1, k, m);
    }

    private static int select(int[] a, int left, int right, int k, Metrics m) {
        if (left == right) {
            return a[left];
        }

        int pivotIndex = medianOfMedians(a, left, right);
        pivotIndex = partition(a, left, right, pivotIndex);

        int rank = pivotIndex - left;

        if (m != null) m.incrementComparisons();

        if (k == rank) {
            return a[pivotIndex];
        } else if (k < rank) {
            return select(a, left, pivotIndex - 1, k, m);
        } else {
            return select(a, pivotIndex + 1, right, k - rank - 1, m);
        }
    }

    private static int partition(int[] a, int left, int right, int pivotIndex) {
        int pivotValue = a[pivotIndex];
        swap(a, pivotIndex, right);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (a[i] < pivotValue) {
                swap(a, storeIndex, i);
                storeIndex++;
            }
        }
        swap(a, storeIndex, right);
        return storeIndex;
    }

    private static int medianOfMedians(int[] a, int left, int right) {
        int n = right - left + 1;
        if (n < 5) {
            Arrays.sort(a, left, right + 1);
            return left + n / 2;
        }

        int numMedians = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            Arrays.sort(a, i, subRight + 1);
            int medianIndex = i + (subRight - i) / 2;
            swap(a, left + numMedians, medianIndex);
            numMedians++;
        }

        return medianOfMedians(a, left, left + numMedians - 1);
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
