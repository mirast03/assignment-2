public final class DynamicArray implements IntSequence {
    private int[] data = new int[16];
    private int size;
    private final Metrics metrics = new Metrics();
    public int size() { return size; }
    public Metrics metrics() { return metrics; }
    private void check(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("index=" + index);
    }
    private void grow() {
        if (size < data.length) return;
        int[] next = new int[data.length * 2];
        for (int i = 0; i < size; i++) {
            next[i] = data[i]; metrics.movements++;
        }
        data = next;
    }
    public void add(int value) { add(size, value); }
    public void add(int index, int value) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("index=" + index);
        grow();
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1]; metrics.movements++;
        }
        data[index] = value; size++;
    }
    public int remove(int index) {
        check(index);
        int value = data[index]; metrics.accesses++;
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1]; metrics.movements++;
        }
        size--; data[size] = 0;
        return value;
    }
    public int get(int index) { check(index); metrics.accesses++; return data[index]; }
    public boolean contains(int value) {
        for (int i = 0; i < size; i++) {
            metrics.comparisons++;
            if (data[i] == value) return true;
        }
        return false;
    }
}
