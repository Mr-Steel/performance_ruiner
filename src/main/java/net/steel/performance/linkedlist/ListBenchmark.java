package net.steel.performance.linkedlist;

import org.eclipse.collections.impl.list.mutable.FastList;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ListBenchmark {

    private static final int ITERATIONS_WARMUP  = 2;
    private static final int ITERATIONS_MEASURE = 3;


    @State(Scope.Benchmark)
    public static class MyState {

        @Param({"10", "100", "1000"})
        private long size;

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> fastList = new FastList<>();
        List<Integer> linkedList = new LinkedList<>();

        @Setup(Level.Invocation)
        public void createMap() {
            arrayList.clear();
            arrayList = new Random().ints(0, 100)
                    .limit(size)
                    .boxed()
                    .collect(Collectors.toCollection(ArrayList::new));

            fastList.clear();
            fastList = new Random().ints(0, 100)
                    .limit(size)
                    .boxed()
                    .collect(Collectors.toCollection(FastList::new));

            linkedList.clear();
            linkedList = new Random().ints(0, 100)
                    .limit(size)
                    .boxed()
                    .collect(Collectors.toCollection(LinkedList::new));
        }
    }



    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public Integer testAddRemoveArrayList(MyState myState) {
        List<Integer> list = myState.arrayList;
        for (int i = 0; i < list.size(); i++) {
            Integer next = list.get(i);
            if (next % 3 == 0) {
                list.remove(i);
            }
            if (next % 5 == 0) {
                list.add(next / 5);
            }
        }
        return myState.arrayList.size();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public Integer testAddRemoveFastList(MyState myState) {
        List<Integer> list = myState.fastList;
        for (int i = 0; i < list.size(); i++) {
            Integer next = list.get(i);
            if (next % 3 == 0) {
                list.remove(i);
            }
            if (next % 5 == 0) {
                list.add(next / 5);
            }
        }
        return myState.fastList.size();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public Integer testAddRemoveLinkedList(MyState myState) {
        ListIterator<Integer> iterator = myState.linkedList.listIterator();
        while (iterator.hasNext()) {
            final Integer next = iterator.next();
            if (next % 3 == 0) {
                iterator.remove();
            }
            if (next % 5 == 0) {
                iterator.add(next/5);
            }
        }
        return myState.linkedList.size();
    }

}
