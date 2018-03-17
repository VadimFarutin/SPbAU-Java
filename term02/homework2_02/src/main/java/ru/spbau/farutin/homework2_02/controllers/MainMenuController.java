package ru.spbau.farutin.homework2_02.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.spbau.farutin.homework2_02.UI;

/**
 * MainMenuController - controller for scene with main menu.
 */
public class MainMenuController {
    /**
     * Sets scene with new game menu.
     */
    @FXML
    public void newGame() throws Exception {
        UI.newGame();
    }

    /**
     * Sets scene with stats.
     */
    @FXML
    public void stats() throws Exception {
        UI.stats();
    }

    /**
     * Exits application.
     */
    @FXML
    public void exit() {
        Platform.exit();
    }
}
