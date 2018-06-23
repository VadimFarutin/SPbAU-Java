package ru.spbau.farutin.test2_03.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import ru.spbau.farutin.test2_03.UI;

import java.io.IOException;

/**
 * MainMenuController - controller for scene with main menu.
 */
public class MainMenuController {
    /**
     * Sets scene with new game menu.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void showNewGame() throws IOException {
        UI.showNewGame();
    }

    /**
     * Sets scene with stats.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void showStats() throws IOException {
        UI.showStats();
    }

    /**
     * Exits application.
     */
    @FXML
    public void exit() {
        Platform.exit();
    }
}
