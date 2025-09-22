# Assignment 1 — Divide & Conquer Algorithms

## Overview
This project contains implementations and measurement scaffolding for four classic divide-and-conquer algorithms:

1. **MergeSort** — reusable buffer, insertion sort cutoff for small arrays.
2. **QuickSort** — randomized pivot, recursion on the smaller partition to limit stack depth.
3. **Deterministic Select** — Median-of-Medians algorithm, groups of 5, linear-time selection.
4. **Closest Pair** — 2D divide-and-conquer algorithm, O(n log n) expected time.

The project also includes:
- Metrics collection (`Metrics`): counts comparisons, swaps, allocations, and recursion depth.
- `Runner` class for automated experiments with CSV output.
- Unit tests with JUnit5 for correctness validation.

---

## Architecture Notes
- **Metrics Tracking**:
    - `Metrics.enter()` / `exit()` manage current recursion depth and maxDepth.
    - `comparisons++` increments for element comparisons.
    - `swaps++` counts element swaps.
    - `allocations++` counts array/object allocations (e.g., temporary buffers in MergeSort).

- **MergeSort**:
    - Uses a reusable buffer for merging.
    - Small-array cutoff switches to InsertionSort to improve constant factors.

- **QuickSort**:
    - Randomized pivot prevents worst-case input performance.
    - Recursion only on the smaller partition limits stack depth to ~O(log n).

- **Deterministic Select**:
    - Median-of-Medians guarantees O(n) worst-case.
    - Recursion only on the partition that contains the k-th element.

- **Closest Pair**:
    - Sorts points by X-coordinate, recursively splits, and checks points in a “strip” sorted by Y-coordinate.
    - Only checks up to 7–8 neighbors in the strip for efficiency.

---

## Recurrence Analysis

| Algorithm           | Method                   | Θ(n) Result |
|--------------------|--------------------------|------------|
| MergeSort           | Master Theorem Case 2    | Θ(n log n) |
| QuickSort           | Akra-Bazzi intuition     | Θ(n log n) average; O(n²) worst-case without random pivot |
| Deterministic Select| Recursion on smaller side| Θ(n)       |
| Closest Pair        | Divide-and-conquer       | Θ(n log n) |

---

## Experiments & Metrics

### Running the Runner
Example command:

```bash
java -jar target/assignment1-1.0-SNAPSHOT-shaded.jar --algo mergesort --n 100000 --runs 3 --out target/results.csv
