package Lecture.Vector.Operators;

public class IntegerOperator implements NumericOperator<Integer> {

    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer pow(Integer a, Double b) {
        return (int) Math.pow(a, b);
    }

    @Override
    public Double sin(Integer a) {
        return Math.sin(a);
    }

    @Override
    public Double cos(Integer a) {
        return Math.cos(a);
    }

    @Override
    public Double arccos(Integer a) {
        return Math.acos(a);
    }
}
