import java.util.NoSuchElementException;

public final class MinHeap {
    private int[] data = new int[16];
    private int size;
    private final Metrics metrics = new Metrics();
    public int size() { return size; }
    public Metrics metrics() { return metrics; }
    private boolean less(int a, int b) { metrics.comparisons++; return a < b; }
    private void swap(int a, int b) { int t = data[a]; data[a] = data[b]; data[b] = t; }
    public void insert(int value) {
        if (size == data.length) {
            int[] next = new int[data.length * 2];
            for (int j = 0; j < size; j++) next[j] = data[j];
            data = next;
        }
        int i = size++;
        data[i] = value;
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (!less(data[i], data[parent])) break;
            swap(i, parent); i = parent;
        }
    }
    public int peekMin() {
        if (size == 0) throw new NoSuchElementException("empty heap");
        return data[0];
    }
    public int extractMin() {
        int result = peekMin();
        data[0] = data[--size];
        int i = 0;
        while (i < size / 2) {
            int left = 2 * i + 1, right = left + 1, smaller = left;
            if (right < size && less(data[right], data[left])) smaller = right;
            if (!less(data[smaller], data[i])) break;
            swap(i, smaller); i = smaller;
        }
        return result;
    }
    /** Validation deliberately does not change benchmark counters. */
    public boolean isValidHeap() {
        for (int i = 1; i < size; i++) if (data[(i - 1) / 2] > data[i]) return false;
        return true;
    }
}
