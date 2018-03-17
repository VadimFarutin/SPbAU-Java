package ru.spbau.farutin.homework2_02;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

/**
 * UI - class for loading scenes.
 */
public class UI {
    private static Stage stage;
    private static FXMLLoader fxmlLoader;

    /**
     * Saves stage and sets window title.
     * @param stage initial stage
     */
    public static void init(Stage stage) {
        UI.stage = stage;
        UI.stage.setTitle("XO");
    }

    /**
     * Getter for controller of current scene.
     * @return controller of current scene
     */
    public static Object getController() {
        return fxmlLoader.getController();
    }

    /**
     * Sets scene with main menu.
     */
    public static void mainMenu() throws Exception {
        setScene("src/main/resources/main_menu.fxml");
    }

    /**
     * Sets scene with new game menu.
     */
    public static void newGame() throws Exception {
        setScene("src/main/resources/new_game.fxml");
    }

    /**
     * Sets scene with stats.
     */
    public static void stats() throws Exception {
        setScene("src/main/resources/stats.fxml");
    }

    /**
     * Sets scene with mode select menu.
     */
    public static void modeSelect() throws Exception {
        setScene("src/main/resources/mode_select.fxml");
    }

    /**
     * Sets scene with game field.
     */
    public static void gameField() throws Exception {
        setScene("src/main/resources/game_field.fxml");
    }

    /**
     * Loads scene from the given path.
     * @param path path to file with scene
     * @throws Exception if failed to load file
     */
    private static void setScene(String path) throws Exception {
        fxmlLoader = new FXMLLoader(new File(path).toURI().toURL());
        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}
