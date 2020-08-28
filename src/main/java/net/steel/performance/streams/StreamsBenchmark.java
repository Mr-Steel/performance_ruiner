package net.steel.performance.streams;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Compares streams vs. Array List Loop, to demonstrate overhead of streams for small operations.
 * A random integer list is created at start.
 * The list is afterwards filtered by a predicate.
 * Every item in the resulting list is modified and the result will again be an Integer.
 * The sum over the resulting elements is computed.
 *
 */
public class StreamsBenchmark {

    private static final int ITERATIONS_WARMUP  = 2;
    private static final int ITERATIONS_MEASURE = 3;


    @State(Scope.Benchmark)
    public static class MyState {

        @Param({"10000", "1000000"})
        private long size;

        List<Integer> arrayList = new ArrayList<>();

        @Setup
        public void createMap() {
            arrayList.clear();
            arrayList = new Random().ints(0, 100)
                    .limit(size)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    /*
        The map to int function is using the modify function directly, thus removing an unnecessary pipeline step.
     */
    public Integer testStreamOfObjectsOneMethod(MyState myState) {
        List<Integer> list = myState.arrayList;
        return list.stream()
                   .filter(this::predicate)
                   .mapToInt(this::modify)
                   .sum();
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    /*
        The map to int function is using the modify function directly, thus removing an unnecessary pipeline step and operating on a parallel stream.
     */
    public Integer testStreamOfObjectsOneMethodParallel(MyState myState) {
        List<Integer> list = myState.arrayList;
        return list.parallelStream()
                   .filter(this::predicate)
                   .mapToInt(this::modify)
                   .sum();
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public Integer testStreamOfObjects(MyState myState) {
        List<Integer> list = myState.arrayList;
        return list.stream()
            .filter(this::predicate)
            .map(this::modify)
            .mapToInt(Integer::valueOf)
            .sum();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    /*
     * This will be slower, since all values are mapped explicitly and not via autoboxing feature
     */
    public Integer testStreamOfObjectsMapFirstToIntStream(MyState myState) {
        List<Integer> list = myState.arrayList;
        return list.stream()
                   .mapToInt(Integer::valueOf)
                   .filter(this::predicate)
                   .map(this::modify)
                   .sum();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    /*
     * Predicate is computed first, then we map to native.
     */
    public Integer testStreamOfObjectsMapLatestToIntStream(MyState myState) {
        List<Integer> list = myState.arrayList;
        return list.stream()
                   .filter(this::predicate)
                   .mapToInt(Integer::valueOf)
                   .map(this::modify)
                   .sum();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    /*
     * runs all of the desired operations in a loop
     */
    public Integer testCollection(MyState myState) {
        List<Integer> list = myState.arrayList;
        int res = 0;
        for (Integer value : list) {
            if (predicate(value)) {
                res += modify(value);
            }
        }
        return res;
    }


    private boolean predicate(int input) {
        return input % 5 == 0;
    }

    private int modify(int input) {
        return (input * 3) -4;
    }

}
