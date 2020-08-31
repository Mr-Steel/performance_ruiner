package net.steel.performance.hierarchy.model;

/**
 * @author Melv80
 */
public class Line extends AbstractShape {

  private final float len;

  public Line(float len) {
    this.len = len;
  }

  @Override
  public float surface() {
    return len;
  }
}

