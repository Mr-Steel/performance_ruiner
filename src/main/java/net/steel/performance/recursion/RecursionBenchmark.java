package net.steel.performance.recursion;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Recursion is often much slower than iteration and should be avoided when runtime critical​
 * Can blow up Stack of the JVM on large call depths​
 * Recursion can often be transformed to iteration using an Accumulator ​
 * Do avoid large stack depth use a queue as an assisting data structure
 */
public class RecursionBenchmark {

    private static final int ITERATIONS_WARMUP  = 2;
    private static final int ITERATIONS_MEASURE = 3;
    private static final int MAX = 1000;


    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    /*
    * recursion with faculty
     */
    public Long testFacultyRecursive() {
        long result = 0;
        for (int i = 1; i < MAX; i++) {
             result+=Factorial.fac(i);
        }
        return result;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    /*
     * recursion with iterative
     */
    public Long testFacultyIterative() {
        long result = 0;
        for (int i = 0; i < MAX; i++) {
            result+=Factorial.fac_it(i);
        }
        return result;
    }



}
