/** Singly linked list with a tail pointer for constant-time append. */
public final class LinkedList implements IntSequence {
    private static final class Node {
        final int value;
        Node next;
        Node(int value) { this.value = value; }
    }
    private Node head, tail;
    private int size;
    private final Metrics metrics = new Metrics();
    public int size() { return size; }
    public Metrics metrics() { return metrics; }
    private void check(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("index=" + index);
    }
    private Node node(int index) {
        Node current = head; metrics.accesses++;
        for (int i = 0; i < index; i++) { current = current.next; metrics.accesses++; }
        return current;
    }
    public void add(int value) {
        Node next = new Node(value);
        if (tail == null) head = next;
        else tail.next = next;
        tail = next; size++;
    }
    public void add(int index, int value) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("index=" + index);
        if (index == size) { add(value); return; }
        Node next = new Node(value);
        if (index == 0) { next.next = head; head = next; }
        else { Node previous = node(index - 1); next.next = previous.next; previous.next = next; }
        size++;
    }
    public int remove(int index) {
        check(index);
        Node removed;
        if (index == 0) { removed = head; head = head.next; metrics.accesses++; }
        else {
            Node previous = node(index - 1);
            removed = previous.next; metrics.accesses++;
            previous.next = removed.next;
            if (removed == tail) tail = previous;
        }
        if (--size == 0) tail = null;
        return removed.value;
    }
    public int get(int index) { check(index); return node(index).value; }
    public boolean contains(int value) {
        for (Node current = head; current != null; current = current.next) {
            metrics.comparisons++;
            if (current.value == value) return true;
        }
        return false;
    }
}
