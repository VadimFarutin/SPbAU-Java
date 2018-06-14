package ru.spbau.farutin.homework2_04;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * GUI Client application.
 */
public class MainApplication extends Application {
    public static void main(String[] args) {
        Application.launch(MainApplication.class, args);
    }

    /**
     * Starts application.
     * @param stage primary stage for UI
     * @throws IOException if failed to load file with scene for UI
     */
    @Override
    public void start(Stage stage) throws IOException {
        UI.initialize(stage);
        UI.showMainMenu();
    }
}
