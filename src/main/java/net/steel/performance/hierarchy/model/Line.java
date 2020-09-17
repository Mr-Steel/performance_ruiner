package net.steel.performance.hierarchy.model;

/**
 * @author Melv80
 */
public class Line extends AbstractShape {

  private final float length;

  public Line(float length) {
    this.length = length;
  }

  @Override
  public float surface() {
    return length;
  }
}

