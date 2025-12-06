package Lecture.Vector.Operators;

public class FloatOperator implements NumericOperator<Float> {
    @Override
    public Float add(Float a, Float b) {
        return a + b;
    }

    @Override
    public Float subtract(Float a, Float b) {
        return a - b;
    }

    @Override
    public Float multiply(Float a, Float b) {
        return a * b;
    }

    @Override
    public Float divide(Float a, Float b) {
        return a / b;
    }

    @Override
    public Float pow(Float a, Double b) {
        return (float) Math.pow(a, b);
    }

    @Override
    public Double sin(Float a) {
        return Math.sin(a);
    }

    @Override
    public Double cos(Float a) {
        return Math.cos(a);
    }

    @Override
    public Double arccos(Float a) {
        return Math.acos(a);
    }
}
