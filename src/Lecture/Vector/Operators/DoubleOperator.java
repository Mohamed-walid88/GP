package Lecture.Vector.Operators;

public class DoubleOperator implements NumericOperator<Double> {
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double pow(Double a, Double b) {
        return Math.pow(a, b);
    }

    @Override
    public Double sin(Double a) {
        return Math.sin(a);
    }

    @Override
    public Double cos(Double a) {
        return Math.cos(a);
    }

    @Override
    public Double arccos(Double a) {
        return Math.acos(a);
    }
}
