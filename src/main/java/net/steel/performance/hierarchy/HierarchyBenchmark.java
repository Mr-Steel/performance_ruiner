package net.steel.performance.hierarchy;

import net.steel.performance.hierarchy.model.*;
import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Example that shows costs for polymorphism and complex object hierarchy.
 *
 * The object hierarchy consists of geometric forms that should compute a surface.
 */
public class HierarchyBenchmark {

  private static final int ITERATIONS_WARMUP  = 5;
  private static final int ITERATIONS_MEASURE = 10;


  @State(Scope.Benchmark)
  public static class MyState {

    @Param({"10000", "1000000"})
    private long size;

    private final List<Shape>              polyMorphComposition = new ArrayList<>();
    private final List<CompositeRectangle> typedComposite       = new ArrayList<>();
    private final List<DirectRectangle>    typedDirect          = new ArrayList<>();

    @Setup
    public void createMap() {
      polyMorphComposition.clear();
      typedComposite.clear();
      typedDirect.clear();
      for (int i = 0; i < size; i++) {
        if (i % 4 == 0)
          polyMorphComposition.add(new DirectRectangle(5, i % 5000));
        else if (i % 4 == 1)
          polyMorphComposition.add(new AnotherCopyOfDirectRectangle(5, i % 5000));
        else if (i % 4 == 2)
          polyMorphComposition.add(new AnotherCopyOfDirectRectangle2(5, i % 5000));
        else
          polyMorphComposition.add(new CopyOfDirectRectangle(5, i % 5000));

        typedComposite.add(new CompositeRectangle(new Line(5), new Line(i % 5000)));
        typedDirect.add(new DirectRectangle(5, i % 5000));
      }
    }
  }

  @Benchmark
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  @Fork(1)
  @Warmup(iterations = ITERATIONS_WARMUP)
  @Measurement(iterations = ITERATIONS_MEASURE)
  @BenchmarkMode(Mode.AverageTime)
  public double testPolyMorph(MyState myState) {
    double result = 0;
    for (Shape shape : myState.polyMorphComposition) {
      result += shape.surface();
    }
    return result;
  }

  @Benchmark
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  @Fork(1)
  @Warmup(iterations = ITERATIONS_WARMUP)
  @Measurement(iterations = ITERATIONS_MEASURE)
  @BenchmarkMode(Mode.AverageTime)
  public double testTypedComposite(MyState myState) {
    double result = 0;
    for (CompositeRectangle shape : myState.typedComposite) {
      result += shape.surface();
    }
    return result;
  }

  @Benchmark
  @OutputTimeUnit(TimeUnit.NANOSECONDS)
  @Fork(1)
  @Warmup(iterations = ITERATIONS_WARMUP)
  @Measurement(iterations = ITERATIONS_MEASURE)
  @BenchmarkMode(Mode.AverageTime)
  public double testTypedDirect(MyState myState) {
    double result = 0;
    for (DirectRectangle shape : myState.typedDirect) {
      result += shape.surface();
    }
    return result;
  }

}
