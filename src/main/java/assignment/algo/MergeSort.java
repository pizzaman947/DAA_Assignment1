package assignment.algo;

import assignment.metrics.Metrics;

public class MergeSort implements Sort {
    private static final int CUTOFF = 16;

    @Override
    public void sort(int[] array, Metrics m) {
        if (array == null || array.length <= 1) return;
        int[] buffer = new int[array.length];
        m.allocations++; // one buffer
        mergeSort(array, 0, array.length - 1, buffer, m);
    }

    private void mergeSort(int[] a, int lo, int hi, int[] buf, Metrics m) {
        m.enter();
        try {
            if (hi - lo + 1 <= CUTOFF) {
                insertionSort(a, lo, hi, m);
                return;
            }
            int mid = (lo + hi) >>> 1;
            mergeSort(a, lo, mid, buf, m);
            mergeSort(a, mid + 1, hi, buf, m);
            merge(a, lo, mid, hi, buf, m);
        } finally {
            m.exit();
        }
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

    private void merge(int[] a, int lo, int mid, int hi, int[] buf, Metrics m) {
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            m.comparisons++;
            if (a[i] <= a[j]) buf[k++] = a[i++];
            else buf[k++] = a[j++];
        }
        while (i <= mid) buf[k++] = a[i++];
        while (j <= hi) buf[k++] = a[j++];
        System.arraycopy(buf, lo, a, lo, hi - lo + 1);
    }
}
