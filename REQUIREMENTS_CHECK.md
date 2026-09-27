# Assignment 2 requirements check

Checked against the uploaded `Assignment 2.pdf` on 26 September 2026.

| PDF requirement | Evidence | Status |
|---|---|---|
| Dynamic Array: add, indexed add, remove, get, contains | `src/DynamicArray.java`, `src/IntSequence.java` | Implemented |
| Linked List: the same five operations | `src/LinkedList.java` | Implemented |
| Min-Heap: insert, peekMin, extractMin | `src/MinHeap.java` | Implemented |
| No standard collections inside the required structures | Structures use an int array or custom nodes; standard collections are test references | Checked |
| Two complete loop-invariant proofs | README Correctness: indexed array insertion and heap extraction | Included |
| Best, average, worst and auxiliary space; O, Omega, Theta | README Complexity Analysis, with distribution and amortization assumptions | Included |
| n = 100, 1,000, 10,000, 100,000 | `Benchmark.SIZES`; CSV n columns | All four sizes |
| Five runs, average time, nanoTime, seed 42 | `src/Benchmark.java`; 280 raw samples and 56 mean rows | Executed |
| Input generation and printing outside timing | Pre-generated arrays; output after each measured case | Checked |
| Random access: 10,000 indices and access count | RandomAccess rows for both sequences | Executed |
| Search: 1,000 queries and comparison count | Search rows for both sequences | Executed |
| Front and fixed-middle insertion: 1,000 calls | InsertFront and InsertMiddle rows | Executed |
| Front and fixed-middle removal from restored input | Fresh sequence for each run; RemoveFront and RemoveMiddle rows | Small-input adjustment below |
| Heap insertion and extraction with comparison counts | PriorityInsert and PriorityExtract rows | Executed |
| Non-decreasing heap output | Benchmark validation; Tests reference PriorityQueue | Passed |
| Tables with n, time, metrics, theoretical complexity | README Results; `results/tables/results.md`; CSV files | Included |
| At least two plots | `results/plots/execution-time.png`, `logical-work.png` | Included |
| All nine discussion questions | README Discussion sections 1 through 9 | Answered |
| Empty, one, multiple, duplicates, boundaries, large and invalid inputs | `src/Tests.java`; `results/tables/tests.txt` | 5,327,957 checks passed |
| Heap property after both mutation types | Tests validate after each small-case insertion/extraction; large-case validation and reference extraction | Passed |
| All eight report sections | README | Included |
| Successful Java compilation | JDK 25, `javac --release 17 -Xlint:all` | Passed without warnings |
| GitHub link and meaningful Git history | User will create commits and publish; `COMMIT_PLAN.md` gives future review stages | User action remains |

## Removal adjustment

The PDF asks for 1,000 successful removals after restoring the original n elements. With n=100, only 100 front removals exist. With a fixed original index n/2, only n-n/2 removals exist, so n=100 and n=1,000 cannot support the requested count.

The benchmark uses `m = min(1000, n-index)` and records m in every raw and summary row. This preserves the original input, fixed index and successful-operation semantics. It is an explicit deviation from the literal count for impossible cases. The instructor should confirm this interpretation before submission. All other mutation cases perform exactly 1,000 calls.

## Measurement check

The latest run contains 280 measured samples and 56 summary rows. Every case has repeats 1 through 5, and each summary metric matches the arithmetic mean of its raw samples. Search performs exactly m*n comparisons for this seed because every query misses. Array random access performs exactly 10,000 accesses at every input size. The README tables and both plots were regenerated from these measured values.

No Git commits, push, repository creation, or fabricated history were performed. The GitHub submission requirement remains with the user.
