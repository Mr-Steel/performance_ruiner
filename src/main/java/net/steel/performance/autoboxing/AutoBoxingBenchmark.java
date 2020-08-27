package net.steel.performance.autoboxing;

import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AutoBoxingBenchmark {

    private static final int ITERATIONS_WARMUP  = 2;
    private static final int ITERATIONS_MEASURE = 3;

    private static final int LOOP_SIZE = 50;

    @State(Scope.Benchmark)
    public static class MyState {

        private int randomInt;
        private Integer randomInteger;
        Random random = new Random();

        private Long totalLongObject;
        private long totalLongPrim;

        @Setup(Level.Invocation)
        public void setup() {

            totalLongObject = 23L;
            totalLongPrim = 27;

            randomInt = random.nextInt();
            randomInteger = Integer.valueOf(randomInt);
        }
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public Long sumIntObject(AutoBoxingBenchmark.MyState myState) {
        return myState.totalLongObject + myState.randomInt;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public long sumIntPrimitive(AutoBoxingBenchmark.MyState myState) {
        return myState.totalLongPrim + myState.randomInt;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public MutableIntList sumIntLoopPrimitiveIntList(AutoBoxingBenchmark.MyState myState) {
        MutableIntList li = new IntArrayList();
        for (int i = 1; i < LOOP_SIZE; i += 2)
            li.add(i);
        return li;
    }



    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public List<Integer> sumIntLoopObjectArrayList(AutoBoxingBenchmark.MyState myState) {
        List<Integer> li = new ArrayList<>();
        for (int i = 1; i < LOOP_SIZE; i += 2)
            li.add(i);
        return li;
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public List<Integer> sumIntLoopObjectFastList(AutoBoxingBenchmark.MyState myState) {
        List<Integer> li = new FastList<>();
        for (int i = 1; i < LOOP_SIZE; i += 2)
            li.add(i);
        return li;
    }


}
