package ru.spbau.farutin.homework02_2;

import java.util.Arrays;

/**
 * Spiral.java - this class stores square matrix and can
 * print it spirally and sort columns by first element.
 */
public class Spiral {
    private int n;
    private int[][] data;

    public static void main(String[] args) {
        Spiral spiral = new Spiral(new int[][] {{2, 3, 1}, {4, 5, 6}, {7, 8, 9}});

        int[] result = spiral.print();
        for (int i = 0; i < result.length; i++) {
            System.out.print(" " + result[i]);
        }

        spiral.columnSort();

        result = spiral.print();
        for (int i = 0; i < result.length; i++) {
            System.out.print(" " + result[i]);
        }
    }

    /**
     * Matrix is stored in rotated position
     * so that it could be sorted easier.
     * @param elements elements of int type, dimension must be odd
     */
    public Spiral(int[][] elements) {
        n = elements.length;
        data = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = elements[j][i];
            }
        }
    }

    /**
     * Prints stored matrix spirally.
     * @return elements in spiral order
     */
    public int[] print() {
        int[] result = new int[n * n];

        int[] xDirection = new int[] {-1, 0, 1, 0};
        int[] yDirection = new int[] {0, 1, 0, -1};

        int elementsCount = 0;
        int length = 1;
        int directionIndex = 0;
        int x = n / 2;
        int y = n / 2;

        while (true) {
            if (elementsCount == n * (n - 1)) {
                break;
            }

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < length; j++) {
                    result[elementsCount++] = data[x][y];
                    x += xDirection[directionIndex % 4];
                    y += yDirection[directionIndex % 4];
                }

                directionIndex++;
            }

            length++;
        }

        for (int i = 0; i < n; i++) {
            result[elementsCount++] = data[x--][y];
        }

        return result;
    }

    /**
     * Sorts matrix columns by their first elements.
     */
    public void columnSort() {
        Arrays.sort(data, (o1, o2) -> {
            if (o1[0] <= o2[0]) {
                if (o1[0] == o2[0]) {
                    return 0;
                }

                return -1;
            }

            return 1;
        });
    }
}
