package ru.spbau.farutin.test2_03.controllers;

import javafx.fxml.FXML;
import ru.spbau.farutin.test2_03.GameLogic;
import ru.spbau.farutin.test2_03.UI;

import java.io.IOException;

/**
 * NewGameController - controller for scene with new game menu.
 */
public class NewGameController {
    /**
     * Sets scene for selecting AI mode.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void showSingleModeSelect() throws IOException {
        UI.showModeSelect();
    }

    /**
     * Starts new hot seat game.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void startHotSeat() throws IOException {
        GameLogic.getInstance().startHotSeat();
        UI.showGameField();
    }

    /**
     * Sets scene for choosing network parameters.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void showNetworkParam() throws IOException {
        UI.showNetworkParam();
    }

    /**
     * Goes back in the menu hierarchy.
     * @throws IOException if failed to load file with scene
     */
    @FXML
    public void goBack() throws IOException {
        UI.showMainMenu();
    }
}
