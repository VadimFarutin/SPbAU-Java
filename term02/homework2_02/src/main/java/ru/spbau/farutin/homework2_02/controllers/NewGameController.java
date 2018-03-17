package ru.spbau.farutin.homework2_02.controllers;

import javafx.fxml.FXML;
import ru.spbau.farutin.homework2_02.GameLogic;
import ru.spbau.farutin.homework2_02.UI;

/**
 * NewGameController - controller for scene with new game menu.
 */
public class NewGameController {
    /**
     * Sets scene for selecting AI mode.
     */
    @FXML
    public void single() throws Exception {
        UI.modeSelect();
    }

    /**
     * Starts new hot seat game.
     */
    @FXML
    public void hotSeat() throws Exception {
        GameLogic.startHotSeat();
        UI.gameField();
    }

    /**
     * Goes back in the menu hierarchy.
     */
    @FXML
    public void back() throws Exception {
        UI.mainMenu();
    }
}
