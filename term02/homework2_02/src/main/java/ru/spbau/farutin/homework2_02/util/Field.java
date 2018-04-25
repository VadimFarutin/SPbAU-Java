package ru.spbau.farutin.homework2_02.util;

/**
 * Field - class for storing tic-tac-toe game field.
 */
public class Field {
    protected int[][] data;
    protected int size;
    private int freeCnt;

    /**
     * Constructs new square field of given size.
     * @param n side size of the field
     */
    public Field(int n) {
        data = new int[n][n];
        size = n;
        freeCnt = n * n;
        clear();
    }

    private void clear() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                data[i][j] = -1;
            }
        }
    }

    /**
     * Getter for field size.
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter for value stored at given coordinates.
     * @param x first coordinate
     * @param y second coordinate
     * @return cell value
     */
    public int getCellValue(int x, int y) {
        return data[x][y];
    }

    /**
     * Fills a sell if it is free.
     * @param player type of the mark to save
     * @param x first coordinate
     * @param y second coordinate
     * @return true if cell was free, false otherwise
     */
    public boolean makeMove(int player, int x, int y) {
        if (!isCellFree(x, y)) {
            return false;
        }

        data[x][y] = player;
        freeCnt--;

        return true;
    }

    /**
     * Checks whether cell at given coordinates is free or not.
     * @param x first coordinate
     * @param y second coordinate
     * @return true if cell is free, false otherwise
     */
    public boolean isCellFree(int x, int y) {
        return x < size && x >= 0 && y < size && y >= 0 && data[x][y] == -1;
    }

    /**
     * Checks whether there are any free cells in the field or not.
     * @return true if field is full, false otherwise
     */
    public boolean isFull() {
        return freeCnt == 0;
    }

    /**
     * Looks for {@link #size} marks in a row for given player.
     * @param player whose marks to look for
     * @return true if player has a winning combination, false otherwise
     */
    public boolean checkWin(int player) {
        return checkWin(player, size);
    }

    /**
     * Looks for {@param n} marks in a row for given player.
     * @param player whose marks to look for
     * @param n required number of marks in a row to win
     * @return true if player has a winning combination, false otherwise
     */
    public boolean checkWin(int player, int n) {
        return n <= size
               && (checkHorizontal(player, n)
                   || checkVertical(player, n)
                   || checkDiagonalLeftBottomToRightTop(player, n)
                   || checkDiagonalLeftTopToRightBottom(player, n));
    }

    private boolean checkHorizontal(int player, int n) {
        boolean isWin;

        for (int x = 0; x < size; x++) {
            for (int y = 0; y <= size - n; y++) {
                isWin = true;

                for (int i = 0; i < n; i++) {
                    if (data[x][y + i] != player) {
                        isWin = false;
                        break;
                    }
                }

                if (isWin) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkVertical(int player, int n) {
        boolean isWin;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x <= size - n; x++) {
                isWin = true;

                for (int i = 0; i < n; i++) {
                    if (data[x + i][y] != player) {
                        isWin = false;
                        break;
                    }
                }

                if (isWin) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkDiagonalLeftBottomToRightTop(int player, int n) {
        boolean isWin;

        for (int x = 0; x <= size - n; x++) {
            for (int y = 0; y <= size - n; y++) {
                isWin = true;

                for (int i = 0; i < n; i++) {
                    if (data[x + i][y + i] != player) {
                        isWin = false;
                        break;
                    }
                }

                if (isWin) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkDiagonalLeftTopToRightBottom(int player, int n) {
        boolean isWin;

        for (int x = size - 1; x >= n - 1; x--) {
            for (int y = 0; y <= size - n; y++) {
                isWin = true;

                for (int i = 0; i < n; i++) {
                    if (data[x - i][y + i] != player) {
                        isWin = false;
                        break;
                    }
                }

                if (isWin) {
                    return true;
                }
            }
        }

        return false;
    }
}
