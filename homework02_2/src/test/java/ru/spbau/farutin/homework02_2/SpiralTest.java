package ru.spbau.farutin.homework02_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SpiralTest.java - unit tests for Spiral class.
 */
public class SpiralTest {
    /**
     * These tests check print() on different square matrixes.
     * NxN specifies dimension of the matrix.
     */
    @Test
    public void testPrint1x1() {
        Spiral spiral = new Spiral(new int[][] {{7}});
        int[] expectedResult = new int[] {7};

        assertArrayEquals(expectedResult, spiral.print(), "wrong print() output");
    }

    @Test
    public void testPrint3x3() {
        Spiral spiral = new Spiral(new int[][] {{2, 3, 1},
                                                {4, 5, 6},
                                                {7, 8, 9}});
        int[] expectedResult = new int[] {5, 4, 7, 8, 9, 6, 1, 3, 2};

        assertArrayEquals(expectedResult, spiral.print(), "wrong print() output");
    }

    @Test
    public void testPrint5x5() {
        Spiral spiral = new Spiral(new int[][] {{2, 5, 1, 4, 3},
                                                {4, 5, 6, 3, 2},
                                                {0, 0, 7, 8, 9},
                                                {1, 5, 3, 2, 9},
                                                {7, 6, 3, 4, 4}});
        int[] expectedResult = new int[] {7, 0, 5, 3, 2, 8, 3, 6, 5, 4, 0, 1, 7, 6, 3, 4, 4, 9, 9, 2, 3, 4, 1, 5, 2};

        assertArrayEquals(expectedResult, spiral.print(), "wrong print() output");
    }

    /**
     * These tests check columnSort() on different square matrixes.
     * NxN specifies dimension of the matrix.
     */
    @Test
    public void testColumnSort1x1() {
        Spiral spiral = new Spiral(new int[][] {{7}});
        spiral.columnSort();
        int[] expectedResult = new int[] {7};

        assertArrayEquals(expectedResult, spiral.print(), "columns are sorted incorrectly");
    }

    @Test
    public void testColumnSort3x3() {
        Spiral spiral = new Spiral(new int[][] {{2, 3, 1},
                                                {4, 5, 6},
                                                {7, 8, 9}});
        spiral.columnSort();
        int[] expectedResult = new int[] {4, 6, 9, 7, 8, 5, 3, 2, 1};

        assertArrayEquals(expectedResult, spiral.print(), "columns are sorted incorrectly");
    }

    @Test
    public void testColumnSort5x5() {
        Spiral spiral = new Spiral(new int[][] {{2, 5, 1, 4, 3},
                                                {4, 5, 6, 3, 2},
                                                {0, 0, 7, 8, 9},
                                                {1, 5, 3, 2, 9},
                                                {7, 6, 3, 4, 4}});
        spiral.columnSort();
        int[] expectedResult = new int[] {9, 0, 1, 9, 2, 8, 3, 2, 4, 6, 7, 3, 3, 7, 4, 4, 6, 5, 0, 5, 5, 4, 3, 2, 1};

        assertArrayEquals(expectedResult, spiral.print(), "columns are sorted incorrectly");
    }
}