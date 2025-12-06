package Lecture;

public class MatrixAddition {
    public static double[][] add(double[][] Matrix1, double[][] Matrix2) {

        if (Matrix1.length != Matrix2.length || Matrix1[0].length != Matrix2[0].length)
            throw new IllegalArgumentException("Matrix lengths are not equal");

        double[][] result = new double[Matrix1.length][Matrix2[0].length];

        for (int i = 0; i < Matrix1.length; i++) {
            for (int j = 0; j < Matrix2[0].length; j++) {
                result[i][j] = Matrix1[i][j] + Matrix2[i][j];
            }
        }
        return result;
    }
}
