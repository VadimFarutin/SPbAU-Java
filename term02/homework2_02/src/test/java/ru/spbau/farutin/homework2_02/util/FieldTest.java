package ru.spbau.farutin.homework2_02.util;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * FieldTest - tests for tic-tac-toe Field.
 */
public class FieldTest {
    /**
     * Tests isCellFree on all types of cells.
     */
    @Test
    public void testIsCellFree() throws Exception {
        Field field = new Field(3);
        field.makeMove(0, 1, 2);
        field.makeMove(1, 1, 0);

        assertTrue(field.isCellFree(1, 1));
        assertFalse(field.isCellFree(1, 2));
        assertFalse(field.isCellFree(1, 0));
    }

    /**
     * Tests isCellFree on all cells in the empty field.
     */
    @Test
    public void testIsCellFreeOnEmptyField() throws Exception {
        int n = 3;
        Field field = new Field(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                assertTrue(field.isCellFree(i, j));
            }
        }
    }

    /**
     * Tests getCellValue on all types of cells.
     */
    @Test
    public void getCellValue() throws Exception {
        Field field = new Field(3);
        field.makeMove(0, 1, 2);
        field.makeMove(1, 1, 0);

        assertThat(field.getCellValue(1, 1), is(-1));
        assertThat(field.getCellValue(1, 2), is(0));
        assertThat(field.getCellValue(1, 0), is(1));
    }

    /**
     * Tests getCellValue on all cells in the empty field.
     */
    @Test
    public void testGetCellValueOnEmptyField() throws Exception {
        int n = 3;
        Field field = new Field(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                assertThat(field.getCellValue(i, j), is(-1));
            }
        }
    }

    /**
     * Tests makeMove on free cell.
     */
    @Test
    public void makeMoveAccepted() throws Exception {
        Field field = new Field(3);

        assertTrue(field.makeMove(0, 1, 2));
        assertThat(field.getCellValue(1, 2), is(0));
    }

    /**
     * Tests makeMove on not free cell.
     */
    @Test
    public void makeMoveNotAccepted() throws Exception {
        Field field = new Field(3);

        assertTrue(field.makeMove(0, 1, 2));
        assertThat(field.getCellValue(1, 2), is(0));
        assertFalse(field.makeMove(1, 1, 2));
        assertThat(field.getCellValue(1, 2), is(0));
        assertFalse(field.makeMove(0, 1, 2));
        assertThat(field.getCellValue(1, 2), is(0));
    }

    /**
     * Tests makeMove with illegal coordinates.
     */
    @Test
    public void makeMoveOutOfBounds() throws Exception {
        Field field = new Field(3);
        assertFalse(field.makeMove(0, 4, 0));
    }

    /**
     * Tests isFull on empty field.
     */
    @Test
    public void testIsFullOnEmptyField() throws Exception {
        Field field = new Field(3);
        assertFalse(field.isFull());
    }

    /**
     * Tests isFull on not full field.
     */
    @Test
    public void testIsFullFalse() throws Exception {
        Field field = new Field(3);
        field.makeMove(0, 1, 2);
        field.makeMove(1, 1, 1);
        field.makeMove(0, 0, 0);
        field.makeMove(1, 2, 2);

        assertFalse(field.isFull());
    }

    /**
     * Tests isFull on full field.
     */
    @Test
    public void testIsFullTrue() throws Exception {
        int n = 3;
        Field field = new Field(n);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                field.makeMove((i + j) & 1, i, j);
            }
        }


        assertTrue(field.isFull());
    }

    /**
     * Tests checkWin when winning combination is horizontal.
     */
    @Test
    public void checkWinHorizontal() throws Exception {
        Field field = new Field(3);

        field.makeMove(0, 1, 0);
        field.makeMove(0, 1, 1);
        field.makeMove(0, 1, 2);
        field.makeMove(1, 2, 2);
        field.makeMove(1, 0, 0);
        field.makeMove(1, 2, 1);

        assertTrue(field.checkWin(0));
        assertFalse(field.checkWin(1));
    }

    /**
     * Tests checkWin when winning combination is vertical.
     */
    @Test
    public void checkWinVertical() throws Exception {
        Field field = new Field(3);

        field.makeMove(0, 1, 0);
        field.makeMove(0, 1, 1);
        field.makeMove(0, 0, 0);
        field.makeMove(1, 0, 2);
        field.makeMove(1, 1, 2);
        field.makeMove(1, 2, 2);

        assertTrue(field.checkWin(1));
        assertFalse(field.checkWin(0));
    }

    /**
     * Tests checkWin when winning combination is from left bottom to right top corner.
     */
    @Test
    public void checkWinDiagonalFirst() throws Exception {
        Field field = new Field(3);

        field.makeMove(0, 0, 0);
        field.makeMove(0, 1, 1);
        field.makeMove(0, 2, 2);
        field.makeMove(1, 0, 2);
        field.makeMove(1, 1, 2);
        field.makeMove(1, 2, 1);

        assertTrue(field.checkWin(0));
        assertFalse(field.checkWin(1));
    }

    /**
     * Tests checkWin when winning combination is from left top to right bottom corner.
     */
    @Test
    public void checkWinDiagonalSecond() throws Exception {
        Field field = new Field(3);

        field.makeMove(0, 2, 0);
        field.makeMove(0, 1, 1);
        field.makeMove(0, 0, 2);
        field.makeMove(1, 0, 1);
        field.makeMove(1, 1, 0);
        field.makeMove(1, 2, 2);

        assertTrue(field.checkWin(0));
        assertFalse(field.checkWin(1));
    }
}
