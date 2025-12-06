package Lecture.Vector.Operators;

public interface NumericOperator <T extends Number> {
    T add(T a,  T b);
    T subtract(T a,  T b);
    T multiply(T a,  T b);
    T divide(T a,  T b);
    T pow(T a, Double b);
    Double sin(T a);
    Double cos(T a);
    Double arccos(T a);
}
