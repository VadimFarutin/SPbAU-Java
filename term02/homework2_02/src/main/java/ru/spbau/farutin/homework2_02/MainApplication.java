package ru.spbau.farutin.homework2_02;

import javafx.application.Application;
import javafx.stage.Stage;

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
     */
    @Override
    public void start(Stage stage) throws Exception {
        UI.init(stage);
        UI.mainMenu();
    }
}
