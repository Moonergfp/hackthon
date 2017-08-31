package com.hack.algorithm;

import org.junit.Test;

public class OneZeroPackage {

    @Test
    public void test() {

        int n = 5;
        int C = 10;
        int[][] x = new int[][] {
//                { 4, 9 }, { 3, 6 }, { 5, 1 }, { 2, 4 }, { 5, 1 },
                // {4, 9},
                // {4, 20},
                // {3, 6},
                // {4, 20},
                // {2, 4},
                // {5, 10},
                  { 2, 3 }, { 2, 5 }, { 2, 4 }, { 2, 3 },{ 2, 6 },
        };

        int[][] d = new int[6][11];

        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= C; ++j) {
                System.out.println("i=" + i + " j=" + j);
                d[i][j] = i == 0 ? 0 : d[i - 1][j];
                if (i > 0 && j >= x[i - 1][0] && d[i][j] < d[i - 1][j - x[i - 1][0]] + x[i - 1][1]) {
                    int temp = d[i - 1][j - x[i - 1][0]] + x[i - 1][1];
                    System.out.println("temp == >" + temp);
                    d[i][j] = temp;
                    System.out.println("d[i][j]== >" + d[i][j]);
                }
            }
        }
        System.out.println(d[n][C]);
    }

}
