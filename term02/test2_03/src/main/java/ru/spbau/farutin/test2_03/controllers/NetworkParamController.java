package ru.spbau.farutin.test2_03.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import ru.spbau.farutin.test2_03.GameLogic;
import ru.spbau.farutin.test2_03.network.Client;
import ru.spbau.farutin.test2_03.UI;
import javafx.scene.control.TextField;
import ru.spbau.farutin.test2_03.network.Server;

import java.io.IOException;

/**
 * NetworkParamController - controller for scene with network params.
 */
public class NetworkParamController {
    @FXML
    private CheckBox checkBox;
    @FXML
    private TextField hostAddress;
    @FXML
    private TextField portNumber;

    /**
     * Establishes new server connection.
     * @throws IOException if failed to connect to server
     */
    @FXML
    public void connect() throws IOException {
        try {
            int port = Integer.parseInt(portNumber.getCharacters().toString());

            if (checkBox.isSelected()) {
                Server.start(port);
                GameLogic.getInstance().startFirst();
            } else {
                String host = hostAddress.getCharacters().toString();
                Client.start(host, port);
                GameLogic.getInstance().startSecond();
            }

            UI.showGameField();
        } catch (NumberFormatException e) {
            UI.showNotification("Wrong port number format!");
        }
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
