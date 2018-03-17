package ru.spbau.farutin.homework2_02.controllers;

import javafx.fxml.FXML;
import ru.spbau.farutin.homework2_02.GameLogic;
import ru.spbau.farutin.homework2_02.UI;

/**
 * ModeSelectController - controller for scene with mode select menu.
 */
public class ModeSelectController {
    /**
     * Starts new game against easy AI.
     */
    @FXML
    public void easy() throws Exception {
        GameLogic.startAI(0);
        UI.gameField();
    }

    /**
     * Starts new game against hard AI.
     */
    @FXML
    public void hard() throws Exception {
        GameLogic.startAI(1);
        UI.gameField();
    }

    /**
     * Goes back in the menu hierarchy.
     */
    @FXML
    public void back() throws Exception {
        UI.newGame();
    }
}
