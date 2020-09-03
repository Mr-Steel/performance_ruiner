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

    private static final int ITERATIONS_WARMUP  = 3;
    private static final int ITERATIONS_MEASURE = 10;

    private static final int LOOP_SIZE = 50;

    @State(Scope.Benchmark)
    public static class MyState {

        int[] randomInts = new int[LOOP_SIZE];
        Integer[] randomIntegers = new Integer[LOOP_SIZE];
        Integer[] randomIntegers2 = new Integer[LOOP_SIZE];
        Random random = new Random();
        private int randomInt = random.nextInt() + 500;
        private int randomInt2 = random.nextInt() + 500;
        private Integer randomInteger = Integer.valueOf(randomInt) + 500;
        private Integer randomInteger2 = Integer.valueOf(randomInt) + 500;

        private Long totalLongObject = 245633L;
        private long totalLongPrim = 27L;

        @Setup(Level.Trial)
        public void setup() {
            for (int i = 0; i < LOOP_SIZE; i++) {
                randomInts[i] = random.nextInt() + 500;
                randomIntegers[i] = Integer.valueOf(randomInts[i]);
                randomIntegers2[i] = Integer.valueOf(randomInts[i] + 7);
            }
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public int remainderIntPrimitive(AutoBoxingBenchmark.MyState myState) {
        return sumEvenPrimitive(myState.randomInts);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public Integer remainderIntObject(AutoBoxingBenchmark.MyState myState) {
        return sumEvenObject(myState.randomIntegers);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public Integer sum2Integers(AutoBoxingBenchmark.MyState myState) {
        return myState.randomInteger + myState.randomInteger2;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public Integer sumIntInteger(AutoBoxingBenchmark.MyState myState) {
        return myState.randomInteger + myState.randomInt;
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public int sum2IntPrimitives(AutoBoxingBenchmark.MyState myState) {
        return myState.randomInt + myState.randomInt2;
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

    private int sumEvenPrimitive(int[] primitives) {
        int sum = 0;
        for (int i = 0; i < primitives.length; i++) {
            if (primitives[i] % 2 == 0) {
                sum += primitives[i];
            }
        }
        return sum;
    }

    private Integer sumEvenObject(Integer[] primitives) {
        Integer sum = 0;
        for (int i = 0; i < primitives.length; i++) {
            if (primitives[i] % 2 == 0) {
                sum += primitives[i];
            }
        }
        return sum;
    }

}
