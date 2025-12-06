package Lecture.Vector;

import Lecture.Vector.Operators.DoubleOperator;
import Lecture.Vector.Operators.FloatOperator;
import Lecture.Vector.Operators.IntegerOperator;
import Lecture.Vector.Operators.NumericOperator;

public class Vector<T extends Number> {
    private final int dim;
    private final T[] values;
    private final NumericOperator<T> operator;

    public Vector(int dim, T[] values) {
        this.dim = dim;
        this.values = values;
        this.operator = getOperator(values[0]);
    }
    public Vector(T[] values) {
        this.dim = values.length;
        this.values = values;
        this.operator = getOperator(values[0]);
    }
    public Vector(int dim) {
        this.dim = dim;
        this.values = (T[]) new Object[dim];
        this.operator = getOperator(values[0]);
    }

    public Vector<T> add(Vector<T> vector) {
        if (this.dim != vector.dim)
            throw new IllegalArgumentException("Vector dimensions don't match");

        @SuppressWarnings("unchecked")
        T[] newValues = (T[]) new Object[this.dim];
        for (int i = 0; i < this.dim; i++) {
            newValues[i] = operator.add(this.values[i], vector.values[i]);
        }

        return new Vector<>(newValues);
    }

    public Vector<T> subtract(Vector<T> vector) {
        if (this.dim != vector.dim)
            throw new IllegalArgumentException("Vector dimensions don't match");

        @SuppressWarnings("unchecked")
        T[] newValues = (T[]) new Object[this.dim];
        for (int i = 0; i < this.dim; i++) {
            newValues[i] = operator.subtract(this.values[i], vector.values[i]);
        }

        return new Vector<>(newValues);
    }

    public Vector<T> scalarMultiplication(T scalar) {
        @SuppressWarnings("unchecked")
        T[] newValues = (T[]) new Object[this.dim];

        for (int i = 0; i < this.dim; i++) {
            newValues[i] = operator.multiply(this.values[i], scalar);
        }

        return new Vector<>(newValues);
    }

    public Vector<T> matrixMultiplication(T[][] matrix) {
        if (this.dim != matrix.length)
            throw new IllegalArgumentException("Vector dimensions don't match");

        @SuppressWarnings("unchecked")
        T[] newValues = (T[]) new Object[this.dim];

        for (int i = 0; i < this.dim; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                operator.add(newValues[i], operator.multiply(values[j], matrix[i][j]));
            }
        }

        return new Vector<>(newValues);
    }

    public T dotProduct(Vector<T> vector) {
        if (this.dim != vector.dim)
            throw new IllegalArgumentException("Vector dimensions don't match");

        T result = operator.multiply(this.values[0], vector.values[0]);

        for (int i = 1; i < this.dim; i++) {
            operator.add(operator.multiply(this.values[i], vector.values[i]), result);
        }

        return result;
    }

    public T dotProduct(T length2, T angle) {
        T result = operator.multiply(this.length(), length2);
        result = operator.multiply(result, (T) operator.cos(angle));

        return result;
    }

    /**
     * Calculates the cross product of two 3D vectors.
     * This method accepts Vector of the same type, which is the standard way to handle
     * generic numerical inputs (int, float, double) in Java math utilities.
     *
     * @param vector the vector that the cross product made on
     * @return A new vector A x B.
     * @throws IllegalArgumentException if vectors are not of length 3.
     */
    public Vector<T> crossProduct(Vector<T> vector) {
        if (vector.dim != 3 || this.dim != 3) {
            throw new IllegalArgumentException("Cross product is only defined for 3D vectors.");
        }

        @SuppressWarnings("unchecked")
        T[] newValues = (T[]) new Object[this.dim];

        // Cx = (Ay * Bz) - (Az * By)
        newValues[0] = operator.subtract(operator.multiply(this.values[1], vector.values[2]),
                operator.multiply(this.values[2], vector.values[1]));

        // Cy = (Az * Bx) - (Ax * Bz)
        // The middle component uses a different term order or a sign flip (-(Ax * Bz - Az * Bx))
        // derived from the determinant expansion.
        newValues[1] = operator.subtract(operator.multiply(this.values[2], vector.values[0]),
                operator.multiply(this.values[0], vector.values[2]));

        // Cz = (Ax * By) - (Ay * Bx)
        newValues[2] = operator.subtract(operator.multiply(this.values[0], vector.values[1]),
                operator.multiply(this.values[1], vector.values[0]));

        return new Vector<>(newValues);
    }

    public T crossProductMagnitude(Vector<T> vector, T angle) {
        T result = operator.multiply(this.length(), vector.length());
        result = operator.multiply(result, (T) operator.sin(angle));

        return result;
    }

    public T crossProductMagnitude(T length2, T angle) {
        T result = operator.multiply(this.length(), length2);
        result = operator.multiply(result, (T) operator.sin(angle));

        return result;
    }

    public boolean hasSameDirection(Vector<T> vector) {
        return dotProduct(vector).doubleValue() > 0;
    }

    public boolean isPerpendicular(Vector<T> vector) {
        return dotProduct(vector).doubleValue() == 0;
    }

    public boolean oppositeDirection(Vector<T> vector) {
        return dotProduct(vector).doubleValue() < 0;
    }

    public T length() {
        T dotProduct = dotProduct(this);

        return operator.pow(dotProduct, 0.5);
    }

    public Vector<T> normalize() {
        boolean zeroVector = true;
        for (T value : values) {
            if (value.doubleValue() != 0) {
                zeroVector = false;
                break;
            }
        }

        if (zeroVector)
            throw new ArithmeticException("Cannot normalize a zero vector");

        @SuppressWarnings("unchecked")
        T[] newValues = (T[]) new Object[this.dim];
        T length = this.length();
        for (int i = 0; i < this.dim; i++) {
            newValues[i] = operator.divide(values[i], length);
        }

        return new Vector<>(newValues);
    }

    public Double getAngle(Vector<T> vector) {
        T dotProduct = dotProduct(vector);
        T length1 = this.length();
        T length2 = vector.length();

        T cosine = operator.divide(dotProduct, operator.multiply(length1, length2));

        return operator.arccos(cosine);
    }



    private NumericOperator<T> getOperator(T sample) {
        if (sample instanceof Integer) return (NumericOperator<T>) new IntegerOperator();
        if (sample instanceof Double) return (NumericOperator<T>) new DoubleOperator();
        if (sample instanceof Float) return (NumericOperator<T>) new FloatOperator();
        throw new IllegalArgumentException("Unsupported numeric type: " + sample.getClass());
    }
}