package ru.spbau.farutin.test2_03;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;

import java.io.File;
import java.io.IOException;

/**
 * UI - class for loading scenes.
 */
public class UI {
    private static Stage stage;
    private static FXMLLoader fxmlLoader;
    private static NotificationPane notificationPane = null;

    /**
     * Shows notification.
     * @param message notification text
     */
    public static void showNotification(String message) {
        buildNotification(message);
        notificationPane.show();
    }

    private static void buildNotification(String message) {
        Scene scene = stage.getScene();
        Parent pane = scene.getRoot();

        if (notificationPane != null && notificationPane.isShowing()) {
            notificationPane.hide();
        }

        notificationPane = new NotificationPane(pane);
        notificationPane.setText(message);
        notificationPane.setCloseButtonVisible(true);
        notificationPane.setShowFromTop(false);
        notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);

        stage.setScene(new Scene(notificationPane, scene.getWidth(), scene.getHeight()));
    }

    /**
     * Saves stage and sets window title.
     * @param stage initial stage
     */
    public static void initialize(Stage stage) {
        UI.stage = stage;
        UI.stage.setTitle("XO");
        UI.stage.setResizable(false);
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
     * @throws IOException if failed to load file with scene
     */
    public static void showMainMenu() throws IOException {
        setScene("src/main/resources/main_menu.fxml");
    }

    /**
     * Sets scene with new game menu.
     * @throws IOException if failed to load file with scene
     */
    public static void showNewGame() throws IOException {
        setScene("src/main/resources/new_game.fxml");
    }

    /**
     * Sets scene for choosing network parameters..
     * @throws IOException if failed to load file with scene
     */
    public static void showNetworkParam() throws IOException {
        setScene("src/main/resources/network_param.fxml");
    }

    /**
     * Sets scene with stats.
     * @throws IOException if failed to load file with scene
     */
    public static void showStats() throws IOException {
        setScene("src/main/resources/stats.fxml");
    }

    /**
     * Sets scene with mode select menu.
     * @throws IOException if failed to load file with scene
     */
    public static void showModeSelect() throws IOException {
        setScene("src/main/resources/mode_select.fxml");
    }

    /**
     * Sets scene with game field.
     * @throws IOException if failed to load file with scene
     */
    public static void showGameField() throws IOException {
        setScene("src/main/resources/game_field.fxml");
    }

    /**
     * Loads scene from the given path.
     * @param path path to file with scene
     * @throws IOException if failed to load file with scene
     */
    private static void setScene(String path) throws IOException {
        fxmlLoader = new FXMLLoader(new File(path).toURI().toURL());
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 300, 275);
        scene.getStylesheets().add("style.css");

        stage.setScene(scene);
        stage.show();
    }
}
