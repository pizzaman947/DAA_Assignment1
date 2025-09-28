package assignment.algo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import assignment.metrics.Metrics;

class DeterministicSelectTest {

    @Test
    void testSelectFindsMedianInSimpleArray() {
        int[] array = {8, 2, 5, 9, 7, 1, 6};
        int k = 3;
        int expectedValue = 6;
        Metrics testMetrics = new Metrics();

        int actualValue = DeterministicSelect.select(array, k, testMetrics);

        assertEquals(expectedValue, actualValue);
    }

    @Test
    void testSelectFindsMinimum() {
        int[] array = {10, 4, 5, 8, 6, 11, 26};
        int k = 0;
        int expectedValue = 4;
        Metrics testMetrics = new Metrics();

        int actualValue = DeterministicSelect.select(array, k, testMetrics);

        assertEquals(expectedValue, actualValue);
    }
}
