package net.steel.performance.exception;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ExceptionDrivenBenchmark {

    public static final int LOOP_SIZE = 100;

    private static final int ITERATIONS_WARMUP  = 2;
    private static final int ITERATIONS_MEASURE = 3;


    @State(Scope.Benchmark)
    public static class MyState {

        private List<String> input = new ArrayList<>(LOOP_SIZE);
        Random random = new Random();

        @Setup
        public void setup() {
            System.out.println("setting up...");
            if (!(input.size() > 0)) {
                for (int i = 0; i < LOOP_SIZE; i++) {
                    int rand = random.nextInt(LOOP_SIZE) + 500; //prevent LongCache later on
                    input.add(Integer.toString(rand));
                }
            }
        }
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public List<String> logicExceptionDriven(MyState myState) {
        return readKeysFromMissingObjects_Original(myState.input);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public List<String> logicExceptionDriven_Fixed(MyState myState) {
        return readKeysFromMissingObjects_Fixed(myState.input);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    @Fork(1)
    @Warmup(iterations = ITERATIONS_WARMUP)
    @Measurement(iterations = ITERATIONS_MEASURE)
    @BenchmarkMode(Mode.AverageTime)
    public List<String> logicExceptionDriven_Fixed_2(MyState myState) {
        return readKeysFromMissingObjects_Fixed_2(myState.input);
    }

    /**
     * The exception driven method implementation.
     *
     * @param objectIDs
     * @return
     */
    public List<String> readKeysFromMissingObjects_Original(List<String> objectIDs) {
        List<String> resultSet = new ArrayList<>();

        for (String objectIDString : objectIDs) {
            try {
                Long objectID = Long.valueOf(objectIDString);
                IdentifiableManager.current().getIdentifiable(objectID);
            } catch (IdentifiableNotFoundException e) {
                resultSet.add(objectIDString);
            } catch (NumberFormatException e) {
                //ignore, not an objectID
            }
        }
        return resultSet;
    }

    /**
     * Fixed the most important execution path.
     * This is still for demo purposes.
     * Here are a lot of other issues that still need to be fixed.
     *
     * @param objectIDs
     * @return
     */
    public List<String> readKeysFromMissingObjects_Fixed(List<String> objectIDs) {
        List<String> resultSet = new ArrayList<>();

        for (String objectIDString : objectIDs) {
            try {
                Long objectID = Long.valueOf(objectIDString);
                if (!IdentifiableManager.current().containsIdentifiable(objectID)) {
                    resultSet.add(objectIDString);
                }
            } catch (NumberFormatException e) {
                //ignore, not an objectID
            }
        }
        return resultSet;
    }

    public List<String> readKeysFromMissingObjects_Fixed_2(List<String> objectIDs) {
        List<String> resultSet = new ArrayList<>();

        for (String objectIDString : objectIDs) {
            try {
                Long objectID = Long.valueOf(objectIDString);
                if (!IdentifiableManager.current().containsIdentifiable_2(objectID)) {
                    resultSet.add(objectIDString);
                }
            } catch (NumberFormatException e) {
                //ignore, not an objectID
            }
        }
        return resultSet;
    }


}
