#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
mkdir -p build
javac --release 17 -d build src/*.java
java -cp build Tests
java -cp build Benchmark
