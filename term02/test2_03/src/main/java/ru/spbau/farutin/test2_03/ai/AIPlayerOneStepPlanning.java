package ru.spbau.farutin.test2_03.ai;

import org.jetbrains.annotations.NotNull;
import ru.spbau.farutin.test2_03.util.Field;
import ru.spbau.farutin.test2_03.util.Pair;

/**
 * AIPlayerOneStepPlanning - AIPlayer implementation with one step planning.
 */
public class AIPlayerOneStepPlanning implements AIPlayer {
    /**
     * If there is a cell which AI can choose and win at once - it chooses it.
     * Else if there is a cell which opponent
     * can choose and win at once - AI chooses it.
     * Else chooses any free cell.
     * @param field current field
     * @return pair of chosen coordinates
     */
    @Override
    public @NotNull
    Pair<Integer, Integer> chooseMove(@NotNull Field field) {
        TestField testField = new TestField(field);
        int size = testField.getSize();
        int x = 0, y = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (testField.getCellValue(i, j) == -1) {
                    testField.setCellValue(1, i, j);

                    if (testField.checkWin(1)) {
                        return new Pair<>(i, j);
                    }

                    testField.setCellValue(-1, i, j);
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (testField.getCellValue(i, j) == -1) {
                    x = i;
                    y = j;

                    testField.setCellValue(0, i, j);

                    if (testField.checkWin(0)) {
                        return new Pair<>(i, j);
                    }

                    testField.setCellValue(-1, i, j);
                }
            }
        }

        return new Pair<>(x, y);
    }

    /**
     * TestField - extends Field so that AI can change cells as it wants.
     */
    private class TestField extends Field {
        private TestField(@NotNull Field other) {
            super(other.getSize());

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    data[x][y] = other.getCellValue(x, y);
                }
            }
        }

        private void setCellValue(int value, int x, int y) {
            data[x][y] = value;
        }
    }
}
