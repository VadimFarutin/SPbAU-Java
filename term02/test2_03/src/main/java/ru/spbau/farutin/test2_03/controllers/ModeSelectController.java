package ru.spbau.farutin.test2_03.controllers;

import javafx.fxml.FXML;
import ru.spbau.farutin.test2_03.GameLogic;
import ru.spbau.farutin.test2_03.UI;

import java.io.IOException;

/**
 * ModeSelectController - controller for scene with mode select menu.
 */
public class ModeSelectController {
    /**
     * Starts new game against easy AI.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void startEasy() throws IOException {
        GameLogic.getInstance().startAI(0);
        UI.showGameField();
    }

    /**
     * Starts new game against hard AI.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void startHard() throws IOException {
        GameLogic.getInstance().startAI(1);
        UI.showGameField();
    }

    /**
     * Goes back in the menu hierarchy.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void goBack() throws IOException {
        UI.showNewGame();
    }
}
