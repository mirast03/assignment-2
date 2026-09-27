/** Logical work counters, independent of elapsed time. */
public final class Metrics {
    public long accesses, comparisons, movements;
    public void reset() { accesses = comparisons = movements = 0; }
}
