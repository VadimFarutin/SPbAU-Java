package ru.spbau.farutin.homework2_02.util;

/**
 * Stats - class for storing statistics.
 */
public class Stats {
    private static int winCnt = 0;
    private static int drawCnt = 0;
    private static int loseCnt = 0;

    /**
     * Win counter increment.
     */
    public static void win() {
        winCnt++;
    }

    /**
     * Draw counter increment.
     */
    public static void draw() {
        drawCnt++;
    }

    /**
     * Lose counter increment.
     */
    public static void lose() {
        loseCnt++;
    }

    public static int getWinCnt() {
        return winCnt;
    }

    public static int getDrawCnt() {
        return drawCnt;
    }

    public static int getLoseCnt() {
        return loseCnt;
    }
}
