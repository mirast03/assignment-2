import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;

public final class Tests {
    private static long checks;
    private static void check(boolean condition) {
        checks++;
        if (!condition) throw new AssertionError("check " + checks);
    }
    private static void throwsType(Class<? extends Throwable> type, Runnable action) {
        try { action.run(); } catch (Throwable e) { check(type.isInstance(e)); return; }
        throw new AssertionError("Expected " + type.getSimpleName());
    }
    private static void same(IntSequence actual, java.util.List<Integer> expected) {
        check(actual.size() == expected.size());
        for (int i = 0; i < expected.size(); i++) check(actual.get(i) == expected.get(i));
    }
    private static void sequence(IntSequence actual, java.util.List<Integer> expected) {
        check(actual.size() == 0 && !actual.contains(1));
        throwsType(IndexOutOfBoundsException.class, () -> actual.get(0));
        throwsType(IndexOutOfBoundsException.class, () -> actual.remove(0));
        throwsType(IndexOutOfBoundsException.class, () -> actual.add(-1, 3));
        throwsType(IndexOutOfBoundsException.class, () -> actual.add(1, 3));
        actual.add(7); expected.add(7); same(actual, expected);
        check(actual.remove(0) == expected.remove(0));
        actual.add(0, 7); expected.add(0, 7);
        actual.add(0, Integer.MIN_VALUE); expected.add(0, Integer.MIN_VALUE);
        actual.add(actual.size(), 7); expected.add(7);
        actual.add(Integer.MAX_VALUE); expected.add(Integer.MAX_VALUE);
        same(actual, expected);
        check(actual.contains(7) && actual.contains(Integer.MIN_VALUE) && !actual.contains(9));
        throwsType(IndexOutOfBoundsException.class, () -> actual.get(-1));
        throwsType(IndexOutOfBoundsException.class, () -> actual.get(actual.size()));
        throwsType(IndexOutOfBoundsException.class, () -> actual.remove(actual.size()));
        throwsType(IndexOutOfBoundsException.class, () -> actual.remove(-1));
        throwsType(IndexOutOfBoundsException.class, () -> actual.add(actual.size() + 1, 0));
        Random random = new Random(42);
        for (int k = 0; k < 5000; k++) {
            int op = random.nextInt(5), value = random.nextInt(101) - 50;
            if (op == 0 || expected.isEmpty()) { actual.add(value); expected.add(value); }
            else if (op == 1) {
                int i = random.nextInt(expected.size() + 1); actual.add(i, value); expected.add(i, value);
            } else if (op == 2) {
                int i = random.nextInt(expected.size()); check(actual.remove(i) == expected.remove(i));
            } else if (op == 3) {
                int i = random.nextInt(expected.size()); check(actual.get(i) == expected.get(i));
            } else check(actual.contains(value) == expected.contains(value));
            same(actual, expected);
        }
        while (!expected.isEmpty()) check(actual.remove(actual.size() - 1) == expected.remove(expected.size() - 1));
        for (int i = 0; i < 100000; i++) actual.add(i);
        check(actual.size() == 100000 && actual.get(99999) == 99999 && actual.contains(99999));
        for (int i = 0; i < 100000; i++) check(actual.remove(0) == i);
        check(actual.size() == 0);
        actual.add(11); check(actual.get(0) == 11); // tail remains usable after becoming empty
    }
    private static void heap() {
        MinHeap actual = new MinHeap(); PriorityQueue<Integer> expected = new PriorityQueue<>();
        throwsType(NoSuchElementException.class, actual::peekMin);
        throwsType(NoSuchElementException.class, actual::extractMin);
        actual.insert(3); check(actual.isValidHeap() && actual.peekMin() == 3);
        check(actual.extractMin() == 3 && actual.isValidHeap());
        Random random = new Random(42);
        for (int i = 0; i < 3000; i++) {
            int value = i == 0 ? Integer.MIN_VALUE : i == 1 ? Integer.MAX_VALUE : random.nextInt(101) - 50;
            actual.insert(value); expected.add(value);
            check(actual.isValidHeap() && actual.peekMin() == expected.peek());
        }
        int previous = Integer.MIN_VALUE;
        while (!expected.isEmpty()) {
            int value = actual.extractMin(); check(value == expected.remove() && value >= previous);
            previous = value; check(actual.isValidHeap());
        }
        for (int i = 0; i < 5000; i++) {
            if (expected.isEmpty() || random.nextBoolean()) {
                int value = random.nextInt(); actual.insert(value); expected.add(value);
            } else check(actual.extractMin() == expected.remove());
            check(actual.size() == expected.size() && actual.isValidHeap());
            if (!expected.isEmpty()) check(actual.peekMin() == expected.peek());
        }
        while (!expected.isEmpty()) check(actual.extractMin() == expected.remove());
        for (int i = 100000; i > 0; i--) { actual.insert(i); expected.add(i); }
        check(actual.isValidHeap());
        while (!expected.isEmpty()) check(actual.extractMin() == expected.remove());
        check(actual.isValidHeap() && actual.size() == 0);
    }
    private static void counters() {
        IntSequence a = new DynamicArray(), b = new LinkedList();
        for (int i = 0; i < 5; i++) { a.add(i); b.add(i); }
        a.metrics().reset(); b.metrics().reset();
        a.get(4); b.get(4); check(a.metrics().accesses == 1 && b.metrics().accesses == 5);
        a.metrics().reset(); b.metrics().reset();
        a.contains(9); b.contains(9); check(a.metrics().comparisons == 5 && b.metrics().comparisons == 5);
        a.metrics().reset(); b.metrics().reset();
        a.add(0, 9); b.add(0, 9); check(a.metrics().movements == 5 && b.metrics().accesses == 0);
    }
    public static void main(String[] args) {
        sequence(new DynamicArray(), new ArrayList<>());
        sequence(new LinkedList(), new java.util.LinkedList<>());
        heap(); counters();
        System.out.println("PASS: " + checks + " checks");
    }
}
