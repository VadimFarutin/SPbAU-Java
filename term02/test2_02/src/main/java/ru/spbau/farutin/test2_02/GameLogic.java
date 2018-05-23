package ru.spbau.farutin.test2_02;

import javafx.scene.control.Button;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * GameLogic - class for handling player moves.
 */
public class GameLogic {
    private int size;
    private int[][] field;
    private int[] pairs;
    private GameFieldController controller;
    private Button lastButton = null;
    private boolean firstOpened = false;
    private Random random = new Random();
    private int successCount;

    /**
     * Builds new field with given size.
     * @param controller associated ui controller
     * @param size field size
     */
    public GameLogic(@NotNull GameFieldController controller, int size) {
        this.controller = controller;
        this.size = size;

        field = new int[size][size];
        int cnt = size * size;
        pairs = new int[cnt];

        for (int i = 0; i < cnt; i += 2) {
            pairs[i] = pairs[i + 1] = i / 2;
        }

        resetCards();
    }

    /**
     * Shuffles cards on the field.
     */
    public void resetCards() {
        Collections.shuffle(Arrays.asList(pairs), random);

        for (int i = 0; i < size; i++) {
            System.arraycopy(pairs, i * size, field[i], 0, size);
        }

        successCount = 0;
    }

    /**
     * Current field size.
     * @return field size
     */
    public int getSize() {
        return size;
    }

    /**
     * Handles button clicks.
     * @param i first coordinate
     * @param j second coordinate
     */
    public void clickButton(int i, int j) {
        Button current = controller.getButton(i, j);
        current.disableProperty().setValue(true);
        current.setText(String.valueOf(field[i][j]));

        if (!firstOpened) {
            lastButton = current;
            firstOpened = true;
        } else {
            if (lastButton.getText().equals(String.valueOf(field[i][j]))) {
                firstOpened = false;

                successCount += 2;

                if (successCount == size * size) {
                    controller.setStatus("You win!");
                }
            } else {
                lastButton.setText("");
                lastButton.disableProperty().setValue(false);
                firstOpened = false;
                current.setText("");
                current.disableProperty().setValue(false);
            }
        }
    }
}
