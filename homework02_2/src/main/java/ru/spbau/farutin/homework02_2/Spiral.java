package ru.spbau.farutin.homework02_2;

import java.util.Arrays;

/**
 * Spiral.java - this class stores square matrix and can
 * print it spirally and sort columns by first element.
 */
public class Spiral {
    private int size;
    private int[][] data;

    /**
     * Matrix is stored in rotated position
     * so that it could be sorted easier.
     * @param elements elements of int type, dimension must be odd
     */
    public Spiral(int[][] elements) {
        size = elements.length;
        data = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = elements[j][i];
            }
        }
    }

    /**
     * Prints stored matrix spirally.
     * @return elements in spiral order
     */
    public int[] print() {
        int[] result = new int[size * size];

        int[] xDirection = new int[] {-1, 0, 1, 0};
        int[] yDirection = new int[] {0, 1, 0, -1};

        int elementsCount = 0;
        int length = 1;
        int directionIndex = 0;
        int x = size / 2;
        int y = size / 2;

        while (true) {
            if (elementsCount == size * (size - 1)) {
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

        for (int i = 0; i < size; i++) {
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
