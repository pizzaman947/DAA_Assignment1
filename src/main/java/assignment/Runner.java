package assignment;

import assignment.metrics.Metrics;
import assignment.algo.*;
import assignment.util.Point;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Arrays;

public class Runner {
    public static void main(String[] args) throws Exception {
        String algo = "closest";
        int n = 10000;
        int runs = 3;
        String outFilePath = "target/results.csv";
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--algo":
                    if (i + 1 < args.length) {
                        algo = args[i + 1];
                        i++;
                    }
                    break;
                case "--n":
                    if (i + 1 < args.length) {
                        n = Integer.parseInt(args[i + 1]);
                        i++;
                    }
                    break;
                case "--runs":
                    if (i + 1 < args.length) {
                        runs = Integer.parseInt(args[i + 1]);
                        i++;
                    }
                    break;
                case "--out":
                    if (i + 1 < args.length) {
                        outFilePath = args[i + 1];
                        i++;
                    }
                    break;
            }
        }
        File outFile = new File(outFilePath);
        boolean writeHeader = !outFile.exists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(outFile, true))) {
            if (writeHeader) {
                pw.println("algo,n,run,time_ns,time_ms,maxDepth,comparisons,swaps,allocations");
            }


            for (int run = 1; run <= runs; run++) {
                Metrics m = new Metrics();
                long start = 0, end = 0;

                if ("mergesort".equalsIgnoreCase(algo)) {
                    int[] a = randomArray(n);
                    start = System.nanoTime();
                    new MergeSort().sort(a, m);
                    end = System.nanoTime();
                    validateSorted(a);

                } else if ("quicksort".equalsIgnoreCase(algo)) {
                    int[] a = randomArray(n);
                    start = System.nanoTime();
                    new QuickSort().sort(a, m);
                    end = System.nanoTime();
                    validateSorted(a);

                } else if ("select".equalsIgnoreCase(algo)) {
                    int[] a = randomArray(n);
                    int k = n / 2;
                    start = System.nanoTime();
                    int val = DeterministicSelect.select(a, k, m);
                    end = System.nanoTime();
                    int[] b = Arrays.copyOf(a, a.length);
                    Arrays.sort(b);
                    if (val != b[k]) throw new RuntimeException("Select mismatch");

                } else if ("closest".equalsIgnoreCase(algo)) {
                    Point[] pts = randomPoints(n);
                    start = System.nanoTime();
                    double d2 = ClosestPair.closest(pts, m);
                    end = System.nanoTime();

                } else {
                    System.out.println("Unknown algo: " + algo);
                    return;
                }

                long durationNs = end - start;
                double durationMs = durationNs / 1_000_000.0;
                String line = String.format(java.util.Locale.US, "%s,%d,%d,%d,%.6f,%d,%d,%d,%d",
                        algo, n, run, durationNs, durationMs, m.maxDepth, m.comparisons, m.swaps, m.allocations);



                System.out.println(line);

                pw.println(line);
            }
        }
    }

    private static int[] randomArray(int n) {
        Random rnd = new Random(12345);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(n * 10);
        return a;
    }

    private static Point[] randomPoints(int n) {
        Random rnd = new Random(12345);
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++) pts[i] = new Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
        return pts;
    }

    private static void validateSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) throw new RuntimeException("Not sorted");
        }
    }
}
