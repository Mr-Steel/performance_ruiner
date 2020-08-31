package net.steel.performance.hierarchy.model;


/**
 * @author Melv80
 */
public class CompositeRectangle extends AbstractShape implements RectangularShape {
  private Line a;
  private Line b;

  public CompositeRectangle(Line a, Line b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public float surface() {
    return a.surface()*b.surface();
  }

  @Override
  public float sideA() {
    return a.surface();
  }

  @Override
  public float sideB() {
    return b.surface();
  }
}

