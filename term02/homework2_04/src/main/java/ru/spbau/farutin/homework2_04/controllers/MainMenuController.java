package ru.spbau.farutin.homework2_04.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import ru.spbau.farutin.homework2_04.Client;
import ru.spbau.farutin.homework2_04.UI;
import javafx.scene.control.TextField;
import java.io.IOException;

/**
 * MainMenuController - controller for scene with main menu.
 */
public class MainMenuController {
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
            String host = hostAddress.getCharacters().toString();
            int port = Integer.parseInt(portNumber.getCharacters().toString());
            Client.initialize(host, port);
        } catch (NumberFormatException e) {
            UI.showNotification("Wrong port number format!");
            return;
        } catch (IOException e) {
            UI.showNotification("Failed to connect!");
            return;
        }

        UI.showTree();
    }

    /**
     * Exits application.
     */
    @FXML
    public void exit() {
        Platform.exit();
    }
}
