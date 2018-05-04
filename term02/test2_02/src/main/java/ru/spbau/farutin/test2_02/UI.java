package ru.spbau.farutin.test2_02;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * UI - class for loading scenes.
 */
public class UI {
    private static Stage stage;

    /**
     * Saves stage and sets window title.
     * @param stage initial stage
     */
    public static void initialize(Stage stage) {
        UI.stage = stage;
        UI.stage.setTitle("Find Pair");
        UI.stage.setResizable(false);
    }

    /**
     * Sets scene with game field.
     * @throws IOException if failed to load file with scene
     */
    public static void showGameField() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                new File("src/main/resources/game_field.fxml").toURI().toURL());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
