package Lecture;

public class MatrixScalarMultiplication {
    public static double[][] multiply(double a, double[][] b) {
        double[][] result = new double[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                result[i][j] = (a * b[i][j]);
            }
        }
        return result;
    }
}
