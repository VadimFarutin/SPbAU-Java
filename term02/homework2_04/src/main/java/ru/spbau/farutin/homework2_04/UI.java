package ru.spbau.farutin.homework2_04;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.action.Action;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * UI - class for loading scenes.
 */
public class UI {
    private static Stage stage;
    private static NotificationPane notificationPane = null;

    /**
     * Saves stage and sets window title.
     * @param stage initial stage
     */
    public static void initialize(Stage stage) {
        UI.stage = stage;
        UI.stage.setTitle("FTP Client");
        UI.stage.setResizable(false);
    }

    /**
     * Shows notification with one button.
     * @param message notification text
     * @param buttonText text on the button
     * @param action consumer to call when button is clicked
     */
    public static void showOneButtonNotification(String message,
                                                 String buttonText,
                                                 Consumer<ActionEvent> action) {
        buildNotification(message);
        notificationPane.getActions().add(new Action(buttonText,
                action.andThen(event -> notificationPane.hide())));
        notificationPane.show();
    }

    /**
     * Shows notification.
     * @param message notification text
     */
    public static void showNotification(String message) {
        buildNotification(message);
        notificationPane.show();
    }

    /**
     * Sets scene with main menu.
     * @throws IOException if failed to load file with scene
     */
    public static void showMainMenu() throws IOException {
        setScene("src/main/resources/main_menu.fxml");
    }

    /**
     * Sets scene with tree.
     * @throws IOException if failed to load file with scene
     */
    public static void showTree() throws IOException {
        setScene("src/main/resources/tree.fxml");
    }

    /**
     * Loads scene from the given path.
     * @param path path to file with scene
     * @throws IOException if failed to load file with scene
     */
    private static void setScene(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new File(path).toURI().toURL());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 500);

        stage.setScene(scene);
        stage.show();
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
}
