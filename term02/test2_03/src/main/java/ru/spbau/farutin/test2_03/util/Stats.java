package ru.spbau.farutin.test2_03.util;

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
    public static void addWin() {
        winCnt++;
    }

    /**
     * Draw counter increment.
     */
    public static void addDraw() {
        drawCnt++;
    }

    /**
     * Lose counter increment.
     */
    public static void addLose() {
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
