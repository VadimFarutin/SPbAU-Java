package ru.spbau.farutin.homework2_02;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Tic-tac-toe application.
 */
public class MainApplication extends Application {
    public static void main(String[] args) {
        Application.launch(MainApplication.class, args);
    }

    /**
     * Application launching.
     * @param stage initial stage
     * @throws IOException if failed to load file with scenes
     */
    @Override
    public void start(Stage stage) throws IOException {
        UI.initialize(stage);
        UI.showMainMenu();
    }
}
