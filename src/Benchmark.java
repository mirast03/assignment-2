import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;

public final class Benchmark {
    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int REPEATS = 5;
    private static volatile long sink;
    private static PrintWriter raw, summary;
    private static final class Sample {
        long ns, accesses, comparisons, movements;
        Sample(long ns, Metrics m) {
            this.ns = ns; accesses = m.accesses; comparisons = m.comparisons; movements = m.movements;
        }
    }
    private static IntSequence sequence(String type, int[] data) {
        IntSequence s = type.equals("DynamicArray") ? new DynamicArray() : new LinkedList();
        for (int v : data) s.add(v);
        s.metrics().reset(); return s;
    }
    private static Sample run(String type, String workload, int[] data, int[] indices, int[] queries, int[] inserted) {
        IntSequence s = sequence(type, data);
        int index = workload.endsWith("Middle") ? data.length / 2 : 0;
        int count = workload.startsWith("Remove") ? Math.min(1000, data.length - index) : 1000;
        long checksum = 0, start = System.nanoTime();
        switch (workload) {
            case "RandomAccess": for (int i : indices) checksum += s.get(i); break;
            case "Search": for (int q : queries) if (s.contains(q)) checksum++; break;
            case "InsertFront": case "InsertMiddle":
                for (int i = 0; i < count; i++) s.add(index, inserted[i]); break;
            case "RemoveFront": case "RemoveMiddle":
                for (int i = 0; i < count; i++) checksum += s.remove(index); break;
            default: throw new IllegalArgumentException(workload);
        }
        long elapsed = System.nanoTime() - start;
        sink = checksum + s.size();
        return new Sample(elapsed, s.metrics());
    }
    private static Sample[] priority(int[] data) {
        MinHeap heap = new MinHeap();
        long start = System.nanoTime();
        for (int value : data) heap.insert(value);
        Sample insert = new Sample(System.nanoTime() - start, heap.metrics());
        if (!heap.isValidHeap()) throw new AssertionError("heap after inserts");
        int[] extracted = new int[data.length]; heap.metrics().reset();
        start = System.nanoTime();
        for (int i = 0; i < data.length; i++) extracted[i] = heap.extractMin();
        Sample extract = new Sample(System.nanoTime() - start, heap.metrics());
        for (int i = 1; i < extracted.length; i++)
            if (extracted[i - 1] > extracted[i]) throw new AssertionError("out of order");
        if (heap.size() != 0) throw new AssertionError("not empty");
        sink = extracted[extracted.length - 1];
        return new Sample[]{insert, extract};
    }
    private static void report(String type, String work, int n, int m, Sample[] samples, String theory) {
        double ns = 0, accesses = 0, comparisons = 0, movements = 0;
        for (int r = 0; r < REPEATS; r++) {
            Sample s = samples[r];
            raw.printf(Locale.ROOT, "%s,%s,%d,%d,%d,%d,%d,%d,%d%n", type, work, n, m, r + 1,
                s.ns, s.accesses, s.comparisons, s.movements);
            ns += s.ns; accesses += s.accesses; comparisons += s.comparisons; movements += s.movements;
        }
        summary.printf(Locale.ROOT, "%s,%s,%d,%d,%.1f,%.1f,%.1f,%.1f,%s%n",
            type, work, n, m, ns / REPEATS, accesses / REPEATS, comparisons / REPEATS, movements / REPEATS, theory);
    }
    public static void main(String[] args) throws Exception {
        Path directory = Path.of(args.length == 0 ? "results/tables" : args[0]);
        Files.createDirectories(directory);
        try (PrintWriter environment = new PrintWriter(directory.resolve("environment.txt").toFile())) {
            environment.println("Run UTC: " + java.time.Instant.now());
            environment.println("Java: " + System.getProperty("java.runtime.version"));
            environment.println("VM: " + System.getProperty("java.vm.name"));
            environment.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
            environment.println("Architecture: " + System.getProperty("os.arch"));
            environment.println("Processors: " + Runtime.getRuntime().availableProcessors());
            environment.println("Seed: 42; repetitions: 5; untimed warm-up rounds per case: 2");
        }
        try (PrintWriter r = new PrintWriter(directory.resolve("raw.csv").toFile());
             PrintWriter s = new PrintWriter(directory.resolve("summary.csv").toFile())) {
            raw = r; summary = s;
            raw.println("structure,workload,n,m,repeat,time_ns,accesses,comparisons,movements");
            summary.println("structure,workload,n,m,mean_time_ns,mean_accesses,mean_comparisons,mean_movements,theory");
            for (int n : SIZES) {
                Random random = new Random(42);
                int[] data = new int[n], indices = new int[10000], queries = new int[1000], inserted = new int[1000];
                for (int i = 0; i < n; i++) data[i] = random.nextInt();
                for (int i = 0; i < indices.length; i++) indices[i] = random.nextInt(n);
                for (int i = 0; i < queries.length; i++) queries[i] = random.nextInt();
                for (int i = 0; i < inserted.length; i++) inserted[i] = random.nextInt();
                for (String work : new String[]{"RandomAccess", "Search", "InsertFront", "RemoveFront", "InsertMiddle", "RemoveMiddle"}) {
                    for (String type : new String[]{"DynamicArray", "LinkedList"}) {
                        for (int warm = 0; warm < 2; warm++) run(type, work, data, indices, queries, inserted);
                        Sample[] samples = new Sample[REPEATS];
                        for (int repeat = 0; repeat < REPEATS; repeat++) samples[repeat] = run(type, work, data, indices, queries, inserted);
                        int index = work.endsWith("Middle") ? n / 2 : 0;
                        int m = work.equals("RandomAccess") ? 10000 : work.startsWith("Remove") ? Math.min(1000, n - index) : 1000;
                        String theory = type.equals("DynamicArray") && work.equals("RandomAccess") ? "Theta(m)" :
                            type.equals("LinkedList") && work.endsWith("Front") ? "Theta(m)" :
                            work.startsWith("Insert") && type.equals("DynamicArray") ? "Theta(m*n+m^2)" : "Theta(m*n)";
                        report(type, work, n, m, samples, theory);
                    }
                }
                for (int warm = 0; warm < 2; warm++) priority(data);
                Sample[] inserts = new Sample[REPEATS], extracts = new Sample[REPEATS];
                for (int repeat = 0; repeat < REPEATS; repeat++) {
                    Sample[] pair = priority(data); inserts[repeat] = pair[0]; extracts[repeat] = pair[1];
                }
                report("MinHeap", "PriorityInsert", n, n, inserts, "O(n*log(n)); expected Theta(n) random keys");
                report("MinHeap", "PriorityExtract", n, n, extracts, "O(n*log(n)); typical Theta(n*log(n))");
                System.out.println("Completed n=" + n);
            }
        }
    }
}
