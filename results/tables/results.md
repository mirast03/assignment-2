# Measured results

Times are arithmetic means of five measured runs. Raw times are in raw.csv.

## Random access (10,000 gets)

| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |
|---|---:|---:|---:|---:|---:|---:|---|
| DynamicArray | 100 | 10000 | 0.129150 | 10,000 | 0 | 0 | Theta(m) |
| LinkedList | 100 | 10000 | 0.344341 | 511,327 | 0 | 0 | Theta(m*n) |
| DynamicArray | 1,000 | 10000 | 0.032475 | 10,000 | 0 | 0 | Theta(m) |
| LinkedList | 1,000 | 10000 | 2.622058 | 5,021,262 | 0 | 0 | Theta(m*n) |
| DynamicArray | 10,000 | 10000 | 0.020884 | 10,000 | 0 | 0 | Theta(m) |
| LinkedList | 10,000 | 10000 | 45.599117 | 50,180,278 | 0 | 0 | Theta(m*n) |
| DynamicArray | 100,000 | 10000 | 0.010283 | 10,000 | 0 | 0 | Theta(m) |
| LinkedList | 100,000 | 10000 | 584.633117 | 505,028,648 | 0 | 0 | Theta(m*n) |

## Search (1,000 queries)

| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |
|---|---:|---:|---:|---:|---:|---:|---|
| DynamicArray | 100 | 1000 | 0.173183 | 0 | 100,000 | 0 | Theta(m*n) |
| LinkedList | 100 | 1000 | 0.178942 | 0 | 100,000 | 0 | Theta(m*n) |
| DynamicArray | 1,000 | 1000 | 0.135867 | 0 | 1,000,000 | 0 | Theta(m*n) |
| LinkedList | 1,000 | 1000 | 1.432608 | 0 | 1,000,000 | 0 | Theta(m*n) |
| DynamicArray | 10,000 | 1000 | 1.303025 | 0 | 10,000,000 | 0 | Theta(m*n) |
| LinkedList | 10,000 | 1000 | 13.918983 | 0 | 10,000,000 | 0 | Theta(m*n) |
| DynamicArray | 100,000 | 1000 | 14.197917 | 0 | 100,000,000 | 0 | Theta(m*n) |
| LinkedList | 100,000 | 1000 | 142.754059 | 0 | 100,000,000 | 0 | Theta(m*n) |

## Insert at index 0

| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |
|---|---:|---:|---:|---:|---:|---:|---|
| DynamicArray | 100 | 1000 | 0.928808 | 0 | 0 | 601,420 | Theta(m*n+m^2) |
| LinkedList | 100 | 1000 | 0.013333 | 0 | 0 | 0 | Theta(m) |
| DynamicArray | 1,000 | 1000 | 0.098666 | 0 | 0 | 1,500,524 | Theta(m*n+m^2) |
| LinkedList | 1,000 | 1000 | 0.008600 | 0 | 0 | 0 | Theta(m) |
| DynamicArray | 10,000 | 1000 | 0.811866 | 0 | 0 | 10,499,500 | Theta(m*n+m^2) |
| LinkedList | 10,000 | 1000 | 0.014225 | 0 | 0 | 0 | Theta(m) |
| DynamicArray | 100,000 | 1000 | 5.825825 | 0 | 0 | 100,499,500 | Theta(m*n+m^2) |
| LinkedList | 100,000 | 1000 | 0.004267 | 0 | 0 | 0 | Theta(m) |

## Remove at index 0

| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |
|---|---:|---:|---:|---:|---:|---:|---|
| DynamicArray | 100 | 100 | 0.025275 | 100 | 0 | 4,950 | Theta(m*n) |
| LinkedList | 100 | 100 | 0.003958 | 100 | 0 | 0 | Theta(m) |
| DynamicArray | 1,000 | 1000 | 0.042400 | 1,000 | 0 | 499,500 | Theta(m*n) |
| LinkedList | 1,000 | 1000 | 0.005209 | 1,000 | 0 | 0 | Theta(m) |
| DynamicArray | 10,000 | 1000 | 0.571450 | 1,000 | 0 | 9,499,500 | Theta(m*n) |
| LinkedList | 10,000 | 1000 | 0.010992 | 1,000 | 0 | 0 | Theta(m) |
| DynamicArray | 100,000 | 1000 | 7.520308 | 1,000 | 0 | 99,499,500 | Theta(m*n) |
| LinkedList | 100,000 | 1000 | 0.001817 | 1,000 | 0 | 0 | Theta(m) |

## Insert at fixed n/2

| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |
|---|---:|---:|---:|---:|---:|---:|---|
| DynamicArray | 100 | 1000 | 0.044141 | 0 | 0 | 551,420 | Theta(m*n+m^2) |
| LinkedList | 100 | 1000 | 0.047633 | 50,000 | 0 | 0 | Theta(m*n) |
| DynamicArray | 1,000 | 1000 | 0.073867 | 0 | 0 | 1,000,524 | Theta(m*n+m^2) |
| LinkedList | 1,000 | 1000 | 0.542358 | 500,000 | 0 | 0 | Theta(m*n) |
| DynamicArray | 10,000 | 1000 | 0.322225 | 0 | 0 | 5,499,500 | Theta(m*n+m^2) |
| LinkedList | 10,000 | 1000 | 1.263808 | 5,000,000 | 0 | 0 | Theta(m*n) |
| DynamicArray | 100,000 | 1000 | 2.941233 | 0 | 0 | 50,499,500 | Theta(m*n+m^2) |
| LinkedList | 100,000 | 1000 | 67.214792 | 50,000,000 | 0 | 0 | Theta(m*n) |

## Remove at fixed n/2

| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |
|---|---:|---:|---:|---:|---:|---:|---|
| DynamicArray | 100 | 50 | 0.002325 | 50 | 0 | 1,225 | Theta(m*n) |
| LinkedList | 100 | 50 | 0.002267 | 2,550 | 0 | 0 | Theta(m*n) |
| DynamicArray | 1,000 | 500 | 0.017650 | 500 | 0 | 124,750 | Theta(m*n) |
| LinkedList | 1,000 | 500 | 0.078675 | 250,500 | 0 | 0 | Theta(m*n) |
| DynamicArray | 10,000 | 1000 | 0.281742 | 1,000 | 0 | 4,499,500 | Theta(m*n) |
| LinkedList | 10,000 | 1000 | 2.829350 | 5,001,000 | 0 | 0 | Theta(m*n) |
| DynamicArray | 100,000 | 1000 | 3.747300 | 1,000 | 0 | 49,499,500 | Theta(m*n) |
| LinkedList | 100,000 | 1000 | 67.310475 | 50,001,000 | 0 | 0 | Theta(m*n) |

## Heap: insert n values

| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |
|---|---:|---:|---:|---:|---:|---:|---|
| MinHeap | 100 | 100 | 0.011059 | 0 | 206 | 0 | O(n*log(n)); expected Theta(n) random keys |
| MinHeap | 1,000 | 1000 | 0.028283 | 0 | 2,326 | 0 | O(n*log(n)); expected Theta(n) random keys |
| MinHeap | 10,000 | 10000 | 0.110467 | 0 | 22,753 | 0 | O(n*log(n)); expected Theta(n) random keys |
| MinHeap | 100,000 | 100000 | 0.875700 | 0 | 227,857 | 0 | O(n*log(n)); expected Theta(n) random keys |

## Heap: extract n values

| Structure | n | Actual m | Mean time (ms) | Accesses | Comparisons | Movements | Workload complexity |
|---|---:|---:|---:|---:|---:|---:|---|
| MinHeap | 100 | 100 | 0.024350 | 0 | 863 | 0 | O(n*log(n)); typical Theta(n*log(n)) |
| MinHeap | 1,000 | 1000 | 0.086508 | 0 | 14,996 | 0 | O(n*log(n)); typical Theta(n*log(n)) |
| MinHeap | 10,000 | 10000 | 0.422267 | 0 | 216,531 | 0 | O(n*log(n)); typical Theta(n*log(n)) |
| MinHeap | 100,000 | 100000 | 5.601975 | 0 | 2,831,426 | 0 | O(n*log(n)); typical Theta(n*log(n)) |

