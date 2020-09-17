package net.steel.performance.hierarchy.model;

public class AnotherCopyOfDirectRectangle extends AbstractShape implements RectangularShape {

    private float a, b;

    public AnotherCopyOfDirectRectangle(float a, float b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public float surface() {
        return a*b;
    }

    @Override
    public float sideA() {
        return a;
    }

    @Override
    public float sideB() {
        return b;
    }
}