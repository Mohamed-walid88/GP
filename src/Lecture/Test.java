package Lecture;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        double[][] a = new double[2][2];
        double[][] b = new double[2][2];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                a[i][j] = (int)(Math.random() * 10) + 1;
                b[i][j] = (int)(Math.random() * 10) + 1;
            }
        }

        System.out.println(Arrays.deepToString(a));
        System.out.println(Arrays.deepToString(b));

        double[][] added = MatrixAddition.add(a, b);
        System.out.println(Arrays.deepToString(added));

        double[][] multiplied = MatrixMultiplication.multiplay(a, b);
        System.out.println(Arrays.deepToString(multiplied));

        b = MatrixScalarMultiplication.multiply(2, b);
        System.out.println(Arrays.deepToString(b));
    }
}
