package assignment.algo;

import assignment.util.Point;
import assignment.metrics.Metrics;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static double closest(Point[] pts, Metrics m) {
        if (pts == null || pts.length < 2) return Double.POSITIVE_INFINITY;
        Point[] byX = Arrays.copyOf(pts, pts.length);
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[pts.length];
        return closestRec(byX, aux, 0, byX.length - 1, m);
    }

    private static double distSq(Point a, Point b) {
        double dx = a.x - b.x, dy = a.y - b.y;
        return dx*dx + dy*dy;
    }

    private static double closestRec(Point[] byX, Point[] aux, int lo, int hi, Metrics m) {
        if (lo >= hi) return Double.POSITIVE_INFINITY;
        int mid = (lo + hi) >>> 1;
        double midx = byX[mid].x;
        double dl = closestRec(byX, aux, lo, mid, m);
        double dr = closestRec(byX, aux, mid+1, hi, m);
        double d = Math.min(dl, dr);
        // merge by Y into aux
        int i = lo, j = mid+1, k = lo;
        while (i <= mid && j <= hi) {
            if (byX[i].y <= byX[j].y) aux[k++] = byX[i++];
            else aux[k++] = byX[j++];
        }
        while (i <= mid) aux[k++] = byX[i++];
        while (j <= hi) aux[k++] = byX[j++];
        System.arraycopy(aux, lo, byX, lo, hi-lo+1);
        // build strip
        int stripSize = 0;
        for (int t = lo; t <= hi; t++) {
            if ((byX[t].x - midx)*(byX[t].x - midx) < d) {
                aux[stripSize++] = byX[t];
            }
        }
        // for each point check next up to 7 neighbors
        for (int p = 0; p < stripSize; p++) {
            for (int q = p+1; q < stripSize && q - p <= 8; q++) {
                double ds = distSq(aux[p], aux[q]);
                if (ds < d) d = ds;
            }
        }
        return d;
    }
}
