package Lecture;

public class MatrixMultiplication {
    public static double[][] multiplay(double[][] Matrix1, double[][] Matrix2) {
        if (Matrix1[0].length != Matrix2.length)
            throw new IllegalArgumentException("Matrix dimensions doesn't match");

        double[][] result = new double[Matrix1.length][Matrix2[0].length];

        for(int i = 0; i < Matrix1.length; i++) {
            for(int j = 0; j < Matrix2[0].length; j++) {
                for(int k = 0; k < Matrix1[0].length; k++) {
                    result[i][j] += Matrix1[i][k] * Matrix2[k][j];
                }
            }
        }

        return result;
    }
}
